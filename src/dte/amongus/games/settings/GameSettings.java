package dte.amongus.games.settings;

import dte.amongus.corpses.factory.CorpseFactory;

public class GameSettings 
{
	private int crewmatesRequired, impostorsRequired;
	private CorpseFactory corpseFactory;
	
	public GameSettings(int crewmatesRequired, int impostorsRequired, CorpseFactory corpseFactory) 
	{
		this.crewmatesRequired = crewmatesRequired;
		this.impostorsRequired = impostorsRequired;
		this.corpseFactory = corpseFactory;
	}
	
	public int getCrewmatesRequired()
	{
		return this.crewmatesRequired;
	}
	
	public int getImpostorsRequired()
	{
		return this.impostorsRequired;
	}
	
	public CorpseFactory getCorpseFactory()
	{
		return this.corpseFactory;
	}
	
	public int getPlayersRequired()
	{
		return this.impostorsRequired + this.crewmatesRequired;
	}
	
	public void setCrewmatesRequired(int crewmatesRequired)
	{
		this.crewmatesRequired = crewmatesRequired;
	}
	
	public void setImpostorsRequired(int impostorsRequired)
	{
		this.impostorsRequired = impostorsRequired;
	}
	
	public void setCorpseFactory(CorpseFactory corpsesFactory)
	{
		this.corpseFactory = corpsesFactory;
	}
}