package dte.amongus.test.corpses.grave;

import org.bukkit.Location;

import dte.amongus.test.corpses.BasicCorpse;

public class SimpleGraveCorpse extends BasicCorpse implements GraveCorpse
{
	private final String type;
	
	public SimpleGraveCorpse(Location deathLocation, String type)
	{
		super(deathLocation);
		
		this.type = type;
	}
	
	@Override
	public String getType()
	{
		return this.type;
	}
	
	@Override
	public void spawn() 
	{
		System.out.println(String.format("Spawned a %s Grave!", this.type));
	}
}