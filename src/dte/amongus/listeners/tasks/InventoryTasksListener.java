package dte.amongus.listeners.tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.AUGameService;
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
		.map(game -> game.getPlayer(player))
		.flatMap(gamePlayer -> this.shipTaskService.getPlayerTask(gamePlayer)) //get the player's current task
		.filter(InventoryTask.class::isInstance) //verify it's an inventory task
		.map(InventoryTask.class::cast)
		.filter(inventoryTask -> inventoryTask.getInventoryManager().wasInvolvedAt(event))
		.ifPresent(inventoryTask -> 
		{
			event.setCancelled(true);
			
			ItemStack item = event.getCurrentItem();

			if(item == null)
				return;
			
			inventoryTask.getInventoryManager().onInventoryClick(event);
		});
	}
}
