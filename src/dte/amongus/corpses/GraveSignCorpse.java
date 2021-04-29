package dte.amongus.corpses;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.corpses.composite.CompositeCorpse;
import dte.amongus.games.players.AUGamePlayer;

public class GraveSignCorpse extends CompositeCorpse
{
	public GraveSignCorpse(AUGamePlayer whoDied, Location deathLocation, Material signMaterial, String... signLines)
	{
		super(whoDied);
		
		addCorpse(new GraveCorpse(whoDied, deathLocation));
		addCorpse(new SignCorpse(whoDied, deathLocation, signMaterial, signLines));
	}
}