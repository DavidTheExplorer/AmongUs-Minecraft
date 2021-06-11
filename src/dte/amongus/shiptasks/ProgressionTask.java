package dte.amongus.shiptasks;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.type.TaskType;

public abstract class ProgressionTask extends SimpleShipTask
{
	private final int maxProgression;
	
	protected ProgressionTask(String name, String description, TaskType type, AUGame game, int maxProgression) 
	{
		super(name, description, type, game);
		
		this.maxProgression = maxProgression;
	}
	
	public boolean addProgression(Crewmate crewmate, int amount)
	{
		int newProgression = getProgression(crewmate) + amount;
		
		if(newProgression > this.maxProgression) 
		{
			crewmate.addFinishedTask(this);
			removeData(crewmate, "Progression");
		}
		else
		{
			setData(crewmate, "Progression", newProgression);
		}
		return newProgression == 100;
	}
	
	public int getProgression(Crewmate crewmate) 
	{
		return (Integer) getOrPut(crewmate, "Progression", 0);
	}
}