package mazgani.amongus.lobbies.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import mazgani.amongus.lobbies.LobbiesManager;

public class LobbyLeaveListener extends LobbyListener
{
	public LobbyLeaveListener(LobbiesManager lobbiesManager) 
	{
		super(lobbiesManager);
	}
	
	@EventHandler
	public void removePlayerOnQuit(PlayerQuitEvent event) 
	{
		Player player = event.getPlayer();
		
		this.lobbiesManager.findLobbyOf(player).ifPresent(lobby -> lobby.removePlayer(player));
	}
}
