package dte.amongus.corpses.basic.components;

import dte.amongus.corpses.basic.BasicCorpse;

public abstract class BasicCorpseComponent 
{
	private final BasicCorpse parentCorpse;
	
	protected BasicCorpseComponent(BasicCorpse parentCorpse) 
	{
		this.parentCorpse = parentCorpse;
	}
	public BasicCorpse getParentCorpse() 
	{
		return this.parentCorpse;
	}
	public abstract void spawn();
	public abstract void despawn();
}