package dte.amongus.sabotages.composite;

import java.util.LinkedHashSet;
import java.util.Set;

import dte.amongus.sabotages.Sabotage;

public class CompositeSabotage implements Sabotage
{
	private final Set<Sabotage> sabotages = new LinkedHashSet<>();
	
	@Override
	public void activate() 
	{
		this.sabotages.forEach(Sabotage::activate);
	}

	@Override
	public void disable() 
	{
		this.sabotages.forEach(Sabotage::disable);
	}
	
	public void addSabotage(Sabotage sabotage) 
	{
		this.sabotages.add(sabotage);
	}
}
