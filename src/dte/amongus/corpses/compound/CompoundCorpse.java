package dte.amongus.corpses.compound;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.corpses.compound.components.CompoundCorpseComponent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.utils.java.IterableUtils;

public class CompoundCorpse extends AbstractCorpse implements Iterable<CompoundCorpseComponent>
{
	private final Set<CompoundCorpseComponent> components = new HashSet<>(); //a compound-corpse consists of components(blocks, holograms, etc)

	public CompoundCorpse(Crewmate whoDied)
	{
		super(whoDied);
	}
	
	@Override
	public void spawn()
	{
		this.components.forEach(CompoundCorpseComponent::spawn);
	}

	@Override
	public void despawn()
	{
		this.components.forEach(CompoundCorpseComponent::despawn);
	}

	public void addComponent(CompoundCorpseComponent component) 
	{
		this.components.add(component);
	}
	
	public Set<CompoundCorpseComponent> getComponents()
	{
		return new HashSet<>(this.components);
	}
	
	public <C extends CompoundCorpseComponent> Set<C> getComponents(Class<C> componentClass)
	{
		return new HashSet<>(IterableUtils.getElementsOf(componentClass, this.components));
	}
	
	@Override
	public Iterator<CompoundCorpseComponent> iterator() 
	{
		return this.components.iterator();
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(this.whoDied.getPlayer().getUniqueId());
	}

	@Override
	public boolean equals(Object object)
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(getClass() != object.getClass())
			return false;
		
		CompoundCorpse other = (CompoundCorpse) object;
		
		return Objects.equals(this.whoDied.getPlayer().getUniqueId(), other.whoDied.getPlayer().getUniqueId());
	}
}