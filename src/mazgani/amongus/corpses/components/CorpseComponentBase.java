package mazgani.amongus.corpses.components;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.corpses.BasicGameCorpse;

public abstract class CorpseComponentBase implements GameCorpseComponent
{
	protected final BasicGameCorpse corpse;
	
	protected CorpseComponentBase(BasicGameCorpse corpse) 
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
		return getLocation().hashCode();
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
		
		return getLocation().equals(otherComponent.getLocation());
	}
}