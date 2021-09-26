package dte.amongus.corpses;

import dte.amongus.games.players.Crewmate;

public abstract class AbstractCorpse implements Corpse
{
	protected final Crewmate whoDied;
	
	public AbstractCorpse(Crewmate whoDied)
	{
		this.whoDied = whoDied;
	}
	
	@Override
	public Crewmate whoDied()
	{
		return this.whoDied;
	}
}
