package dte.amongus.events.lobbies;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import dte.amongus.lobby.AULobby;

public abstract class LobbyEvent extends Event
{
	private final AULobby lobby;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public LobbyEvent(AULobby lobby) 
	{
		this.lobby = lobby;
	}
	public AULobby getLobby()
	{
		return this.lobby;
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
