package dte.amongus.events.lobbies;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import dte.amongus.lobby.AULobby;

public class LobbyFullEvent extends LobbyEvent
{
	private final Player lastJoiner;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public LobbyFullEvent(AULobby lobby, Player lastJoiner) 
	{
		super(lobby);
		
		this.lastJoiner = lastJoiner;
	}
	
	public Player getLastJoiner() 
	{
		return this.lastJoiner;
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
