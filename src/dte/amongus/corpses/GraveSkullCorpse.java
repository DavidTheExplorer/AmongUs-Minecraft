package dte.amongus.corpses;

import org.bukkit.Location;

import dte.amongus.corpses.composite.CompositeCorpse;
import dte.amongus.games.players.Crewmate;

public class GraveSkullCorpse extends CompositeCorpse //implements SkullCorpse, GraveCorpse
{
	private final GraveCorpse graveCorpse;
	private final SkullCorpse skullCorpse;
	
	public GraveSkullCorpse(Crewmate whoDied, Location deathLocation)
	{
		super(whoDied);
		
		this.graveCorpse = new GraveCorpse(whoDied, deathLocation);
		this.skullCorpse = new SkullCorpse(whoDied, deathLocation);
		
		addCorpse(this.graveCorpse);
		addCorpse(this.skullCorpse);
	}
	
	/*@Override
	public Location getSkullLocation() 
	{
		return this.skullCorpse.getSkullLocation();
	}

	@Override
	public Location[] getGraveBlocksLocations() 
	{
		return this.graveCorpse.getGraveBlocksLocations();
	}*/
}