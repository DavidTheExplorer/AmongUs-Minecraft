package dte.amongus.listeners.tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import dte.amongus.games.players.Crewmate;
import dte.amongus.games.service.AUGameService;
import dte.amongus.shiptasks.inventory.InventoryTask;

public class InventoryTasksListener implements Listener
{
	private final AUGameService gameService;

	public InventoryTasksListener(AUGameService gameService) 
	{
		this.gameService = gameService;
	}

	@EventHandler
	public void onTaskInventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked();

		this.gameService.getPlayerGame(player).ifPresent(playerGame -> 
		{
			Crewmate crewmate = playerGame.getPlayer(player, Crewmate.class);

			//verify the inventory clicker is Crewmate
			if(crewmate == null) 
				return;
			
			playerGame.getCurrentTask(crewmate)
			.filter(InventoryTask.class::isInstance) //verify the crewmate's current task is an inventory task
			.map(InventoryTask.class::cast)
			.filter(inventoryTask -> inventoryTask.getInventoryManager().wasInvolvedAt(event)) //verify the event's inventory is the task's inventory
			.ifPresent(inventoryTask -> 
			{
				event.setCancelled(true);

				if(event.getCurrentItem() == null)
					return;

				inventoryTask.getInventoryManager().onInventoryClick(crewmate, event);
			});
		});
	}
}
