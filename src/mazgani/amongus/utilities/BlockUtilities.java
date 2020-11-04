package mazgani.amongus.utilities;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUtilities 
{
	//Container of static methods
	private BlockUtilities(){}
	
	public static final BlockFace[] SURROUNDING_SIDES = {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};
	
	public static Block[] getSurroundingBlocks(Block centerBlock) 
	{
		Block[] blocks = new Block[SURROUNDING_SIDES.length];
		
		for(int i = 0; i < blocks.length; i++) 
		{
			blocks[i] = centerBlock.getRelative(SURROUNDING_SIDES[i]);
		}
		return blocks;
	}
}
