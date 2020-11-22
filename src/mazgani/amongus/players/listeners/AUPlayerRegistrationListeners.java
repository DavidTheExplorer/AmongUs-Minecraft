package mazgani.amongus.players.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mazgani.amongus.players.AUPlayersManager;

public class AUPlayerRegistrationListeners implements Listener
{
	private final AUPlayersManager playersManager;
	
	public AUPlayerRegistrationListeners(AUPlayersManager playersManager) 
	{
		this.playersManager = playersManager;
	}
	
	@EventHandler
	public void registerPlayerOnJoin(PlayerJoinEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.playersManager.register(playerUUID);
	}
	
	@EventHandler
	public void deregisterPlayerOnQuit(PlayerQuitEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.playersManager.unregister(playerUUID);
	}
}
