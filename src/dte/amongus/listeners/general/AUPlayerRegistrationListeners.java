package dte.amongus.listeners.general;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.amongus.player.AUPlayerService;

public class AUPlayerRegistrationListeners implements Listener
{
	private final AUPlayerService auPlayerService;
	
	public AUPlayerRegistrationListeners(AUPlayerService auPlayerService) 
	{
		this.auPlayerService = auPlayerService;
	}
	
	@EventHandler
	public void registerOnJoin(PlayerJoinEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.auPlayerService.load(playerUUID);
	}
	
	@EventHandler
	public void deregisternQuit(PlayerQuitEvent event) 
	{
		UUID playerUUID = event.getPlayer().getUniqueId();
		
		this.auPlayerService.unload(playerUUID);
	}
}
