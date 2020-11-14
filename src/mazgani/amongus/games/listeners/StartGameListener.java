package mazgani.amongus.games.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.events.LobbyFullEvent;

public class StartGameListener implements Listener
{
	private final GamesManager gamesManager;
	
	public StartGameListener(GamesManager gamesManager)
	{
		this.gamesManager = gamesManager;
	}
	
	@EventHandler
	public void startGameOnLobbyFull(LobbyFullEvent event) 
	{
		GameLobby lobby = event.getLobby();
		AUGame game = this.gamesManager.createGame(lobby, lobby.getGameMap());
	}
}
