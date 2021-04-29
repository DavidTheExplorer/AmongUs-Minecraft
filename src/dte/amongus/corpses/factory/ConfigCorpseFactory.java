package dte.amongus.corpses.factory;

import org.bukkit.Location;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.AUGamePlayer;

public class ConfigCorpseFactory implements CorpseFactory
{
	@Override
	public AbstractCorpse generateCorpse(AUGamePlayer whoDied, Location deathLocation)
	{
		return null;
	}
}