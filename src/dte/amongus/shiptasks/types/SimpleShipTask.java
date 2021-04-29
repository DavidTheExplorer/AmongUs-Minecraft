package dte.amongus.shiptasks.types;

import java.util.HashMap;
import java.util.Map;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.ShipTask;

public abstract class SimpleShipTask implements ShipTask
{
	private final String name, description;
	private final AUGame game;
	private final Map<AUGamePlayer, Map<String, Object>> playersData = new HashMap<>();
	
	public SimpleShipTask(String name, String description, AUGame game) 
	{
		this.name = name;
		this.description = description;
		this.game = game;
	}

	@Override
	public String getName() 
	{
		return this.name;
	}
	
	@Override
	public String getDescription() 
	{
		return this.description;
	}
	
	@Override
	public AUGame getGame() 
	{
		return this.game;
	}
	
	@Override
	public void setFinished(AUGamePlayer gamePlayer) 
	{
		setData(gamePlayer, "Finished", true);
	}
	
	@Override
	public boolean hasFinished(AUGamePlayer gamePlayer) 
	{
		return (boolean) getData(gamePlayer, "Finished");
	}
	
	public void setData(AUGamePlayer gamePlayer, String data, Object value) 
	{
		this.playersData.computeIfAbsent(gamePlayer, d -> new HashMap<>()).put(data, value);
	}
	public void removeData(AUGamePlayer gamePlayer, String data) 
	{
		Map<String, Object> playerData = this.playersData.get(gamePlayer);
		
		if(playerData != null)
			playerData.remove(data);
	}
	public Object getData(AUGamePlayer gamePlayer, String data) 
	{
		return this.playersData.getOrDefault(gamePlayer, new HashMap<>()).get(data);
	}
	public Object getOrPut(AUGamePlayer gamePlayer, String data, Object defaultIfAbsent) 
	{
		Map<String, Object> playerData = this.playersData.computeIfAbsent(gamePlayer, v -> new HashMap<>());
		
		return playerData.computeIfAbsent(data, v -> defaultIfAbsent);
	}
	
	@Override
	public String toString() 
	{
		return this.description;
	}
}