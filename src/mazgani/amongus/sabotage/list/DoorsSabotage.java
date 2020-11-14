package mazgani.amongus.sabotage.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import mazgani.amongus.sabotage.OpenablesSabotage;

public class DoorsSabotage extends OpenablesSabotage
{
	private final Map<Location, Material> previousDoorsMaterials = new HashMap<>();
	
	public DoorsSabotage(Block... doors)
	{
		super(doors);
	}
	
	@Override
	public void sabotage() 
	{
		for(Block block : getBlocks())
		{
			this.previousDoorsMaterials.put(block.getLocation(), block.getType());
		}
		super.sabotage();
		
		setDoorsMaterial(Material.IRON_DOOR);
	}
	
	@Override
	public void unsabotage() 
	{
		super.unsabotage();
		
		for(Block block : getBlocks()) 
		{
			block.setType(this.previousDoorsMaterials.get(block.getLocation()));
		}
	}
	
	//make a BlocksSabotage class and put this there, let OpenablesSabotage extend it
	//make a new WallSabotage class that extends BlocksSabotage which put blocks and deletes them
	protected void setDoorsMaterial(Material newMaterial) 
	{
		Arrays.stream(getBlocks()).forEach(block -> block.setType(newMaterial));
	}
}
