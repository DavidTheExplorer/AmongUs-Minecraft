package mazgani.amongus.games.maps;

import org.bukkit.Location;

public class GameMap 
{
	private final String name;
	private final Location[] spawnLocations;
	
	public GameMap(String name, Location... spawnLocations) 
	{
		this.name = name;
		this.spawnLocations = spawnLocations;
	}
	public String getName() 
	{
		return this.name;
	}
	public Location[] getSpawnLocations() 
	{
		return this.spawnLocations;
	}
}
