package dte.amongus.listeners.lobbies;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dte.amongus.events.games.GameStartEvent;
import dte.amongus.events.lobbies.LobbyFullEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.GamesManager;
import dte.amongus.lobby.AULobby;

public class LobbyFullListener implements Listener
{
	private final GamesManager gamesManager;
	
	public LobbyFullListener(GamesManager gamesManager)
	{
		this.gamesManager = gamesManager;
	}
	
	@EventHandler
	public void startGameOnLobbyFull(LobbyFullEvent event) 
	{
		AULobby lobby = event.getLobby();
		
		AUGame newGame = this.gamesManager.registerNewGame(lobby, lobby.getGameMap());
		Bukkit.getPluginManager().callEvent(new GameStartEvent(newGame));
	}
}