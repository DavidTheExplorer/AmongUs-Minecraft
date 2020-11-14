package mazgani.amongus.corpses.types.specials;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import org.bukkit.Location;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public abstract class CompositeCorpse extends BasicGameCorpse
{
	private final Queue<BasicGameCorpse> corpses = new LinkedList<>();

	public CompositeCorpse(GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
	}
	public void setSpawnOrder(BasicGameCorpse... corpses) 
	{
		this.corpses.addAll(Arrays.asList(corpses));
	}
	public void remove(BasicGameCorpse corpse) 
	{
		this.corpses.remove(corpse);
	}
	protected void forCorpses(Consumer<BasicGameCorpse> corpseAction) 
	{
		this.corpses.forEach(corpseAction);
	}
	
	@Override
	public void initComponents(Location location)
	{
		forCorpses(corpse -> corpse.initComponents(corpse.computeBestLocation(location)));
	}
	
	@Override
	public void spawn(Location location) 
	{
		forCorpses(corpse -> corpse.spawn(location));
	}
	
	@Override
	public void despawn() 
	{
		forCorpses(BasicGameCorpse::despawn);
	}
	public Queue<BasicGameCorpse> getCorpsesBySpawnOrder()
	{
		return new LinkedList<>(this.corpses);
	}
	
	/*public abstract Set<CorpseComponent> handleDuplicatedComponents(Set<CorpseComponent> duplicatedComponents)
	{
		return ?
	}*/
}