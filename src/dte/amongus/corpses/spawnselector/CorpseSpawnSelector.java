package dte.amongus.corpses.spawnselector;

import org.bukkit.Location;

import dte.amongus.corpses.AbstractCorpse;

public interface CorpseSpawnSelector
{
	Location getLocation(Location deathLocation, Class<? extends AbstractCorpse> corpseClass);
	void setRulesFor(Class<? extends AbstractCorpse> corpseClass, CorpseSpawnRule... rules);
}