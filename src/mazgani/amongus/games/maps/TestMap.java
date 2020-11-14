package mazgani.amongus.games.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class TestMap extends GameMap
{
	public TestMap() 
	{
		super("Test", 
				new Location(Bukkit.getWorld("world"), -19.700, 64, 242.700),
				new Location(Bukkit.getWorld("world"), 14, 64, 240),
				new Location(Bukkit.getWorld("world"), 13, 64, 242),
				new Location(Bukkit.getWorld("world"), 14, 64, 244),
				new Location(Bukkit.getWorld("world"), -16, 64, 245),
				new Location(Bukkit.getWorld("world"), -18, 84, 244),
				new Location(Bukkit.getWorld("world"), -19, 64, 242),
				new Location(Bukkit.getWorld("world"), -18, 64, 240));
	}
}
