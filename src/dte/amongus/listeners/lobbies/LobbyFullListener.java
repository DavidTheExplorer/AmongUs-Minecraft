package dte.amongus.listeners.lobbies;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dte.amongus.events.games.GameStartEvent;
import dte.amongus.events.lobbies.LobbyFullEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.AUGameService;
import dte.amongus.lobby.AULobby;

public class LobbyFullListener implements Listener
{
	private final AUGameService gameService;
	
	public LobbyFullListener(AUGameService gameService)
	{
		this.gameService = gameService;
	}
	
	@EventHandler
	public void startGameOnLobbyFull(LobbyFullEvent event) 
	{
		AULobby lobby = event.getLobby();
		
		AUGame newGame = this.gameService.registerNewGame(lobby, lobby.getGameMap());
		Bukkit.getPluginManager().callEvent(new GameStartEvent(newGame));
	}
}