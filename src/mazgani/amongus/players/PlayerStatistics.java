package mazgani.amongus.players;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class PlayerStatistics
{
	private final Map<String, Object> generalStats = new HashMap<>();
	private final Map<GameRole, Map<String, Object>> roleStats = new HashMap<>();
	
	public void addCurse() 
	{
		
	}
	public void addWin(GameRole role)
	{
		put(role, "Wins", 0, currentWins -> (int) currentWins +1);
	}
	public int getWins(GameRole role)
	{
		return (int) getOrPut(role, "Wins", 0);
	}
	private void put(GameRole role, String statistic, Object defaultIfAbsent, UnaryOperator<Object> incrementor) 
	{
		Object currentValue = getOrPut(role, statistic, defaultIfAbsent);
		Object newValue = incrementor.apply(currentValue);
		
		this.roleStats.computeIfAbsent(role, r -> new HashMap<>()).put(statistic, newValue);
	}
	private Object getOrPut(GameRole role, String statistic, Object defaultIfAbsent) 
	{
		if(!this.roleStats.containsKey(role)) 
		{
			return false;
		}
		Map<String, Object> roleStats = this.roleStats.computeIfAbsent(role, r -> new HashMap<>());
		
		return roleStats.computeIfAbsent(statistic, s -> defaultIfAbsent);
	}
}