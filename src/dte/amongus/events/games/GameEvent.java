package dte.amongus.events.games;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import dte.amongus.games.AUGame;

public abstract class GameEvent extends Event
{
	private final AUGame game;
	
	private static final HandlerList HANDLERS = new HandlerList();

	public GameEvent(AUGame game) 
	{
		this.game = game;
	}
	public AUGame getGame() 
	{
		return this.game;
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
