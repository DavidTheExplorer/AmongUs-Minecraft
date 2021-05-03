package dte.amongus.listeners.tasks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.GamesManager;
import dte.amongus.shiptasks.ShipTaskService;
import dte.amongus.shiptasks.types.inventory.InventoryTask;

public class InventoryTasksListener implements Listener
{
	private final GamesManager gamesManager;
	private final ShipTaskService shipTaskService;

	public InventoryTasksListener(ShipTaskService shipTaskService, GamesManager gamesManager) 
	{
		this.shipTaskService = shipTaskService;
		this.gamesManager = gamesManager;
	}

	@EventHandler
	public void onTaskInventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked();
		
		this.gamesManager.getPlayerGame(player)
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
