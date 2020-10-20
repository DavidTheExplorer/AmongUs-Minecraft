package mazgani.amongus.shiptasks;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public abstract class ProgressionTask extends SimpleTask
{
	public ProgressionTask(String name, AUGame game) 
	{
		super(name, game);
	}
	public boolean addProgression(GamePlayer player, double amount) 
	{
		Double currentProgression = getProgression(player);
		
		if(currentProgression == null) 
		{
			currentProgression = 0.0;
		}
		
		//if the new value is more than 100, the player finished the task
		double newProgression = Math.max(getProgression(player) + amount, 100.0);
		
		setData(player, "Progression", newProgression);
		
		return newProgression == 100.0;
	}
	public Double getProgression(GamePlayer player) 
	{
		Object data = getData(player, "Progression");
		
		return data == null ? null : (Double) data;
	}
}