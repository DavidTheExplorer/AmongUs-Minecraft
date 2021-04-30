package dte.amongus.shiptasks.types;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;

public abstract class ProgressionTask extends SimpleShipTask
{
	private final int maxProgression;
	
	public ProgressionTask(String name, String description, AUGame game, int maxProgression) 
	{
		super(name, description, game);
		
		this.maxProgression = maxProgression;
	}
	
	//returns whether the player finished the task after the progression was added
	public boolean addProgression(AUGamePlayer gamePlayer, int amount)
	{
		int newProgression = getProgression(gamePlayer) + amount;
		
		if(newProgression > this.maxProgression) 
		{
			setFinished(gamePlayer);
			removeData(gamePlayer, "Progression");
		}
		else
		{
			setData(gamePlayer, "Progression", newProgression);
		}
		return newProgression == 100;
	}
	public int getProgression(AUGamePlayer gamePlayer) 
	{
		return (Integer) getOrPut(gamePlayer, "Progression", 0);
	}
}