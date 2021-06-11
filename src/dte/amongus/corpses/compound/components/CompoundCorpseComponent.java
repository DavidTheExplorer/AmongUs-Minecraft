package dte.amongus.corpses.compound.components;

import dte.amongus.corpses.compound.CompoundCorpse;

public abstract class CompoundCorpseComponent 
{
	private final CompoundCorpse parentCorpse;
	
	protected CompoundCorpseComponent(CompoundCorpse parentCorpse) 
	{
		this.parentCorpse = parentCorpse;
	}
	public CompoundCorpse getParentCorpse() 
	{
		return this.parentCorpse;
	}
	public abstract void spawn();
	public abstract void despawn();
}