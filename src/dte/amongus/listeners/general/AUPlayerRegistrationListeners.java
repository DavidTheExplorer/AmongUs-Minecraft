package dte.amongus.listeners.general;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.amongus.player.AUPlayersManager;

public class AUPlayerRegistrationListeners implements Listener
{
	private final AUPlayersManager playersManager;
	
	public AUPlayerRegistrationListeners(AUPlayersManager playersManager) 
	{
		this.playersManager = playersManager;
	}
	
	@EventHandler
	public void registerOnJoin(PlayerJoinEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.playersManager.load(playerUUID);
	}
	
	@EventHandler
	public void deregisternQuit(PlayerQuitEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.playersManager.unload(playerUUID);
	}
}
