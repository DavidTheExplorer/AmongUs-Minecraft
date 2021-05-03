package dte.amongus.shiptasks.wires;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class WiresConnectionRunnable extends BukkitRunnable
{
	private final Inventory wiresInventory;
	private final int startIndex, endIndex;
	private final Material connectionMaterial;
	
	private int currentIndex;
	private boolean isFirstRun;
	
	private static final ItemBuilder CONNECTED_BUILDER = new ItemBuilder(Material.BLACK_STAINED_GLASS, ChatColor.GRAY + "Connected.");

	public WiresConnectionRunnable(Inventory wiresInventory, int startIndex, int endIndex, Material connectionMaterial) 
	{
		this.wiresInventory = wiresInventory;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.connectionMaterial = connectionMaterial;
		
		this.currentIndex = startIndex;
		this.isFirstRun = true;
	}

	@Override
	public void run() 
	{
		if(this.currentIndex == (this.endIndex+1)) 
		{
			this.wiresInventory.setItem(this.currentIndex-1, CONNECTED_BUILDER.createCopy());
			
			//remove the glow from both wires
			GlowEffect.deleteGlow(this.wiresInventory.getItem(this.endIndex+1));
			GlowEffect.deleteGlow(this.wiresInventory.getItem(this.startIndex-1));
			
			cancel();
			return;
		}
		this.wiresInventory.setItem(this.currentIndex, new ItemBuilder(this.connectionMaterial, ChatColor.WHITE + "Connecting...").createCopy());
		
		//set the index before as connected
		if(!this.isFirstRun)
			this.wiresInventory.setItem(this.currentIndex -1, CONNECTED_BUILDER.createCopy());
		
		this.currentIndex++;
		
		if(this.isFirstRun)
			this.isFirstRun = false;
	}
}
