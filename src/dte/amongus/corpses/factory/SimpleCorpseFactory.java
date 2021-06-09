package dte.amongus.corpses.factory;

import org.bukkit.Location;

import dte.amongus.corpses.Corpse;
import dte.amongus.corpses.GraveSkullCorpse;
import dte.amongus.games.players.Crewmate;

public class SimpleCorpseFactory implements CorpseFactory
{
	@Override
	public Corpse generateCorpse(Crewmate whoDied, Location deathLocation)
	{
		return new GraveSkullCorpse(whoDied, deathLocation);
	}
}
