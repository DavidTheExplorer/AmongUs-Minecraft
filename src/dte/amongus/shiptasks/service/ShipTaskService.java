package dte.amongus.shiptasks.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.ShipTask;

public class ShipTaskService 
{
	private final Map<AUGamePlayer, ShipTask> currentlyDoing = new HashMap<>();
	
	public Optional<ShipTask> getPlayerTask(AUGamePlayer gamePlayer)
	{
		return Optional.ofNullable(this.currentlyDoing.get(gamePlayer));
	}
	public void setDoing(AUGamePlayer gamePlayer, ShipTask task) 
	{
		this.currentlyDoing.put(gamePlayer, task);
	}
	public void setNoTask(AUGamePlayer gamePlayer) 
	{
		this.currentlyDoing.remove(gamePlayer);
	}
}