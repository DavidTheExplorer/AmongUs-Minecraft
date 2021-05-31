package dte.amongus.shiptasks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.type.TaskType;

public abstract class SimpleShipTask implements ShipTask
{
	private final String name, description;
	private final TaskType type;
	private final AUGame game;
	private final Map<AUGamePlayer, Map<String, Object>> playersData = new HashMap<>();
	private final Set<Crewmate> finishers = new HashSet<>();
	
	protected SimpleShipTask(String name, String description, TaskType type, AUGame game) 
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
	public void setFinished(Crewmate crewmate) 
	{
		this.finishers.add(crewmate);
	}

	@Override
	public boolean hasFinished(Crewmate crewmate) 
	{
		return this.finishers.contains(crewmate);
	}

	protected void setData(AUGamePlayer gamePlayer, String data, Object value)
	{
		Map<String, Object> playerData = this.playersData.computeIfAbsent(gamePlayer, p -> new HashMap<>());
		
		playerData.put(data, value);
	}
	protected void removeData(AUGamePlayer gamePlayer, String data)
	{
		Map<String, Object> playerData = this.playersData.get(gamePlayer);
		
		if(playerData != null)
			playerData.remove(data);
	}
	protected <T> Optional<T> getData(AUGamePlayer gamePlayer, String data, Class<T> valueClass)
	{
		Map<String, Object> playerData = this.playersData.get(gamePlayer);

		return Optional.ofNullable(playerData)
				.map(playerDataArg -> playerDataArg.get(data))
				.map(valueClass::cast);
	}
	protected <T> T getOrPut(AUGamePlayer gamePlayer, String data, T defaultIfAbsent) 
	{
		return getOrPut(gamePlayer, data, () -> defaultIfAbsent);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getOrPut(AUGamePlayer gamePlayer, String data, Supplier<T> defaultSupplier)
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