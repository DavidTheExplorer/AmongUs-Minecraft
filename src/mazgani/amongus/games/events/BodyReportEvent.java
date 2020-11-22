package mazgani.amongus.games.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class BodyReportEvent extends Event
{
	private final GamePlayer reporter;
	private final AbstractGameCorpse corpseFound;
	private final AUGame game;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public BodyReportEvent(GamePlayer reporter, AbstractGameCorpse corpseFound, AUGame game) 
	{
		this.reporter = reporter;
		this.corpseFound = corpseFound;
		this.game = game;
	}
	public GamePlayer getReporter() 
	{
		return this.reporter;
	}
	public AbstractGameCorpse getCorpseFound() 
	{
		return this.corpseFound;
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