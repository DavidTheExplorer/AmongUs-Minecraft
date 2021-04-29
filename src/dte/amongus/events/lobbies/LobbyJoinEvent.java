package dte.amongus.events.lobbies;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import dte.amongus.lobby.AULobby;

public class LobbyJoinEvent extends LobbyEvent
{
	private final Player whoJoined;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public LobbyJoinEvent(AULobby lobby, Player whoJoined)
	{
		super(lobby);
		
		this.whoJoined = whoJoined;
	}
	public Player whoJoined() 
	{
		return this.whoJoined;
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
