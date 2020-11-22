package mazgani.amongus.games;

import org.bukkit.entity.Player;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.players.PlayerColor;
import mazgani.amongus.players.Role;

public class GamePlayer
{
	private final Player player;
	private final PlayerColor color;
	private final AUGame game;
	
	private Role role;
	private boolean spectator = false;
	private BasicGameCorpse corpse;
	
	public GamePlayer(Player player, PlayerColor color, AUGame game) 
	{
		this.player = player;
		this.color = color;
		this.game = game;
	}
	public Player getPlayer() 
	{
		return this.player;
	}
	public PlayerColor getColor() 
	{
		return this.color;
	}
	public AUGame getGame() 
	{
		return this.game;
	}
	public Role getRole() 
	{
		return this.role;
	}
	public boolean isSpectator() 
	{
		return this.spectator;
	}
	public void setRole(Role role) 
	{
		if(this.game.getState() != GameState.INIT)
		{
			throw new UnsupportedOperationException("The players' roles can only be determined at the init of the game.");
		}
		this.role = role;
	}
	public void setSpectator() 
	{
		this.spectator = true;
	}
	public BasicGameCorpse getCorpse() 
	{
		return this.corpse;
	}
	public void setCorpse(BasicGameCorpse corpse) 
	{
		this.corpse = corpse;
	}
}