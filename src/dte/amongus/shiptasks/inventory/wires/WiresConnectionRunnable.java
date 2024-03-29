package dte.amongus.shiptasks.inventory.wires;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class WiresConnectionRunnable extends BukkitRunnable
{
	private final Inventory taskInventory;
	private final Player crewmatePlayer;
	private final int startIndex, endIndex;
	private final ItemStack connectingItem, connectedItem;
	private final Sound connectionSound, connectionFinishedSound;
	
	private int currentIndex;
	private boolean isFirstRun;

	public WiresConnectionRunnable(Inventory taskInventory, Player crewmatePlayer, int startIndex, int endIndex, Sound connectionSound, Sound connectionFinishedSound, ChatColor wireColor, Material wireMaterial) 
	{
		this.taskInventory = taskInventory;
		this.crewmatePlayer = crewmatePlayer;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.connectionSound = connectionSound;
		this.connectionFinishedSound = connectionFinishedSound;
		
		this.connectingItem = new ItemBuilder(wireMaterial)
				.named(wireColor + "Connecting...")
				.createCopy();
		
		this.connectedItem = new ItemBuilder(wireMaterial)
				.named(wireColor + "Connected")
				.createCopy();
		
		this.currentIndex = startIndex;
		this.isFirstRun = true;
	}

	@Override
	public void run() 
	{
		if(this.currentIndex == (this.endIndex+1)) 
		{
			this.taskInventory.setItem(this.currentIndex-1, this.connectedItem.clone());
			this.crewmatePlayer.playSound(this.crewmatePlayer.getLocation(), this.connectionFinishedSound, 1, 1);
			
			//remove the glow from both wires
			GlowEffect.deleteGlow(this.taskInventory.getItem(this.endIndex+1));
			GlowEffect.deleteGlow(this.taskInventory.getItem(this.startIndex-1));
			
			cancel();
			return;
		}
		this.taskInventory.setItem(this.currentIndex, this.connectingItem.clone());
		this.crewmatePlayer.playSound(this.crewmatePlayer.getLocation(), this.connectionSound, 1, 1);
		
		
		//set the index before as connected
		if(!this.isFirstRun)
			this.taskInventory.setItem(this.currentIndex -1, this.connectedItem.clone());
		
		this.currentIndex++;
		
		if(this.isFirstRun)
			this.isFirstRun = false;
	}
}
