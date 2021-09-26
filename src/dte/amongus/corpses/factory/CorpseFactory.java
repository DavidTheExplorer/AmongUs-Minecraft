package dte.amongus.corpses.factory;

import org.bukkit.Location;

import dte.amongus.corpses.Corpse;
import dte.amongus.games.players.Crewmate;

@FunctionalInterface
public interface CorpseFactory
{
	Corpse generateCorpse(Crewmate whoDied, Location deathLocation);
}