package dte.amongus.shiptasks;

import java.util.Optional;

import org.bukkit.Sound;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.shiptasks.inventory.cleano2filter.CleanO2FilterInventoryManager;
import dte.amongus.shiptasks.type.TaskType;

public class CleanO2FilterTask extends AbstractShipTask implements InventoryTask
{
	private final int leavesAmount;
	private final CleanO2FilterInventoryManager inventoryManager;
	
	public CleanO2FilterTask(AUGame game, int leavesAmount, Sound cleaningSound) 
	{
		super("Clean O2 Filter", "Clean the O2 Filter from the Leaves!", TaskType.SHORT, game);
		
		this.leavesAmount = leavesAmount;
		this.inventoryManager = new CleanO2FilterInventoryManager(this, cleaningSound);
	}
	
	@Override
	public TaskInventoryManager getInventoryManager() 
	{
		return this.inventoryManager;
	}
	
	public int getLeavesAmount() 
	{
		return this.leavesAmount;
	}
	
	public Optional<Integer> getCurrentLeafIndex(Crewmate crewmate)
	{
		return getData(crewmate, "Current Leaf Index", Integer.class);
	}
	
	public void setCurrentLeafIndex(Crewmate crewmate, int leafIndex) 
	{
		setData(crewmate, "Current Leaf Index", leafIndex);
	}
	
	public void removeCurrentLeafIndex(Crewmate crewmate) 
	{
		removeData(crewmate, "Current Leaf Index");
	}
}
