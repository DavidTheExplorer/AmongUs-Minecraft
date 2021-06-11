package dte.amongus.shiptasks;

import org.bukkit.Sound;

import dte.amongus.games.AUGame;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.shiptasks.inventory.stabilizesteering.StabilizeSteeringInventoryManager;
import dte.amongus.shiptasks.simple.SimpleShipTask;

public class StabilizeSteeringTask extends SimpleShipTask implements InventoryTask
{
	private final StabilizeSteeringInventoryManager inventoryManager;
	
	public StabilizeSteeringTask(AUGame game, Sound steeringSound) 
	{
		super("Stabilize Steering", "Stabilize The Steering!", TaskType.SHORT, game);
		
		this.inventoryManager = new StabilizeSteeringInventoryManager(steeringSound);
	}

	@Override
	public TaskInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
}