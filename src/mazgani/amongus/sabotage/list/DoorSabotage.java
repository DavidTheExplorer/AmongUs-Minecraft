package mazgani.amongus.sabotage.list;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.block.Block;

import mazgani.amongus.sabotage.OpenablesSabotage;

public class DoorSabotage extends OpenablesSabotage
{
	public DoorSabotage(Block... doors)
	{
		super(doors);
	}
	
	@Override
	public void sabotage() 
	{
		super.sabotage();
		
		Arrays.stream(this.blocks).forEach(block -> block.setType(Material.IRON_DOOR));
	}
	
	@Override
	public void unsabotage() 
	{
		super.sabotage();
		
		Arrays.stream(this.blocks).forEach(block -> block.setType(Material.OAK_DOOR));
	}
}
