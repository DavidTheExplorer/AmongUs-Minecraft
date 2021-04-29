package dte.amongus.games.settings;

import dte.amongus.corpses.factory.CorpseFactory;

public class GameSettings 
{
	private int crewmatesAmount, impostorsAmount;
	private CorpseFactory corpseFactory;
	
	public GameSettings(int crewmatesAmount, int impostorsAmount, CorpseFactory corpseFactory) 
	{
		this.crewmatesAmount = crewmatesAmount;
		this.impostorsAmount = impostorsAmount;
		this.corpseFactory = corpseFactory;
	}
	public int crewmatesAmount()
	{
		return this.crewmatesAmount;
	}
	public int impostorsAmount()
	{
		return this.impostorsAmount;
	}
	public CorpseFactory getCorpseFactory()
	{
		return this.corpseFactory;
	}
	public int getPlayersRequired()
	{
		return this.impostorsAmount + this.crewmatesAmount;
	}
	public void setCrewmatesAmount(int crewmatesAmount)
	{
		this.crewmatesAmount = crewmatesAmount;
	}
	public void setImpostorsAmount(int impostorsAmount)
	{
		this.impostorsAmount = impostorsAmount;
	}
	public void setCorpseFactory(CorpseFactory corpsesFactory)
	{
		this.corpseFactory = corpsesFactory;
	}
}