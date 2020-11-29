package mazgani.amongus.shiptasks.types;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.Listener;

import mazgani.amongus.AmongUs;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.shiptasks.ShipTask;
import mazgani.amongus.utilities.PluginUtilities;

public abstract class SimpleTask implements ShipTask, Listener
{
	private final String name, description;
	protected final AUGame game;
	private final Map<GamePlayer, Map<String, Object>> playersDatas = new HashMap<>();
	
	public SimpleTask(String name, String description, AUGame game) 
	{
		this.name = name;
		this.description = description;
		this.game = game;
		
		PluginUtilities.registerListeners(AmongUs.getInstance(), this); //fuck this outta here
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
	public void setData(GamePlayer player, String data, Object value) 
	{
		this.playersDatas.computeIfAbsent(player, d -> new HashMap<>()).put(data, value);
	}
	public void removeData(GamePlayer player, String data) 
	{
		Map<String, Object> playerData = this.playersDatas.get(player);
		
		if(playerData != null) 
		{
			playerData.remove(data);
		}
	}
	public Object getData(GamePlayer player, String data) 
	{
		Map<String, Object> playerData = this.playersDatas.get(player);
		
		return playerData == null ? null : playerData.get(data);
	}
	public Object getOrPut(GamePlayer player, String data, Object defaultIfAbsent) 
	{
		Map<String, Object> playerData = this.playersDatas.computeIfAbsent(player, v -> new HashMap<>());
		
		return playerData.computeIfAbsent(data, v -> defaultIfAbsent);
	}
	public void setFinished(GamePlayer player) 
	{
		setData(player, "Finished", true);
	}
	public boolean finished(GamePlayer player) 
	{
		return (boolean) getData(player, "Finished");
	}
	
	@Override
	public String toString() 
	{
		return this.description;
	}
}