package mazgani.amongus.games.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mazgani.amongus.players.GameRole;

public class GameWinEvent extends Event
{
	private final GameRole winningRole;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public GameWinEvent(GameRole winningRole) 
	{
		this.winningRole = winningRole;
	}
	public GameRole getWinningRole() 
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