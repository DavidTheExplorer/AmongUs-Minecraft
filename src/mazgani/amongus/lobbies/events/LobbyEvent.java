package mazgani.amongus.lobbies.events;

import org.bukkit.event.Event;

import mazgani.amongus.lobbies.GameLobby;

public abstract class LobbyEvent extends Event
{
	private final GameLobby lobby;
	
	public LobbyEvent(GameLobby lobby) 
	{
		this.lobby = lobby;
	}
	public GameLobby getLobby() 
	{
		return this.lobby;
	}
}
