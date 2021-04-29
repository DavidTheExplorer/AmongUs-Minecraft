package dte.amongus.corpses.factory;

import org.bukkit.Location;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.AUGamePlayer;

public interface CorpseFactory
{
	AbstractCorpse generateCorpse(AUGamePlayer whoDied, Location deathLocation);
}