package mazgani.amongus.maps;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import mazgani.amongus.utilities.storage.IdentityStorage;
import mazgani.amongus.utilities.storage.identificators.Named;
import mazgani.amongus.utilities.storage.map_implementations.IdentityMapStorage;

public class GameMapsManager 
{
	private final IdentityStorage<String, IdentifyableMap> mapByName = new IdentityMapStorage<>(new HashMap<>());
	
	public GameMap getMap(String mapName) 
	{
		return this.mapByName.get(mapName);
	}
	public void register(GameMap map) 
	{
		this.mapByName.put(new IdentifyableMap(map));
	}
	public int mapsAmount() 
	{
		return this.mapByName.getValues().size();
	}
	public Collection<GameMap> getMapsView()
	{
		return Collections.unmodifiableCollection(this.mapByName.getValues());
	}
	
	private class IdentifyableMap extends GameMap implements Named
	{
		public IdentifyableMap(GameMap map)
		{
			super(map.getName(), map.getSpawnLocations());
		}

		@Override
		public String identifyableBy() 
		{
			return getName();
		}
	}
}