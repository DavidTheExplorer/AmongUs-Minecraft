package mazgani.amongus.lobbies;

import mazgani.amongus.games.corpsesfactory.GameCorpsesFactory;
import mazgani.amongus.games.corpsesfactory.SimpleCorpsesFactory;

public class GameSettings 
{
	private int crewmatesAmount, impostorsAmount;
	private GameCorpsesFactory corpsesFactory;
	
	private static final GameCorpsesFactory DEFAULT_CORPSES_FACTORY = new SimpleCorpsesFactory();
	
	public GameSettings(int crewmatesAmount, int impostorsAmount) 
	{
		this(crewmatesAmount, impostorsAmount, DEFAULT_CORPSES_FACTORY);
	}
	public GameSettings(int crewmatesAmount, int impostorsAmount, GameCorpsesFactory corpsesFactory) 
	{
		this.crewmatesAmount = crewmatesAmount;
		this.impostorsAmount = impostorsAmount;
		this.corpsesFactory = corpsesFactory;
	}
	public int crewmatesAmount() 
	{
		return this.crewmatesAmount;
	}
	public int impostorsAmount()
	{
		return this.impostorsAmount;
	}
	public GameCorpsesFactory getCorpsesFactory() 
	{
		return this.corpsesFactory;
	}
	public int getPlayersRequired()
	{
		return this.impostorsAmount + this.crewmatesAmount;
	}
}