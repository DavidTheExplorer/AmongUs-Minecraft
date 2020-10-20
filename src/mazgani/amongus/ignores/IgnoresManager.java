package mazgani.amongus.ignores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mazgani.amongus.players.AUPlayer;

public class IgnoresManager 
{
	private Map<AUPlayer, Set<AUPlayer>> playersIgnoredLists = new HashMap<>();
	
	public boolean ignore(AUPlayer ignorer, AUPlayer ignored)
	{
		return this.playersIgnoredLists.computeIfAbsent(ignorer, l -> new HashSet<>()).add(ignored);
	}
	public boolean isIgnoring(AUPlayer ignorer, AUPlayer ignored) 
	{
		Set<AUPlayer> ignoreList = this.playersIgnoredLists.get(ignorer);
		
		if(ignoreList == null) 
		{
			return false;
		}
		return ignoreList.contains(ignored);
	}
	public boolean unignore(AUPlayer ignorer, AUPlayer unignored)
	{
		return this.playersIgnoredLists.get(ignorer).remove(unignored);
	}
}
