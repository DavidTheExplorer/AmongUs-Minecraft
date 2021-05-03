package dte.amongus.listeners.lobbies;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.amongus.lobby.AULobbyService;

public class LobbyLeaveListeners implements Listener
{
	private final AULobbyService lobbyService;
	
	public LobbyLeaveListeners(AULobbyService lobbyService) 
	{
		this.lobbyService = lobbyService;
	}
	
	@EventHandler
	public void removePlayerOnQuit(PlayerQuitEvent event) 
	{
		removePlayer(event.getPlayer());
	}
	
	private void removePlayer(Player player) 
	{
		this.lobbyService.findLobbyOf(player).ifPresent(lobby -> lobby.removePlayer(player));
	}
}
