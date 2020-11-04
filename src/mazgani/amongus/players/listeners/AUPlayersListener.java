package mazgani.amongus.players.listeners;

import org.bukkit.event.Listener;

import mazgani.amongus.players.AUPlayersManager;

public abstract class AUPlayersListener implements Listener
{
	protected final AUPlayersManager auPlayersManager;
	
	protected AUPlayersListener(AUPlayersManager auPlayersManager) 
	{
		this.auPlayersManager = auPlayersManager;
	}
}