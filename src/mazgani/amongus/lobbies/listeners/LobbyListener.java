package mazgani.amongus.lobbies.listeners;

import org.bukkit.event.Listener;

import mazgani.amongus.lobbies.LobbiesManager;

public abstract class LobbyListener implements Listener
{
	protected final LobbiesManager lobbiesManager;
	
	public LobbyListener(LobbiesManager lobbiesManager) 
	{
		this.lobbiesManager = lobbiesManager;
	}
}
