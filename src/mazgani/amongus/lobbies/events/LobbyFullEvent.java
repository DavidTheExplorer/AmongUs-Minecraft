package mazgani.amongus.lobbies.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import mazgani.amongus.lobbies.GameLobby;

public class LobbyFullEvent extends LobbyEvent
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private final Player lastJoined;
	
	public LobbyFullEvent(GameLobby lobby, Player lastJoined) 
	{
		super(lobby);
		
		this.lastJoined = lastJoined;
	}
	public Player getLastJoined() 
	{
		return this.lastJoined;
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
