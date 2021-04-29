package dte.amongus.test.corpses;

import org.bukkit.Location;

public abstract class BasicCorpse implements AbstractCorpse
{
	private final Location deathLocation;
	
	public BasicCorpse(Location deathLocation) 
	{
		this.deathLocation = deathLocation;
	}

	@Override
	public Location getDeathLocation() 
	{
		return this.deathLocation;
	}
}