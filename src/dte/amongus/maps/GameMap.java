package dte.amongus.maps;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

public class GameMap
{
	private final String name;
	private final Set<Location> spawnLocations = new HashSet<>();
	
	public GameMap(String name) 
	{
		this.name = name;
	}
	public String getName() 
	{
		return this.name;
	}
	public void addSpawnLocation(Location spawnLocation) 
	{
		this.spawnLocations.add(spawnLocation);
	}
	public Set<Location> getSpawnLocations() 
	{
		return new HashSet<>(this.spawnLocations);
	}
	
	@Override
	public String toString() 
	{
		return this.name;
	}
}