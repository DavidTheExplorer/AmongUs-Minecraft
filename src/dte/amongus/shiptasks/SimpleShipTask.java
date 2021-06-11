package dte.amongus.shiptasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.type.TaskType;

public abstract class SimpleShipTask implements ShipTask
{
	private final String name, description;
	private final TaskType type;
	private final AUGame game;
	private final Map<Crewmate, Map<String, Object>> playersData = new HashMap<>();
	
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

	protected void setData(Crewmate crewmate, String data, Object value)
	{
		Map<String, Object> playerData = this.playersData.computeIfAbsent(crewmate, c -> new HashMap<>());
		
		playerData.put(data, value);
	}
	
	protected void removeData(Crewmate crewmate, String data)
	{
		Map<String, Object> playerData = this.playersData.get(crewmate);
		
		if(playerData != null)
			playerData.remove(data);
	}
	
	protected <T> Optional<T> getData(Crewmate crewmate, String data, Class<T> valueClass)
	{
		Map<String, Object> playerData = this.playersData.get(crewmate);

		return Optional.ofNullable(playerData)
				.map(playerDataArg -> playerDataArg.get(data))
				.map(valueClass::cast);
	}
	
	protected <T> T getOrPut(Crewmate crewmate, String data, T defaultIfAbsent) 
	{
		return getOrPut(crewmate, data, () -> defaultIfAbsent);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getOrPut(Crewmate crewmate, String data, Supplier<T> defaultSupplier)
	{
		Map<String, Object> playerData = this.playersData.computeIfAbsent(crewmate, c -> new HashMap<>());

		return (T) playerData.computeIfAbsent(data, v -> defaultSupplier.get());
	}

	@Override
	public String toString() 
	{
		return this.name;
	}
}