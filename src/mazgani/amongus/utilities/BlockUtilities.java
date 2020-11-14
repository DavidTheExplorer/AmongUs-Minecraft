package mazgani.amongus.utilities;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockUtilities 
{
	//Container of static methods
	private BlockUtilities(){}
	
	public static final BlockFace[] SURROUNDING_SIDES = {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};
	
	public static Block[] getSurroundingBlocks(Block centerBlock) 
	{
		return Arrays.stream(SURROUNDING_SIDES)
				.map(centerBlock::getRelative)
				.toArray(Block[]::new);
		
		/*Block[] blocks = new Block[SURROUNDING_SIDES.length];
		
		for(int i = 0; i < blocks.length; i++)
		{
			blocks[i] = centerBlock.getRelative(SURROUNDING_SIDES[i]);
		}
		return blocks;*/
	}
	public static Block computeLowestBlock(Location location) 
	{
		return computeClosestTakenBlock(location, BlockFace.DOWN);
	}
	public static Block computeHighestBlock(Location location) 
	{
		return computeClosestTakenBlock(location, BlockFace.UP);
	}
	public static Block computeClosestTakenBlock(Location source, BlockFace face)
	{
		Block closest = source.getBlock();
		
		while(closest.getType() == Material.AIR)
		{
			closest = closest.getRelative(face);
		}
		return closest;
	}
}
