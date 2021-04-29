package dte.amongus.utils.blocks;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.bukkit.block.BlockFace.EAST;
import static org.bukkit.block.BlockFace.NORTH;
import static org.bukkit.block.BlockFace.NORTH_EAST;
import static org.bukkit.block.BlockFace.NORTH_WEST;
import static org.bukkit.block.BlockFace.SOUTH;
import static org.bukkit.block.BlockFace.SOUTH_EAST;
import static org.bukkit.block.BlockFace.SOUTH_WEST;
import static org.bukkit.block.BlockFace.WEST;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;

public class BlockUtils
{
	//Container of static methods
	private BlockUtils(){}

	public static final BlockFace[]
			CIRCLE_FACES = {NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST},
			PLUS_FACES = {NORTH, EAST, SOUTH, WEST};

	/*
	 * Relative Blocks
	 */
	public static List<Block> getFacedBlocks(Block centerBlock, Map<BlockFace, Integer> facesAndDistances) 
	{
		return facesAndDistances.entrySet().stream()
				.map(entry -> centerBlock.getRelative(entry.getKey(), entry.getValue()))
				.collect(toList());
	}
	public static List<Block> getFacedBlocks(Block centerBlock, BlockFace... faces) 
	{
		Map<BlockFace, Integer> distanceOfOne = Arrays.stream(faces).collect(toMap(Function.identity(), face -> 1));

		return getFacedBlocks(centerBlock, distanceOfOne);
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
			closest = closest.getRelative(face);

		return closest;
	}

	/*
	 * Doors
	 */
	public static boolean isDoor(Material material) 
	{
		return material.name().endsWith("_DOOR"); //the _ is to exclude trapdoors
	}
	public static boolean isWoodenDoor(Material material) 
	{
		return isDoor(material) && material != Material.IRON_DOOR;
	}
	public static void placeDoor(Material doorMaterial, Location bottomPartLocation) 
	{
		Location upperPartLocation = bottomPartLocation.add(0, 1, 0);
		
		setDoorHalf(bottomPartLocation, Half.BOTTOM, doorMaterial);
		setDoorHalf(upperPartLocation, Half.TOP, doorMaterial);
	}
	public static void changeDoor(Location doorLocation, Material newMaterial) 
	{
		verifyDoor(newMaterial);
		verifyDoor(doorLocation.getBlock().getType());
		
		Block bottomBlock = getBottomHalf(doorLocation).getBlock();
		Block upperBlock = bottomBlock.getRelative(BlockFace.UP);

		bottomBlock.setType(newMaterial, false);
		upperBlock.setType(newMaterial, false);

		setDoorHalf(upperBlock.getLocation(), Half.TOP, newMaterial);
	}
	private static void setDoorHalf(Location halfLocation, Half half, Material halfMaterial) 
	{
		verifyDoor(halfMaterial);
		
		Bisected bisected = (Bisected) Bukkit.createBlockData(halfMaterial);
		bisected.setHalf(half);
		halfLocation.getBlock().setBlockData(bisected, false);
	}
	private static Location getBottomHalf(Location doorLocation) 
	{
		Bisected bisected = (Bisected) doorLocation.getBlock().getBlockData();

		switch(bisected.getHalf())
		{
		case BOTTOM:
			return doorLocation;
		case TOP:
			return doorLocation.clone().subtract(0, 1, 0);
		default:
			return null;
		}
	}
	private static void verifyDoor(Material doorMaterial) 
	{
		if(!isDoor(doorMaterial))
			throw new IllegalArgumentException(String.format("The provided material(%s) doesn't represent a Door!", doorMaterial.name()));
	}
}
