package mazgani.amongus.games.listeners;

import org.bukkit.event.Listener;

import mazgani.amongus.games.GamesManager;

public abstract class GameListener implements Listener
{
	protected final GamesManager gamesManager;
	
	public GameListener(GamesManager gamesManager) 
	{
		this.gamesManager = gamesManager;
	}
}
