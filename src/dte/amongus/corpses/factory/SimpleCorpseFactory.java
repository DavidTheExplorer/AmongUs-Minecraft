package dte.amongus.corpses.factory;

import org.bukkit.Location;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.corpses.GraveSkullCorpse;
import dte.amongus.games.players.Crewmate;

public class SimpleCorpseFactory implements CorpseFactory
{
	@Override
	public AbstractCorpse generateCorpse(Crewmate whoDied, Location deathLocation)
	{
		return new GraveSkullCorpse(whoDied, deathLocation);
	}
}
