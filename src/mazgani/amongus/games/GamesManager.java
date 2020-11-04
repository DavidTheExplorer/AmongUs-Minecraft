package mazgani.amongus.games;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mazgani.amongus.AmongUs;
import mazgani.amongus.enums.GameState;
import mazgani.amongus.enums.Role;
import mazgani.amongus.games.listeners.GameWinListeners;
import mazgani.amongus.games.listeners.ImpostorKillListener;
import mazgani.amongus.games.listeners.StartGameListener;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.shiptasks.inventorytasks.wires.WiresTask;
import mazgani.amongus.utilities.RandomUtilities;
import mazgani.amongus.utilities.UUIDProvider;

public class GamesManager
{
	private final Set<AUGame> activeGames = new HashSet<>();
	
	public GamesManager() 
	{
		Bukkit.getPluginManager().registerEvents(new StartGameListener(this), AmongUs.getInstance());
		Bukkit.getPluginManager().registerEvents(new ImpostorKillListener(this), AmongUs.getInstance());
		Bukkit.getPluginManager().registerEvents(new GameWinListeners(this), AmongUs.getInstance());
	}
	public AUGame createGame(GameLobby lobby)
	{
		//create a game
		UUID randomID = UUIDProvider.generateUUID(AUGame.class);
		AUGame game = new AUGame(randomID, lobby, lobby.getPlayersView());
		
		//register the game
		lobby.setCurrentGame(game);
		this.activeGames.add(game);
		
		//setup the game
		determineRoles(game, lobby.impostorsAmount());
		game.addTasks(new WiresTask(game));
		game.setState(GameState.PLAYING);

		return game;
	}
	public Optional<AUGame> getPlayerGame(UUID playerUUID) 
	{
		return this.activeGames.stream()
				.filter(game -> game.contains(playerUUID))
				.findAny();
	}
	public boolean onSameGame(Player player1, Player player2) 
	{
		Optional<AUGame> 
		player1Game = getPlayerGame(player1.getUniqueId()),
		player2Game = getPlayerGame(player2.getUniqueId());
		
		return Objects.equals(player1Game, player2Game);
	}
	public Set<AUGame> getActiveGamesView()
	{
		return Collections.unmodifiableSet(this.activeGames);
	}
	private void determineRoles(AUGame game, int impostorsAmount) 
	{
		Collection<GamePlayer> players = game.getPlayersView();
		
		//set everyones' roles to crewmate first
		players.forEach(player -> player.setRole(Role.CREWMATE));
		
		//select X random players and turn them to impostors
		for(int i = 1; i <= impostorsAmount; i++) 
		{
			RandomUtilities.from(players).getElementThat(player -> player.getRole() == Role.CREWMATE).setRole(Role.IMPOSTOR);
		}
	}
}