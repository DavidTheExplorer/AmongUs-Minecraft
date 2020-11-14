package mazgani.amongus.ignores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mazgani.amongus.players.AUPlayer;

public class IgnoresManager 
{
	private final Map<AUPlayer, Set<AUPlayer>> playersIgnored = new HashMap<>();
	
	public void ignore(AUPlayer ignorer, AUPlayer ignored)
	{
		this.playersIgnored.computeIfAbsent(ignorer, i -> new HashSet<>()).add(ignored);
	}
	public boolean isIgnoring(AUPlayer ignorer, AUPlayer ignored) 
	{
		Set<AUPlayer> ignoredList = this.playersIgnored.get(ignorer);
		
		if(ignoredList == null)
		{
			return false;
		}
		return ignoredList.contains(ignored);
	}
	public boolean unignore(AUPlayer ignorer, AUPlayer unignored)
	{
		return this.playersIgnored.get(ignorer).remove(unignored);
	}
}