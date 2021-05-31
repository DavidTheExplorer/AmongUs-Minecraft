package dte.amongus.shiptasks.list.cleano2filter;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import dte.amongus.AmongUs;
import dte.amongus.games.players.Crewmate;

public class LeafCleaningRunnable extends BukkitRunnable
{
	private final CleanO2FilterTask cleanO2FilterTask;
	private final Inventory taskInventory;
	private final Crewmate cleaner;
	private final List<Integer> exitPath;
	private final Sound cleaningSound;
	private final int chainIndex;
	
	private ItemStack chainItem; //before the chain is opened(the chain is removed from the inventory), it's saved here
	private int currentPathIndex = 1;

	public LeafCleaningRunnable(CleanO2FilterTask cleanO2FilterTask, Inventory taskInventory, Crewmate cleaner, List<Integer> exitPath, Sound cleaningSound) 
	{
		this.cleanO2FilterTask = cleanO2FilterTask;
		this.taskInventory = taskInventory;
		this.cleaner = cleaner;
		this.exitPath = exitPath;
		this.cleaningSound = cleaningSound;
		this.chainIndex = exitPath.get(exitPath.size()-2);
	}

	@Override
	public void run()
	{
		forwardLeaf();
		this.cleaner.getPlayer().playSound(this.cleaner.getPlayer().getLocation(), this.cleaningSound, 1, 1);
		this.currentPathIndex++;
		
		if(this.currentPathIndex == this.exitPath.size()) 
		{
			closeChain();
			Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), this::removeLeaf, 10);
			this.cleanO2FilterTask.removeCurrentLeafData(this.cleaner);
			
			//return the (Left Click) to the leaves' names
			CleanO2FilterInventoryManager.setLeavesTo(this.taskInventory, leaf -> CleanO2FilterInventoryManager.createLeaf(leaf.getType()));
			
			cancel();
			return;
		}
		
		if(shouldOpenChain())
		{
			saveChain();
			openChain();
		}
	}
	
	private void forwardLeaf()
	{
		int leafIndex = this.exitPath.get(this.currentPathIndex-1);
		int nextIndex = this.exitPath.get(this.currentPathIndex);

		ItemStack leaf = this.taskInventory.getItem(leafIndex);
		this.taskInventory.setItem(leafIndex, null);
		this.taskInventory.setItem(nextIndex, leaf);
	}
	private boolean shouldOpenChain()
	{
		return this.chainIndex == this.exitPath.get(this.currentPathIndex);
	}
	private void openChain() 
	{
		this.taskInventory.setItem(this.chainIndex, null);
	}
	private void saveChain() 
	{
		this.chainItem = this.taskInventory.getItem(this.chainIndex);
	}
	private void closeChain() 
	{
		this.taskInventory.setItem(this.chainIndex, this.chainItem);
	}
	private void removeLeaf() 
	{
		this.taskInventory.setItem(this.exitPath.get(this.currentPathIndex-1), null);
	}
}