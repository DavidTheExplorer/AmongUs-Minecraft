package dte.amongus.test.corpses.composite;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import dte.amongus.test.corpses.AbstractCorpse;

public class CompositeCorpse implements AbstractCorpse
{
	private final Location deathLocation;
	private final List<AbstractCorpse> corpses = new ArrayList<>();
	
	public CompositeCorpse(Location deathLocation) 
	{
		this.deathLocation = deathLocation;
	}
	
	public void addCorpse(AbstractCorpse corpse) 
	{
		this.corpses.add(corpse);
	}
	
	@Override
	public Location getDeathLocation() 
	{
		return this.deathLocation;
	}
	
	@Override
	public void spawn()
	{
		this.corpses.forEach(AbstractCorpse::spawn);
	}
}