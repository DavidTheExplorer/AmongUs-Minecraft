package dte.amongus.events.games;

import dte.amongus.deathcontext.ImpostorKillContext;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;

public class ImpostorKillEvent extends AUDeathEvent
{
	public ImpostorKillEvent(AUGame game, Crewmate whoDied, ImpostorKillContext context) 
	{
		super(game, whoDied, context);
	}
	
	@Override
	public Crewmate whoDied()
	{
		return (Crewmate) super.whoDied();
	}
	
	@Override
	public ImpostorKillContext getContext() 
	{
		return (ImpostorKillContext) super.getContext();
	}
}