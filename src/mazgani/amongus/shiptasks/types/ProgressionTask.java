package mazgani.amongus.shiptasks.types;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public abstract class ProgressionTask extends SimpleTask
{
	private int maxProgression;
	
	public ProgressionTask(String name, AUGame game, int maxProgression) 
	{
		super(name, game);
		
		this.maxProgression = maxProgression;
	}
	public boolean addProgression(GamePlayer player, int amount)
	{
		int newProgression = (getProgression(player) + amount);
		
		//if the new progression is enough to finish the task, mark the player as a finisher
		if(newProgression > this.maxProgression) 
		{
			setFinished(player);
			removeData(player, "Progression");
		}
		else
		{
			setData(player, "Progression", newProgression);
		}
		return newProgression == 100;
	}
	public int getProgression(GamePlayer player) 
	{
		return (Integer) getOrPut(player, "Progression", 0);
	}
}