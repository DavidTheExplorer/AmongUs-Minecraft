package dte.amongus.events.games;

import org.bukkit.event.HandlerList;

import dte.amongus.games.AUGame;

public class GameStartEvent extends GameEvent
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	public GameStartEvent(AUGame game) 
	{
		super(game);
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
