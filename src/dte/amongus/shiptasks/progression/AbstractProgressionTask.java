package dte.amongus.shiptasks.progression;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.AbstractShipTask;
import dte.amongus.shiptasks.type.TaskType;

public abstract class AbstractProgressionTask extends AbstractShipTask implements ProgressionShipTask
{
	private final int maxProgression;
	
	protected AbstractProgressionTask(String name, String description, TaskType type, AUGame game, int maxProgression) 
	{
		super(name, description, type, game);
		
		this.maxProgression = maxProgression;
	}
	
	@Override
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
	
	@Override
	public int getProgression(Crewmate crewmate) 
	{
		return getOrPut(crewmate, "Progression", 0);
	}
}