package mazgani.amongus.shiptasks.inventorytasks.wires;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import mazgani.amongus.utilities.ItemBuilder;

public class WiresConnectionTask extends BukkitRunnable
{
	private final Inventory wiresInv;
	private final int startIndex, endIndex;
	private final Queue<Material> progressionMaterials;
	
	private Material currentConnectorMaterial;
	
	public WiresConnectionTask(Inventory wiresInv, int startIndex, int endIndex, Material... progressionMaterials) 
	{
		this.wiresInv = wiresInv;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.progressionMaterials = new LinkedList<>(Arrays.asList(progressionMaterials));
	}
	
	@Override
	public void run() 
	{
		if(this.progressionMaterials.isEmpty()) 
		{
			cancel();
			return;
		}
		this.currentConnectorMaterial = this.progressionMaterials.poll();
		
		updateConnectorsColor();
	}
	public Material getCurrentConnectorMaterial() 
	{
		return this.currentConnectorMaterial;
	}
	private void updateConnectorsColor() 
	{
		ItemStack connector = new ItemBuilder(this.currentConnectorMaterial, ChatColor.WHITE + "Connecting...").createCopy();
		
		for(int i = this.startIndex; i <= this.endIndex; i++) 
		{
			this.wiresInv.setItem(i, connector);
		}
	}
}
