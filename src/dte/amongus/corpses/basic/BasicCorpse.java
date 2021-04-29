package dte.amongus.corpses.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.corpses.basic.components.BasicCorpseComponent;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.utils.java.IterableUtils;

public class BasicCorpse implements AbstractCorpse, Iterable<BasicCorpseComponent>
{
	private final AUGamePlayer whoDied;
	private final Set<BasicCorpseComponent> components = new HashSet<>(); //a corpse consists of components(blocks, holograms, etc)

	public BasicCorpse(AUGamePlayer whoDied)
	{
		this.whoDied = whoDied;
	}
	
	@Override
	public AUGamePlayer whoDied()
	{
		return this.whoDied;
	}

	@Override
	public void spawn()
	{
		this.components.forEach(BasicCorpseComponent::spawn);
	}

	@Override
	public void despawn()
	{
		this.components.forEach(BasicCorpseComponent::despawn);
	}

	public void addComponent(BasicCorpseComponent component) 
	{
		this.components.add(component);
	}
	public Set<BasicCorpseComponent> getComponents()
	{
		return Collections.unmodifiableSet(this.components);
	}
	public <C extends BasicCorpseComponent> Set<C> getComponents(Class<C> componentClass)
	{
		return new HashSet<>(IterableUtils.getElementsOf(componentClass, this.components));
	}
	
	@Override
	public Iterator<BasicCorpseComponent> iterator() 
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
		
		BasicCorpse other = (BasicCorpse) object;
		
		return Objects.equals(this.whoDied.getPlayer().getUniqueId(), other.whoDied.getPlayer().getUniqueId());
	}
}