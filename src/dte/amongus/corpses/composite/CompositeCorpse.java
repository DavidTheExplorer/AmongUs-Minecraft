package dte.amongus.corpses.composite;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.BasicCorpseComponent;
import dte.amongus.corpses.composite.exceptions.CompositeCorpseException;
import dte.amongus.corpses.composite.exceptions.DuplicateComponentsException;
import dte.amongus.games.players.Crewmate;

public class CompositeCorpse extends BasicCorpse
{
	private final Set<BasicCorpse> corpses = new HashSet<>();
	
	public CompositeCorpse(Crewmate whoDied)
	{
		super(whoDied);
	}
	
	@Override
	public Set<BasicCorpseComponent> getComponents() 
	{
		return this.corpses.stream()
				.flatMap(corpse -> corpse.getComponents().stream())
				.collect(toSet());
	}
	
	public void addCorpse(BasicCorpse corpse) throws CompositeCorpseException
	{
		if(this.corpses.contains(corpse)) 
			throw new CompositeCorpseException(this, "The provided corpse is already contained within the composite corpse!");
		
		verifyNoDuplicates(corpse);
		
		this.corpses.add(corpse);
		corpse.getComponents().forEach(this::addComponent);
	}
	
	public Set<BasicCorpse> getCorpses()
	{
		return this.corpses;
	}
	
	private void verifyNoDuplicates(BasicCorpse corpse) throws CompositeCorpseException
	{
		List<BasicCorpseComponent> duplicateComponents = corpse.getComponents().stream()
				.filter(component -> getComponents().contains(component))
				.collect(toList());
		
		if(!duplicateComponents.isEmpty())
			throw new DuplicateComponentsException(this, duplicateComponents);
	}
	
	/*public Map<Class<? extends BasicCorpseComponent>, List<BasicCorpseComponent>> computeDuplicateComponents()
	{
		return CollectionUtils.findAllDuplicates(getComponents()).stream().collect(groupingBy(BasicCorpseComponent::getClass));
	}*/
}