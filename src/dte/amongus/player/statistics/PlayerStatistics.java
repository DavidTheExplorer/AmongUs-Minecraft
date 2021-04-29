package dte.amongus.player.statistics;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import dte.amongus.player.PlayerRole;
import dte.amongus.utils.java.NumberUtils;

public class PlayerStatistics
{
	private final Map<Statistic<?>, Object> generalStats = new HashMap<>();
	private final Map<PlayerRole, Map<Statistic<?>, Object>> roleStats = new HashMap<>();
	
	/*
	 * General Stats
	 */
	public <VT> void set(Statistic<VT> statistic, VT value) 
	{
		this.generalStats.put(statistic, value);
	}
	public <VT> void compute(Statistic<VT> statistic, UnaryOperator<VT> newValueProvider) 
	{
		VT currentValue = get(statistic);
		VT newValue = newValueProvider.apply(currentValue);
		
		set(statistic, newValue);
	}
	public <VT> VT getOrSet(Statistic<VT> statistic, VT defaultValue)
	{
		this.generalStats.putIfAbsent(statistic, defaultValue);

		return get(statistic);
	}
	public <N extends Number> void increment(Statistic<N> statistic) 
	{
		compute(statistic, number -> (N) NumberUtils.increment(number, 1));
	}
	
	@SuppressWarnings("unchecked")
	public <VT> VT get(Statistic<VT> statistic)
	{
		return (VT) this.generalStats.get(statistic);
	}
	
	/*
	 * Role Specific Stats
	 */
	public <VT> void set(PlayerRole role, Statistic<VT> statistic, VT value) 
	{
		getRoleMap(role).put(statistic, value);
	}
	public <VT> void compute(PlayerRole role, Statistic<VT> statistic, UnaryOperator<VT> newValueProvider)
	{
		VT currentValue = get(role, statistic);
		VT newValue = newValueProvider.apply(currentValue);
		
		set(role, statistic, newValue);
	}
	
	public <VT> VT getOrSet(PlayerRole role, Statistic<VT> statistic, VT defaultValue) 
	{
		getRoleMap(role).putIfAbsent(statistic, defaultValue);

		return get(role, statistic);
	}

	@SuppressWarnings("unchecked")
	public <VT> VT get(PlayerRole role, Statistic<VT> statistic) 
	{
		return (VT) getRoleMap(role).get(statistic);
	}
	

	private Map<Statistic<?>, Object> getRoleMap(PlayerRole role)
	{
		return this.roleStats.computeIfAbsent(role, r -> new HashMap<>());
	}
}