package mazgani.amongus.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mazgani.amongus.AmongUs;

public class AUPlayersManager implements Listener
{
	private final Map<UUID, AUPlayer> playerByUUID = new HashMap<>();
	
	public AUPlayersManager() 
	{
		Bukkit.getPluginManager().registerEvents(this, AmongUs.getInstance());
	}
	public AUPlayer getPlayer(UUID playerUUID) 
	{
		return this.playerByUUID.get(playerUUID);
	}
	
	@EventHandler
	public void registerPlayerOnJoin(PlayerJoinEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.playerByUUID.put(playerUUID, new AUPlayer(playerUUID));
	}
	
	@EventHandler
	public void deregisterPlayerOnQuit(PlayerQuitEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.playerByUUID.remove(playerUUID);
	}
}
