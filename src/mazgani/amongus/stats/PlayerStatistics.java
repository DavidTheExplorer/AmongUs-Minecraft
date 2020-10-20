package mazgani.amongus.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
		Function<Object, Object> incrementor = current -> (int) current +1;
		
		add(role, "Wins", 0, incrementor);
	}
	public int getWins(Role role) 
	{
		return (int) get(role, "Wins", 0);
	}
	
	private void add(Role role, String statistic, Object defaultIfAbsent, Function<Object, Object> incrementor) 
	{
		Object currentValue = get(role, statistic, defaultIfAbsent);
		Object newValue = incrementor.apply(currentValue);
		
		this.statsByRole.computeIfAbsent(role, r -> new HashMap<>()).put(statistic, newValue);
	}
	private Object get(Role role, String statistic, Object defaultIfAbsent) 
	{
		Map<String, Object> roleStats = this.statsByRole.computeIfAbsent(role, r -> new HashMap<>());
		
		return roleStats.computeIfAbsent(statistic, s -> defaultIfAbsent);
	}
}
