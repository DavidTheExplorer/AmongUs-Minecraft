package dte.amongus.deathcontext;

import org.bukkit.Location;

import dte.amongus.corpses.AbstractCorpse;

public class DeathContext 
{
	private final Location deathLocation;
	private AbstractCorpse corpse;
	
	public DeathContext(Location deathLocation)
	{
		this.deathLocation = deathLocation;
	}
	public DeathContext(Location deathLocation, AbstractCorpse corpse) 
	{
		this(deathLocation);
		
		this.corpse = corpse;
	}
	public Location getDeathLocation()
	{
		return this.deathLocation;
	}
	public AbstractCorpse getCorpse()
	{
		return this.corpse;
	}
}