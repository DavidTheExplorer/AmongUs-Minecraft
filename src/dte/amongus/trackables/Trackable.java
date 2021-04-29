package dte.amongus.trackables;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface Trackable
{
	Location getLocation();
	
	public static Trackable of(Entity entity) 
	{
		return entity::getLocation;
	}
}