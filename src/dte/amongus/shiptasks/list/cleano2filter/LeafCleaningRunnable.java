package dte.amongus.shiptasks.list.cleano2filter;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LeafCleaningRunnable extends BukkitRunnable
{
	private final Inventory o2Inventory;
	private final int chainIndex;
	
	private int currentIndex;
	
	/*
	 * 1: 0   1   2   3   4   5   6   7   8 
	 * 2: 9   10  11  12  13  14  15  16  17
	 * 3: 18  19  20  21  22  23  24  25
	 */
	
	public LeafCleaningRunnable(Inventory o2Inventory, int leafIndex, int chainIndex) 
	{
		this.o2Inventory = o2Inventory;
		this.currentIndex = leafIndex;
		this.chainIndex = chainIndex;
	}
	
	@Override
	public void run() 
	{
		if(this.currentIndex == this.chainIndex) 
		{
			this.o2Inventory.setItem(this.currentIndex-1, null);
			return;
		}
		ItemStack leaf = this.o2Inventory.getItem(this.currentIndex);
		this.o2Inventory.setItem(this.currentIndex, null);
		
		int nextIndex = calculateNextIndex();
		this.o2Inventory.setItem(nextIndex, leaf);
	}
	
	private int calculateNextIndex() 
	{
		int currentRow = getRow(this.currentIndex);
		int chainRow = getRow(this.chainIndex);
		
		if(currentRow == chainRow) 
			return --this.currentIndex;
		
		//the leaf is above the chain
		if(currentRow < chainRow)
			return this.currentIndex+9;
		
		throw new RuntimeException("How did we get here?");
	}
	
	private static int getRow(int index) 
	{
		int row = index % 9;
		
		return row == 0 ? 1 : row;
	}
}
