package mazgani.amongus.corpses.types.specials;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import mazgani.amongus.AmongUs;
import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class TemporaryCorpse implements AbstractGameCorpse
{
	private final BasicGameCorpse corpse;
	private final int ticksUntilDespawn;

	protected TemporaryCorpse(BasicGameCorpse corpse, int ticksUntilDespawn) 
	{
		this.corpse = corpse;
		this.ticksUntilDespawn = ticksUntilDespawn;
	}
	public static TemporaryCorpse despawnAfter(BasicGameCorpse corpse, int ticks) 
	{
		return new TemporaryCorpse(corpse, ticks);
	}
	
	@Override
	public GamePlayer getWhoDied() 
	{
		return this.corpse.getWhoDied();
	}

	@Override
	public AUGame getGame() 
	{
		return this.corpse.getGame();
	}
	
	@Override
	public void despawn()
	{
		this.corpse.despawn();
	}
	
	@Override
	public void spawn(Location location) 
	{
		this.corpse.spawn(location);
		
		Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), this::despawn, this.ticksUntilDespawn);
	}
}
