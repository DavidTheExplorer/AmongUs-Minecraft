package dte.amongus.games.players;

import java.util.Optional;

import org.bukkit.entity.Player;

import dte.amongus.deathcontext.DeathContext;
import dte.amongus.games.AUGame;
import dte.amongus.player.PlayerRole;

public abstract class AUGamePlayer
{
	private final Player player;
	private final AUGame game;
	private final PlayerRole role;
	private DeathContext deathContext;
	
	protected AUGamePlayer(Player player, AUGame game, PlayerRole role) 
	{
		this.player = player;
		this.game = game;
		this.role = role;
	}
	
	public Player getPlayer() 
	{
		return this.player;
	}
	
	public AUGame getGame()
	{
		return this.game;
	}
	
	public PlayerRole getRole() 
	{
		return this.role;
	}
	
	public boolean isDead() 
	{
		return this.deathContext != null;
	}
	
	public Optional<DeathContext> getDeathContext()
	{
		return Optional.ofNullable(this.deathContext);
	}
	
	public void setDead(DeathContext context) 
	{
		this.deathContext = context;
	}
}