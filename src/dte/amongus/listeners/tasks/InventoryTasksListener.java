package dte.amongus.listeners.tasks;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import dte.amongus.games.AUGameService;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.service.ShipTaskService;

public class InventoryTasksListener implements Listener
{
	private final AUGameService gameService;
	private final ShipTaskService shipTaskService;

	public InventoryTasksListener(ShipTaskService shipTaskService, AUGameService gameService) 
	{
		this.shipTaskService = shipTaskService;
		this.gameService = gameService;
	}

	@EventHandler
	public void onTaskInventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked();

		this.gameService.getPlayerGame(player)
		.map(playerGame -> playerGame.getPlayer(player, Crewmate.class))
		.filter(Objects::nonNull) //verify the inventory clicker is Crewmate
		.flatMap(this.shipTaskService::getPlayerTask)
		.filter(InventoryTask.class::isInstance) //verify the crewmate's current task is an inventory task
		.map(InventoryTask.class::cast)
		.filter(inventoryTask -> inventoryTask.getInventoryManager().wasInvolvedAt(event))
		.ifPresent(inventoryTask -> 
		{
			event.setCancelled(true);

			if(event.getCurrentItem() == null)
				return;
			
			Crewmate crewmate = this.gameService.getPlayerGame(player).get().getPlayer(player, Crewmate.class);
			inventoryTask.getInventoryManager().onInventoryClick(crewmate, event);
		});
	}
}
