package dte.amongus.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class TestMap extends GameMap
{
	private static final World WORLD = Bukkit.getWorld("world");
	
	public TestMap() 
	{
		super("Test");
		
		addSpawnLocation(new Location(WORLD, -19.700, 64, 242.700));
		addSpawnLocation(new Location(WORLD, 14, 64, 240));
		addSpawnLocation(new Location(WORLD, 13, 64, 242));
		addSpawnLocation(new Location(WORLD, 14, 64, 244));
		addSpawnLocation(new Location(WORLD, -16, 64, 245));
		addSpawnLocation(new Location(WORLD, -18, 84, 244));
		addSpawnLocation(new Location(WORLD, -19, 64, 242));
		addSpawnLocation(new Location(WORLD, -18, 64, 240));
	}
}