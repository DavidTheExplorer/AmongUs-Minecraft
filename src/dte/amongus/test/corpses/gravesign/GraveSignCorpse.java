package dte.amongus.test.corpses.gravesign;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.test.corpses.composite.CompositeCorpse;
import dte.amongus.test.corpses.grave.GraveCorpse;
import dte.amongus.test.corpses.sign.SignCorpse;

public class GraveSignCorpse extends CompositeCorpse implements GraveCorpse, SignCorpse
{
	private final GraveCorpse graveCorpse;
	private final SignCorpse signCorpse;

	public GraveSignCorpse(Location deathLocation, GraveCorpse graveCorpse, SignCorpse signCorpse) 
	{
		super(deathLocation);
		
		addCorpse(this.graveCorpse = graveCorpse);
		addCorpse(this.signCorpse = signCorpse);
	}
	
	@Override
	public String getType() //from GraveCorpse interface
	{
		return this.graveCorpse.getType();
	}
	
	@Override
	public Material getSignMaterial() //from SignCorpse interface
	{
		return this.signCorpse.getSignMaterial();
	}
}