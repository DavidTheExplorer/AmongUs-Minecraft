package dte.amongus.corpses.composite.exceptions;

import java.util.List;

import dte.amongus.corpses.composite.CompositeCorpse;
import dte.amongus.corpses.compound.components.CompoundCorpseComponent;

public class DuplicateComponentsException extends CompositeCorpseException
{
	private final List<CompoundCorpseComponent> duplicateComponents;
	
	private static final long serialVersionUID = -6409843137907112239L;
	
	public DuplicateComponentsException(CompositeCorpse compositeCorpse, List<CompoundCorpseComponent> duplicateComponents) 
	{
		super(compositeCorpse, String.format("%d components in the corpse were already in the compsite corpse!", duplicateComponents.size()));
		
		this.duplicateComponents = duplicateComponents;
	}
	
	public List<CompoundCorpseComponent> getDuplicateComponents()
	{
		return this.duplicateComponents;
	}
}