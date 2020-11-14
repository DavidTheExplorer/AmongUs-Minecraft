package mazgani.amongus.lobbies.events;

import org.bukkit.event.HandlerList;

import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbyPlayer;

public class LobbyFullEvent extends LobbyEvent
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private final LobbyPlayer lastJoined;
	
	public LobbyFullEvent(GameLobby lobby, LobbyPlayer lastJoined) 
	{
		super(lobby);
		
		this.lastJoined = lastJoined;
	}
	
	@Override
    public HandlerList getHandlers() 
	{
        return HANDLERS;
    }
    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }
}
