package mazgani.amongus.games.listeners;

import org.bukkit.event.EventHandler;

import mazgani.amongus.events.games.GameWinEvent;
import mazgani.amongus.games.GamesManager;

public class GameWinListeners extends GameListener
{
	public GameWinListeners(GamesManager gamesManager) 
	{
		super(gamesManager);
	}
	
	@EventHandler
	public void onGameWin(GameWinEvent event) 
	{
		
	}
}
