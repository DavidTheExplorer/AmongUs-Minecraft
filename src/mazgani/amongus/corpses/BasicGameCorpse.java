package mazgani.amongus.corpses;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import mazgani.amongus.corpses.components.GameCorpseComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.events.BodyReportEvent;
import mazgani.amongus.utilities.BlockUtilities;

public abstract class BasicGameCorpse implements AbstractGameCorpse
{
	private final GamePlayer whoDied;
	private final AUGame game;
	private final List<GameCorpseComponent> components = new ArrayList<>();
	
	public BasicGameCorpse(GamePlayer whoDied, AUGame game)
	{
		this.whoDied = whoDied;
		this.game = game;
	}
	
	@Override
	public GamePlayer getWhoDied()
	{
		return this.whoDied;
	}
	
	@Override
	public AUGame getGame() 
	{
		return this.game;
	}
	public List<GameCorpseComponent> getComponents()
	{
		return this.components;
	}
	public void addComponent(GameCorpseComponent component) 
	{
		this.components.add(component);
	}
	public void report(GamePlayer reporter) 
	{
		Bukkit.getPluginManager().callEvent(new BodyReportEvent(reporter, this, getGame()));
	}
	
	/**
	 * Returns the closest appropriate location to summon this body at, <b>depending</b> on the implementation of <i>BasicGameCorpse</i>.
	 * The default implementation covers the case when the corpse would spawn mid-air, but again it wouldn't necessarily happen.
	 * 
	 * @param deathLocation The original death location, might be returned.
	 * @return The closest appropriate location to summon this body at.
	 */
	public Location computeBestLocation(Location deathLocation) 
	{
		Location bestLocation;
		
		//if the player died mid-air, avoid spawning the corpse mid-air
		bestLocation = BlockUtilities.computeLowestBlock(deathLocation).getLocation();
		
		return bestLocation;
	}
	
	/** Despawns all the components this corpse consists of. */
	@Override
	public void despawn()
	{
		this.components.forEach(GameCorpseComponent::despawn);
	}
	
	/**
	 * Spawns this corpse at the closest appropriate location to the provided {@code location}.
	 * <p>
	 * That location is computed at {@link #computeBestLocation(Location)}.
	 * 
	 * @param location the death location.
	 */
	@Override
	public void spawn(Location deathLocation) 
	{
		initComponents(computeBestLocation(deathLocation));
		
		this.components.forEach(GameCorpseComponent::spawn);
	}
	public abstract void initComponents(Location location);
}