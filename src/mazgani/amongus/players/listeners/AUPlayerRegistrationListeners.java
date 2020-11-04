package mazgani.amongus.players.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mazgani.amongus.players.AUPlayersManager;

public class AUPlayerRegistrationListeners extends AUPlayersListener
{
	public AUPlayerRegistrationListeners(AUPlayersManager auPlayersManager) 
	{
		super(auPlayersManager);
	}
	
	@EventHandler
	public void registerPlayerOnJoin(PlayerJoinEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.auPlayersManager.register(playerUUID);
	}
	
	@EventHandler
	public void deregisterPlayerOnQuit(PlayerQuitEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.auPlayersManager.unregister(playerUUID);
	}
}
