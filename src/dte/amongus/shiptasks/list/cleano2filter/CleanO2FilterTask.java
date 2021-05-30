package dte.amongus.shiptasks.list.cleano2filter;

import java.util.Optional;

import org.bukkit.Sound;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.SimpleShipTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.shiptasks.type.TaskType;

public class CleanO2FilterTask extends SimpleShipTask implements InventoryTask
{
	private final int leavesAmount;
	private final CleanO2FilterInventoryManager inventoryManager;
	
	public CleanO2FilterTask(AUGame game, int leavesAmount, Sound cleaningSound) 
	{
		super("Clean O2 Filter", "Clean the O2 Filter from the Leaves!", TaskType.SHORT, game);
		
		this.leavesAmount = leavesAmount;
		this.inventoryManager =  new CleanO2FilterInventoryManager(this, cleaningSound);
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
	
	public Optional<Integer> getCurrentLeafData(AUGamePlayer gamePlayer)
	{
		return getData(gamePlayer, "Current Leaf Index", Integer.class);
	}
	
	public void setCurrentLeafData(AUGamePlayer gamePlayer, int leafIndex) 
	{
		setData(gamePlayer, "Current Leaf Index", leafIndex);
	}
	
	public void removeCurrentLeafData(AUGamePlayer gamePlayer) 
	{
		removeData(gamePlayer, "Current Leaf Index");
	}
}
