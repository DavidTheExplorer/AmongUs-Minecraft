package dte.amongus.corpses.spawnselector;

import org.bukkit.Location;

import dte.amongus.corpses.Corpse;

public interface CorpseSpawnSelector
{
	Location getLocation(Location deathLocation, Class<? extends Corpse> corpseClass);
	
	void setRulesFor(Class<? extends Corpse> corpseClass, CorpseSpawnRule... rules);
}