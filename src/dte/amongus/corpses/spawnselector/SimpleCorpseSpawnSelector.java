package dte.amongus.corpses.spawnselector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ClassUtils;

import dte.amongus.corpses.Corpse;

public class SimpleCorpseSpawnSelector implements CorpseSpawnSelector
{
	private final Map<Class<? extends Corpse>, CorpseSpawnRule[]> corpsesRules = new HashMap<>();
	private final CorpseSpawnRule defaultRule;

	public SimpleCorpseSpawnSelector(CorpseSpawnRule defaultRule) 
	{
		this.defaultRule = defaultRule;
	}
	
	@Override
	public Location getLocation(Location deathLocation, Class<? extends Corpse> corpseClass) 
	{
		CorpseSpawnRule[] spawnRules = this.corpsesRules.getOrDefault(corpseClass, new CorpseSpawnRule[]{this.defaultRule});
		
		return CorpseSpawnRule.compose(spawnRules).apply(deathLocation);
	}

	@Override
	public void setRulesFor(Class<? extends Corpse> corpseClass, CorpseSpawnRule... rules) 
	{
		CorpseSpawnRule[] withParentsRules = ArrayUtils.addAll(rules, getParentsRules(corpseClass));
		
		this.corpsesRules.put(corpseClass, withParentsRules);
	}
	
	private CorpseSpawnRule[] getParentsRules(Class<? extends Corpse> corpseClass)
	{
		return ClassUtils.getAllSuperclasses(corpseClass).stream()
				.map(this.corpsesRules::get)
				.filter(Objects::nonNull)
				.flatMap(Arrays::stream)
				.toArray(CorpseSpawnRule[]::new);
	}
}