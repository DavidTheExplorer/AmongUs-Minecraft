package dte.amongus.ignore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dte.amongus.player.AUPlayer;

public class IgnoreService
{
	private final Map<AUPlayer, Set<AUPlayer>> playersIgnores = new HashMap<>();
	
	private static final Set<AUPlayer> DUMMY_LIST = new HashSet<>();
	
	public void ignore(AUPlayer ignorer, AUPlayer ignored) 
	{
		this.playersIgnores.computeIfAbsent(ignorer, u -> new HashSet<>()).add(ignored);
	}
	
	public boolean isIgnored(AUPlayer ignorer, AUPlayer ignored) 
	{
		return this.playersIgnores.getOrDefault(ignorer, DUMMY_LIST).contains(ignored);
	}
	
	public void unIgnore(AUPlayer ignorer, AUPlayer ignored) 
	{
		this.playersIgnores.getOrDefault(ignorer, DUMMY_LIST).remove(ignored);
	}
}