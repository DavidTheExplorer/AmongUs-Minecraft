package dte.amongus.deathcontext;

import java.util.Optional;

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
	
	public Optional<Corpse> getCorpse()
	{
		return Optional.ofNullable(this.corpse);
	}
}