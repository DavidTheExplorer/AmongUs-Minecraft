package dte.amongus.events.games;

import org.bukkit.event.HandlerList;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.AUGamePlayer;

public class BodyReportEvent extends GameEvent
{
	private final AbstractCorpse foundCorpse;
	private final AUGamePlayer reporter;

	private static final HandlerList HANDLERS = new HandlerList();

	public BodyReportEvent(AbstractCorpse foundCorpse, AUGamePlayer reporter) 
	{
		super(reporter.getGame());

		this.foundCorpse = foundCorpse;
		this.reporter = reporter;
	}
	public AbstractCorpse getFoundCorpse() 
	{
		return this.foundCorpse;
	}
	public AUGamePlayer getReporter() 
	{
		return this.reporter;
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