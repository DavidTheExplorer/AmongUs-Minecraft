package dte.amongus.corpses.composite.exceptions;

import java.util.List;

import dte.amongus.corpses.basic.components.BasicCorpseComponent;
import dte.amongus.corpses.composite.CompositeCorpse;

public class DuplicateComponentsException extends CompositeCorpseException
{
	private final List<BasicCorpseComponent> duplicateComponents;
	
	private static final long serialVersionUID = -6409843137907112239L;
	
	public DuplicateComponentsException(CompositeCorpse compositeCorpse, List<BasicCorpseComponent> duplicateComponents) 
	{
		super(compositeCorpse, String.format("%d components in the corpse were already in the compsite corpse!", duplicateComponents.size()));
		
		this.duplicateComponents = duplicateComponents;
	}
	
	public List<BasicCorpseComponent> getDuplicateComponents()
	{
		return this.duplicateComponents;
	}
}