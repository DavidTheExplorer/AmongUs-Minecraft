package dte.amongus.corpses.composite.exceptions;

import dte.amongus.corpses.composite.CompositeCorpse;

public class CompositeCorpseException extends RuntimeException
{
	private final CompositeCorpse compositeCorpse;
	
	private static final long serialVersionUID = 8480781160294058309L;
	
	public CompositeCorpseException(CompositeCorpse compositeCorpse, String message) 
	{
		super(message);
		
		this.compositeCorpse = compositeCorpse;
	}
	public CompositeCorpse getCorpse() 
	{
		return this.compositeCorpse;
	}
}