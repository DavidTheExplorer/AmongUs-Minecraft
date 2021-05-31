package dte.amongus.maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GameMapService
{
	private final Map<String, GameMap> mapByName = new HashMap<>();
	
	public GameMap getMap(String mapName) 
	{
		return this.mapByName.get(mapName);
	}
	public void register(GameMap map) 
	{
		this.mapByName.put(map.getName(), map);
	}
	public int mapsAmount()
	{
		return this.mapByName.size();
	}
	public Collection<GameMap> getMaps()
	{
		return new HashSet<>(this.mapByName.values());
	}
	
	/*private class IdentifyableMap extends GameMap implements Named
	{
		public IdentifyableMap(GameMap map)
		{
			super(map.getName());
			
			map.getSpawnLocations().forEach(this::addSpawnLocation);
		}

		@Override
		public String identifyableBy()
		{
			return getName();
		}
	}*/
}