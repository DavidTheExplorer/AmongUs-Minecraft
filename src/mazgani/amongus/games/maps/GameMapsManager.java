package mazgani.amongus.games.maps;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameMapsManager 
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
	public Collection<GameMap> getMapsView()
	{
		return Collections.unmodifiableCollection(this.mapByName.values());
	}
}
