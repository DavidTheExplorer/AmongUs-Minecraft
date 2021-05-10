package dte.amongus.shiptasks.list.wires;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class WiresConnectionRunnable extends BukkitRunnable
{
	private final Inventory wiresInventory;
	private final int startIndex, endIndex;
	private final ItemStack connectingItem, connectedItem;
	
	private int currentIndex;
	private boolean isFirstRun;

	public WiresConnectionRunnable(Inventory wiresInventory, int startIndex, int endIndex, ChatColor wireColor, Material wireMaterial) 
	{
		this.wiresInventory = wiresInventory;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.connectingItem = new ItemBuilder(wireMaterial, wireColor + "Connecting...").createCopy();
		this.connectedItem = new ItemBuilder(wireMaterial, wireColor + "Connected").createCopy();
		
		this.currentIndex = startIndex;
		this.isFirstRun = true;
	}

	@Override
	public void run() 
	{
		if(this.currentIndex == (this.endIndex+1)) 
		{
			this.wiresInventory.setItem(this.currentIndex-1, this.connectedItem.clone());
			
			//remove the glow from both wires
			GlowEffect.deleteGlow(this.wiresInventory.getItem(this.endIndex+1));
			GlowEffect.deleteGlow(this.wiresInventory.getItem(this.startIndex-1));
			
			cancel();
			return;
		}
		this.wiresInventory.setItem(this.currentIndex, this.connectingItem.clone());
		
		//set the index before as connected
		if(!this.isFirstRun)
			this.wiresInventory.setItem(this.currentIndex -1, this.connectedItem.clone());
		
		this.currentIndex++;
		
		if(this.isFirstRun)
			this.isFirstRun = false;
	}
}
