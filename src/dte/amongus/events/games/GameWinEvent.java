package dte.amongus.events.games;

import org.bukkit.event.HandlerList;

import dte.amongus.games.AUGame;
import dte.amongus.player.PlayerRole;

public class GameWinEvent extends GameEvent
{
	private final PlayerRole winnerRole;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public GameWinEvent(AUGame game, PlayerRole winnerRole) 
	{
		super(game);
		
		this.winnerRole = winnerRole;
	}
	
	public PlayerRole getWinnerType() 
	{
		return this.winnerRole;
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