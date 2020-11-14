package mazgani.amongus.games.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mazgani.amongus.players.Role;

public class GameWinEvent extends Event
{
	private final Role winningRole;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public GameWinEvent(Role winningRole) 
	{
		this.winningRole = winningRole;
	}
	public Role getWinningRole() 
	{
		return this.winningRole;
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
