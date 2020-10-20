package mazgani.amongus.shiptasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import mazgani.amongus.AmongUs;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public abstract class SimpleTask implements Task
{
	private final String name;
	protected final AUGame game;
	
	private final Map<GamePlayer, Map<String, Object>> playersDatas = new HashMap<>();
	
	public SimpleTask(String name, AUGame game) 
	{
		this.name = name;
		this.game = game;
		
		Bukkit.getPluginManager().registerEvents(this, AmongUs.getInstance());
	}
	
	@Override
	public String getName() 
	{
		return this.name;
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
	public Object getData(GamePlayer player, String data) 
	{
		Map<String, Object> playerData = getPlayerData(player);
		
		return playerData == null ? null : playerData.get(data);
	}
	public Object getData(GamePlayer player, String data, Object defaultIfAbsent) 
	{
		Map<String, Object> playerData = getPlayerData(player);
		
		if(playerData == null) 
		{
			return null;
		}
		return playerData.getOrDefault(data, defaultIfAbsent);
	}
	public void removeData(GamePlayer player, String data) 
	{
		Map<String, Object> playerData = getPlayerData(player);
		
		if(playerData != null) 
		{
			playerData.remove(data);
		}
	}
	
	private Map<String, Object> getPlayerData(GamePlayer player)
	{
		return this.playersDatas.get(player);
	}
}
