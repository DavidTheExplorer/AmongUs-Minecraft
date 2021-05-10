package dte.amongus.shiptasks.list.stabilizesteering;

import dte.amongus.games.AUGame;
import dte.amongus.shiptasks.SimpleShipTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.type.TaskType;

public class StabilizeSteeringTask extends SimpleShipTask implements InventoryTask
{
	private final StabilizeSteeringInventoryManager inventoryManager = new StabilizeSteeringInventoryManager(this);
	
	public StabilizeSteeringTask(AUGame game) 
	{
		super("Stabilize Steering", "Stabilize The Steering!", TaskType.SHORT, game);
	}

	@Override
	public StabilizeSteeringInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
}