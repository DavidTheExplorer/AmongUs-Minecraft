package dte.amongus.listeners.lobbies;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dte.amongus.lobby.service.AULobbyService;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.service.AUPlayerService;

public class LobbyLeaveListeners implements Listener
{
	private final AULobbyService lobbyService;
	private final AUPlayerService auPlayerService;
	
	public LobbyLeaveListeners(AULobbyService lobbyService, AUPlayerService auPlayerService) 
	{
		this.lobbyService = lobbyService;
		this.auPlayerService = auPlayerService;
	}
	
	@EventHandler
	public void removePlayerOnQuit(PlayerQuitEvent event) 
	{
		removePlayer(event.getPlayer());
	}
	
	private void removePlayer(Player player) 
	{
		AUPlayer auPlayer = this.auPlayerService.getAUPlayer(player.getUniqueId());
		
		this.lobbyService.getPlayerLobby(auPlayer).ifPresent(lobby -> lobby.removePlayer(auPlayer));
	}
}
