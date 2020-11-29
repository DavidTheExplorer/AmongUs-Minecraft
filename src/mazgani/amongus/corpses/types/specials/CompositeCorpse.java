package mazgani.amongus.corpses.types.specials;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static mazgani.amongus.utilities.CollectionUtilities.findAllDuplicates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.Location;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.GameCorpseComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public abstract class CompositeCorpse extends BasicGameCorpse
{
	private final List<BasicGameCorpse> corpses = new ArrayList<>();

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
		computeUniqueComponents().forEach(GameCorpseComponent::spawn);

		//forEachCorpse(corpse -> corpse.spawn(location));
	}

	@Override
	public void despawn() 
	{
		forEachCorpse(BasicGameCorpse::despawn);
	}
	public List<BasicGameCorpse> getCorpsesBySpawnOrderView()
	{
		return Collections.unmodifiableList(this.corpses);
	}

	@Override
	public <T extends GameCorpseComponent> List<T> getComponents(Class<T> componentClass) 
	{
		return this.corpses.stream()
				.flatMap(corpse -> corpse.getComponents(componentClass).stream())
				.collect(toList());
	}

	@Override
	public List<GameCorpseComponent> getComponentsView() 
	{
		return this.corpses.stream()
				.flatMap(corpse -> corpse.getComponentsView().stream())
				.collect(collectingAndThen(toList(), Collections::unmodifiableList));
	}

	public Set<GameCorpseComponent> computeUniqueComponents()
	{
		return computeDuplicateComponents().values().stream()
				.flatMap(List::stream)
				.collect(toSet());
	}
	public Map<Class<? extends GameCorpseComponent>, List<GameCorpseComponent>> computeDuplicateComponents() 
	{
		List<GameCorpseComponent> components = this.corpses.stream()
				.flatMap(corpse -> corpse.getComponentsView().stream())
				.collect(toList());

		return findAllDuplicates(components).stream().collect(groupingBy(GameCorpseComponent::getClass));
	}
}