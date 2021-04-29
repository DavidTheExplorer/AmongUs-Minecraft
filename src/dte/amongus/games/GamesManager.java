package dte.amongus.games;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import dte.amongus.games.AUGame.GameState;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.lobby.AULobby;
import dte.amongus.maps.GameMap;
import dte.amongus.shiptasks.list.wires.WiresTask;
import dte.amongus.utils.java.UUIDProvider;

public class GamesManager
{
	private final Set<AUGame> activeGames = new HashSet<>();

	public AUGame registerNewGame(AULobby lobby, GameMap map)
	{
		//create a game
		UUID randomID = UUIDProvider.generateUUID(AUGame.class);
		AUGame game = new AUGame(randomID, lobby, map, lobby.getSettings().getCorpseFactory());

		//setup the game
		toGamePlayers(lobby, game).forEach(game::addPlayer);
		game.addTask(new WiresTask(game));
		game.setState(GameState.PLAYING);

		//register the game
		lobby.setCurrentGame(game);
		this.activeGames.add(game);

		return game;
	}
	public Optional<AUGame> getPlayerGame(Player player) 
	{
		return this.activeGames.stream()
				.filter(game -> game.contains(player))
				.findFirst();
	}
	public boolean onSameGame(Player player1, Player player2) 
	{
		return getPlayerGame(player1).equals(getPlayerGame(player2));
	}
	public Set<AUGame> getActiveGames()
	{
		return Collections.unmodifiableSet(this.activeGames);
	}
	
	private Collection<AUGamePlayer> toGamePlayers(AULobby lobby, AUGame game)
	{
		LinkedList<Player> bukkitPlayers = lobby.getPlayers().stream()
				.map(auPlayer -> auPlayer.getOfflinePlayer().getPlayer())
				.collect(toCollection(LinkedList::new));
		
		//randomize the players - so the roles are random
		Collections.shuffle(bukkitPlayers);
		
		List<AUGamePlayer> players = new ArrayList<>();
		
		//Crewmates selection
		for(int i = 1; i <= lobby.getSettings().crewmatesAmount(); i++)
			players.add(new Crewmate(bukkitPlayers.poll(), game));

		//Impostors Selection
		for(int i = 1; i <= lobby.getSettings().impostorsAmount(); i++)
			players.add(new Impostor(bukkitPlayers.poll(), game));
		
		return players;
	}
}