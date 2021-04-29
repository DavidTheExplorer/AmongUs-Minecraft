package dte.amongus.utils.blocks.transformers.restorers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;

import dte.amongus.utils.blocks.BlockUtils;

public class DoorRestorer extends BlockStateRestorer
{
	public DoorRestorer(BlockState originalState) 
	{
		super(originalState);
	}
	
	@Override
	public void restore() 
	{
		super.restore();
		
		Location doorLocation = this.originalState.getLocation();
		
		doorLocation.getBlock().setType(Material.AIR);
		BlockUtils.placeDoor(this.originalState.getType(), doorLocation);
	}
}
