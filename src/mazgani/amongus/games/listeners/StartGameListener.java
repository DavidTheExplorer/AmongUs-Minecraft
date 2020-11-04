package mazgani.amongus.games.listeners;

import org.bukkit.event.EventHandler;

import mazgani.amongus.events.lobbies.LobbyFullEvent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamesManager;

public class StartGameListener extends GameListener
{
	public StartGameListener(GamesManager gamesManager)
	{
		super(gamesManager);
	}
	
	@EventHandler
	public void startGameOnLobbyFull(LobbyFullEvent event) 
	{
		AUGame game = this.gamesManager.createGame(event.getLobby());
	}
}
