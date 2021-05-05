package dte.amongus.corpses.factory;

import org.bukkit.Location;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.Crewmate;

public interface CorpseFactory
{
	AbstractCorpse generateCorpse(Crewmate whoDied, Location deathLocation);
}