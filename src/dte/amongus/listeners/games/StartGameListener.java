package dte.amongus.listeners.games;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dte.amongus.events.lobbies.LobbyFullEvent;
import dte.amongus.games.GamesManager;
import dte.amongus.lobby.AULobby;

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
		AULobby lobby = event.getLobby();
		this.gamesManager.registerNewGame(lobby, lobby.getGameMap());
	}
}