package mazgani.amongus.lobbies.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.lobbies.events.LobbyJoinEvent;

public class LobbyJoinListener implements Listener
{
	private final LobbiesManager lobbiesManager;
	
	public LobbyJoinListener(LobbiesManager lobbiesManager)
	{
		this.lobbiesManager = lobbiesManager;
	}
	
	@EventHandler
	public void onPlayerJoin(LobbyJoinEvent event) 
	{
		
	}
}
