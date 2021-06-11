package dte.amongus.shiptasks.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.ShipTask;

public class ShipTaskService 
{
	private final Map<Crewmate, ShipTask> currentlyDoing = new HashMap<>();
	
	public Optional<ShipTask> getPlayerTask(Crewmate crewmate)
	{
		return Optional.ofNullable(this.currentlyDoing.get(crewmate));
	}
	
	public void setDoing(Crewmate crewmate, ShipTask task) 
	{
		this.currentlyDoing.put(crewmate, task);
	}
	
	public void setNoTask(Crewmate crewmate) 
	{
		this.currentlyDoing.remove(crewmate);
	}
}