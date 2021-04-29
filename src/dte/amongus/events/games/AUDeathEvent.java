package dte.amongus.events.games;

import org.bukkit.event.HandlerList;

import dte.amongus.deathcontext.DeathContext;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;

public class AUDeathEvent extends GameEvent
{
	private final AUGamePlayer whoDied;
	private final DeathContext context;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public AUDeathEvent(AUGame game, AUGamePlayer whoDied, DeathContext context) 
	{
		super(game);
		
		this.whoDied = whoDied;
		this.context = context;
	}
	public AUGamePlayer whoDied() 
	{
		return this.whoDied;
	}
	public DeathContext getContext() 
	{
		return this.context;
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
