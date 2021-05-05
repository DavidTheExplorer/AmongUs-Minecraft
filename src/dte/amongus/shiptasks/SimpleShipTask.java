package dte.amongus.shiptasks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.type.TaskType;

public abstract class SimpleShipTask implements ShipTask
{
	private final String name, description;
	private final TaskType type;
	private final AUGame game;
	private final Map<AUGamePlayer, Map<String, Object>> playersData = new HashMap<>();
	private final Set<AUGamePlayer> finishers = new HashSet<>();
	
	public SimpleShipTask(String name, String description, TaskType type, AUGame game) 
	{
		this.name = name;
		this.description = description;
		this.type = type;
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
	public TaskType getType() 
	{
		return this.type;
	}

	@Override
	public AUGame getGame() 
	{
		return this.game;
	}

	@Override
	public void setFinished(AUGamePlayer gamePlayer) 
	{
		this.finishers.add(gamePlayer);
	}

	@Override
	public boolean hasFinished(AUGamePlayer gamePlayer) 
	{
		return this.finishers.contains(gamePlayer);
	}

	public void setData(AUGamePlayer gamePlayer, String data, Object value)
	{
		Map<String, Object> playerData = this.playersData.computeIfAbsent(gamePlayer, p -> new HashMap<>());
		
		playerData.put(data, value);
	}
	public void removeData(AUGamePlayer gamePlayer, String data)
	{
		Map<String, Object> playerData = this.playersData.get(gamePlayer);
		
		if(playerData != null)
			playerData.remove(data);
	}
	public Optional<Object> getData(AUGamePlayer gamePlayer, String data)
	{
		Map<String, Object> playerData = this.playersData.get(gamePlayer);

		return Optional.ofNullable(playerData)
				.map(playerDataArg -> playerDataArg.get(data));
	}
	public <T> T getOrPut(AUGamePlayer gamePlayer, String data, T defaultIfAbsent) 
	{
		return getOrPut(gamePlayer, data, () -> defaultIfAbsent);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOrPut(AUGamePlayer gamePlayer, String data, Supplier<T> defaultSupplier)
	{
		Map<String, Object> playerData = this.playersData.computeIfAbsent(gamePlayer, p -> new HashMap<>());

		return (T) playerData.computeIfAbsent(data, v -> defaultSupplier.get());
	}

	@Override
	public String toString() 
	{
		return this.name;
	}
}