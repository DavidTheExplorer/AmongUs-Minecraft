package mazgani.amongus.utilities;

import static java.util.stream.Collectors.toList;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.NORTH_EAST;
import static org.bukkit.block.BlockFace.NORTH_WEST;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.SOUTH_EAST;
import static org.bukkit.block.BlockFace.SOUTH_WEST;
import static org.bukkit.block.BlockFace.WEST;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import mazgani.amongus.utilities.objectholders.Pair;

public class BlockUtilities 
{
	//Container of static methods
	private BlockUtilities(){}

	public static final BlockFace[] 
			SURROUNDING_FACES = {NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST},
			ATTACHED_FACES = {NORTH, EAST, SOUTH, WEST};

	public static List<Block> getFacedBlocks(Block centerBlock, BlockFace... faces) 
	{
		return Arrays.stream(faces)
				.map(centerBlock::getRelative)
				.collect(toList());
	}
	public static List<Block> getFacedBlocks(Block centerBlock, Collection<Pair<BlockFace, Integer>> facesAndDistances) 
	{
		return facesAndDistances.stream()
				.map(pair -> centerBlock.getRelative(pair.getFirst(), pair.getSecond()))
				.collect(toList());
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
