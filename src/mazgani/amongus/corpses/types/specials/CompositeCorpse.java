package mazgani.amongus.corpses.types.specials;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.Location;

import com.google.common.collect.Lists;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.GameCorpseComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.utilities.CollectionUtilities;

public abstract class CompositeCorpse extends BasicGameCorpse
{
	private final List<BasicGameCorpse> corpses = Lists.newArrayList();

	public CompositeCorpse(GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
	}
	public void addCorpse(BasicGameCorpse corpse) 
	{
		this.corpses.add(corpse);
	}
	public void remove(BasicGameCorpse corpse) 
	{
		this.corpses.remove(corpse);
	}

	@Override
	public <T extends GameCorpseComponent> List<T> getComponents(Class<T> componentClass) 
	{
		return this.corpses.stream()
				.flatMap(corpse -> corpse.getComponents(componentClass).stream())
				.collect(toList());
	}
	
	@Override
	public Queue<GameCorpseComponent> getComponentsView() 
	{
		return this.corpses.stream()
				.flatMap(corpse -> corpse.getComponentsView().stream())
				.collect(toCollection(LinkedList::new));
	}
	protected void forEachCorpse(Consumer<BasicGameCorpse> corpseAction) 
	{
		this.corpses.forEach(corpseAction);
	}
	protected void forEachCorpseThat(Predicate<BasicGameCorpse> corpseFilter, Consumer<BasicGameCorpse> corpseAction) 
	{
		this.corpses.stream()
		.filter(corpseFilter)
		.forEach(corpseAction);
	}

	@Override
	public void initComponents(Location location)
	{
		forEachCorpse(corpse -> corpse.initComponents(location));
	}

	@Override
	public void spawn(Location location) 
	{
		handleDuplicatedComponents().forEach(GameCorpseComponent::spawn);
		
		forEachCorpse(corpse -> corpse.spawn(location));
	}

	@Override
	public void despawn() 
	{
		forEachCorpse(BasicGameCorpse::despawn);
	}
	public Queue<BasicGameCorpse> getCorpsesBySpawnOrder()
	{
		return new LinkedList<>(this.corpses);
	}

	protected Set<GameCorpseComponent> handleDuplicatedComponents()
	{
		return computeDuplicatedComponents().values().stream()
				.flatMap(List::stream)
				.collect(toSet());
	}
	protected Map<Class<? extends GameCorpseComponent>, List<GameCorpseComponent>> computeDuplicatedComponents() 
	{
		List<GameCorpseComponent> corpsesComponents = this.corpses.stream()
				.flatMap(corpse -> corpse.getComponentsView().stream())
				.collect(toList());

		return CollectionUtilities.findAllDuplicates(corpsesComponents).stream().collect(groupingBy(GameCorpseComponent::getClass));
	}
}