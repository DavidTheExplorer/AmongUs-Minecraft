package mazgani.amongus.lobbies;

import org.bukkit.entity.Player;

import mazgani.amongus.players.PlayerColor;

public class LobbyPlayer 
{
	private final Player player;
	
	private PlayerColor color;
	
	public LobbyPlayer(Player player, PlayerColor color) 
	{
		this.player = player;
		this.color = color;
	}
	public Player getPlayer()
	{
		return this.player;
	}
	public PlayerColor getColor() 
	{
		return this.color;
	}
	public void setColor(PlayerColor color)
	{
		this.color = color;
	}
}
