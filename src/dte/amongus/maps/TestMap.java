package dte.amongus.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class TestMap extends GameMap
{
	public TestMap() 
	{
		super("Test");
		
		World world = Bukkit.getWorld("world");
		
		addSpawnLocation(new Location(world, -19.700, 64, 242.700));
		addSpawnLocation(new Location(world, 14, 64, 240));
		addSpawnLocation(new Location(world, 13, 64, 242));
		addSpawnLocation(new Location(world, 14, 64, 244));
		addSpawnLocation(new Location(world, -16, 64, 245));
		addSpawnLocation(new Location(world, -18, 84, 244));
		addSpawnLocation(new Location(world, -19, 64, 242));
		addSpawnLocation(new Location(world, -18, 64, 240));
	}
}