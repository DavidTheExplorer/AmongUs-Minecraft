package mazgani.amongus.bodies.list;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import mazgani.amongus.AmongUs;
import mazgani.amongus.bodies.BodyColor;
import mazgani.amongus.bodies.DeadBody;
import mazgani.amongus.utilities.BlockUtilities;

public class BlockBody extends DeadBody
{
	private Hologram hologram;
	
	public BlockBody(BodyColor color) 
	{
		super(color);
	}
	
	@Override
	public void spawn(String deadName, Location deathLocation) 
	{
		spawnGrave(deathLocation.getBlock());
		spawnDeathHologram(deadName, deathLocation);
	}
	private void spawnGrave(Block center) 
	{
		center.setType(getBodyMaterial(this.color));
		
		Block[] blocks = BlockUtilities.getSurroundingBlocks(center);
		
		for(int i = 0; i < blocks.length; i++) 
		{
			Material material = (i % 2 == 0 ? Material.BLACK_WOOL : Material.BEDROCK);
			
			blocks[i].setType(material);
		}
	}
	private void spawnDeathHologram(String deadName, Location deathLocation) 
	{
		this.hologram = HologramsAPI.createHologram(AmongUs.getInstance(), deathLocation.add(0, 2.5, 0));
        this.hologram.appendTextLine(this.color.getChatColor() + deadName + ChatColor.WHITE + " died here.");
      //textLine.setTouchHandler(player -> {}); call report
	}
	private Material getBodyMaterial(BodyColor color) 
	{
		return Material.valueOf(color.name() + "_WOOL");
	}
}