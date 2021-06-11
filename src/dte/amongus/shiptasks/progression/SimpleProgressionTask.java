package dte.amongus.shiptasks.progression;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.simple.SimpleShipTask;

public abstract class SimpleProgressionTask extends SimpleShipTask implements ProgressionShipTask
{
	private final int maxProgression;
	
	protected SimpleProgressionTask(String name, String description, TaskType type, AUGame game, int maxProgression) 
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
		return (Integer) getOrPut(crewmate, "Progression", 0);
	}
}