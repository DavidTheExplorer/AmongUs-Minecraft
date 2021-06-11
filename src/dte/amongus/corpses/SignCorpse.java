package dte.amongus.corpses;

import org.bukkit.Location;
import org.bukkit.Material;

import dte.amongus.corpses.compound.CompoundCorpse;
import dte.amongus.corpses.compound.components.concretes.DeathSignComponent;
import dte.amongus.games.players.Crewmate;

public class SignCorpse extends CompoundCorpse
{
	public SignCorpse(Crewmate whoDied, Location deathLocation, Material signMaterial) 
	{
		super(whoDied);
		
		addComponent(new DeathSignComponent(this, deathLocation.getBlock(), signMaterial));
	}
}