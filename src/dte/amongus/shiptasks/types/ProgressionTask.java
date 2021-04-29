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
	
	//returns if after the progression was added, the player finished the task
	public boolean addProgression(AUGamePlayer gamePlayer, int amount)
	{
		int newProgression = getProgression(gamePlayer) + amount;
		
		//if the new progression is enough to finish the task, mark the player as a finisher
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