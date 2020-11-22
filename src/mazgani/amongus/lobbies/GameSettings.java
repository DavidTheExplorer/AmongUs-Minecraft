package mazgani.amongus.lobbies;

public class GameSettings 
{
	private int crewmates, impostors;
	
	public GameSettings(int crewmates, int impostors) 
	{
		this.crewmates = crewmates;
		this.impostors = impostors;
	}
	public int crewmatesAmount() 
	{
		return this.crewmates;
	}
	public int impostorsAmount() 
	{
		return this.impostors;
	}
	public int getPlayersRequired()
	{
		return this.impostors + this.crewmates;
	}
}