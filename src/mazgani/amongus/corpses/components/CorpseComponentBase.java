package mazgani.amongus.corpses.components;

import org.bukkit.ChatColor;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.games.GamePlayer;

public abstract class CorpseComponentBase implements GameCorpseComponent
{
	protected final BasicGameCorpse parentCorpse;
	
	protected CorpseComponentBase(BasicGameCorpse parentCorpse) 
	{
		this.parentCorpse = parentCorpse;
	}
	public BasicGameCorpse getParentCorpse() 
	{
		return this.parentCorpse;
	}
	protected String getColoredPlayerName() 
	{
		GamePlayer whoDied = this.parentCorpse.getWhoDied();
		
		ChatColor diedColor = whoDied.getAUPlayer().getVisibilityManager().getCurrentColor().getColor();
		String diedPlayerName = whoDied.getAUPlayer().getPlayer().getPlayer().getName();
		
		return diedColor + diedPlayerName;
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