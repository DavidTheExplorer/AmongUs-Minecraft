package mazgani.amongus.lobbies.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import mazgani.amongus.lobbies.LobbiesManager;

public class LobbyLeaveListeners implements Listener
{
	private final LobbiesManager lobbiesManager;
	
	public LobbyLeaveListeners(LobbiesManager lobbiesManager) 
	{
		this.lobbiesManager = lobbiesManager;
	}
	
	@EventHandler
	public void removePlayerOnQuit(PlayerQuitEvent event) 
	{
		removePlayer(event.getPlayer());
	}
	
	private void removePlayer(Player player) 
	{
		this.lobbiesManager.findLobbyOf(player).ifPresent(lobby -> lobby.removePlayer(player));
	}
}
