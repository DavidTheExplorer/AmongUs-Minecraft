package dte.amongus.deathcontext;

import org.bukkit.Location;

import dte.amongus.corpses.Corpse;

public class DeathContext 
{
	private final Location deathLocation;
	private Corpse corpse;
	
	public DeathContext(Location deathLocation)
	{
		this.deathLocation = deathLocation;
	}
	public DeathContext(Location deathLocation, Corpse corpse) 
	{
		this(deathLocation);
		
		this.corpse = corpse;
	}
	
	public Location getDeathLocation()
	{
		return this.deathLocation;
	}
	
	public Corpse getCorpse()
	{
		return this.corpse;
	}
}