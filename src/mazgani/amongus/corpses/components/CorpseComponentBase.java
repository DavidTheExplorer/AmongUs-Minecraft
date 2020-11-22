package mazgani.amongus.corpses.components;

import mazgani.amongus.corpses.AbstractGameCorpse;

public abstract class CorpseComponentBase implements GameCorpseComponent
{
	protected final AbstractGameCorpse corpse;
	
	protected CorpseComponentBase(AbstractGameCorpse corpse) 
	{
		this.corpse = corpse;
	}
	public AbstractGameCorpse getCorpse() 
	{
		return this.corpse;
	}
	
	@Override
	public int hashCode()
	{
		return getCurrentLocation().hashCode();
	}
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(getClass() != object.getClass())
			return false;
		
		CorpseComponentBase otherComponent = (CorpseComponentBase) object;
		
		return getCurrentLocation().equals(otherComponent.getCurrentLocation());
	}
}