package dte.amongus.corpses.compound.components;

import dte.amongus.corpses.compound.CompoundCorpse;

public abstract class CompoundCorpseComponent 
{
	protected final CompoundCorpse parent;
	
	protected CompoundCorpseComponent(CompoundCorpse parent) 
	{
		this.parent = parent;
	}
	
	public CompoundCorpse getParent() 
	{
		return this.parent;
	}
	
	public abstract void spawn();
	public abstract void despawn();
}