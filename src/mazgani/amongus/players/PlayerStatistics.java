package mazgani.amongus.players;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import mazgani.amongus.enums.Role;

public class PlayerStatistics
{
	private Map<String, Object> generalStats = new HashMap<>();
	private Map<Role, Map<String, Object>> statsByRole = new HashMap<>();
	
	public void addCurse() 
	{
		
	}
	public void addWin(Role role)
	{
		put(role, "Wins", 0, currentWins -> (int) currentWins +1);
	}
	public int getWins(Role role)
	{
		return (int) getOrPut(role, "Wins", 0);
	}
	private void put(Role role, String statistic, Object defaultIfAbsent, UnaryOperator<Object> incrementor) 
	{
		Object currentValue = getOrPut(role, statistic, defaultIfAbsent);
		Object newValue = incrementor.apply(currentValue);
		
		this.statsByRole.computeIfAbsent(role, r -> new HashMap<>()).put(statistic, newValue);
	}
	private Object getOrPut(Role role, String statistic, Object defaultIfAbsent) 
	{
		if(!this.statsByRole.containsKey(role)) 
		{
			return false;
		}
		Map<String, Object> roleStats = this.statsByRole.computeIfAbsent(role, r -> new HashMap<>());
		
		return roleStats.computeIfAbsent(statistic, s -> defaultIfAbsent);
	}
}