package mazgani.amongus.games;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import mazgani.amongus.AmongUs;
import mazgani.amongus.games.corpsesfactory.GameCorpsesFactory;
import mazgani.amongus.games.listeners.BodyReportListener;
import mazgani.amongus.games.listeners.GameWinListeners;
import mazgani.amongus.games.listeners.ImpostorKillListener;
import mazgani.amongus.games.listeners.StartGameListener;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.GameSettings;
import mazgani.amongus.maps.GameMap;
import mazgani.amongus.players.Role;
import mazgani.amongus.shiptasks.inventorytasks.wires.WiresTask;
import mazgani.amongus.utilities.PluginUtilities;
import mazgani.amongus.utilities.RandomUtilities;
import mazgani.amongus.utilities.UUIDProvider;

public class GamesManager
{
	private final Set<AUGame> activeGames = new HashSet<>();

	private final GameCorpsesFactory defaultCorpsesFactory;

	public GamesManager(GameCorpsesFactory defaultCorpsesFactory) 
	{
		this.defaultCorpsesFactory = defaultCorpsesFactory;

		PluginUtilities.registerListeners(AmongUs.getInstance(), new StartGameListener(this), new ImpostorKillListener(), new GameWinListeners(), new BodyReportListener());
	}
	public AUGame createGame(GameLobby lobby, GameMap map) 
	{
		return createGame(lobby, map, this.defaultCorpsesFactory);
	}
	public AUGame createGame(GameLobby lobby, GameMap map, GameCorpsesFactory corpsesFactory)
	{
		//create a game
		UUID randomID = UUIDProvider.generateUUID(AUGame.class);
		AUGame game = new AUGame(randomID, lobby, map, corpsesFactory);

		//register the game
		lobby.setCurrentGame(game);
		this.activeGames.add(game);

		//setup the game
		determineRoles(game, lobby.getSettings());
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
		Optional<AUGame> player1Game = getPlayerGame(player1.getUniqueId());

		if(!player1Game.isPresent()) 
		{
			return false;
		}
		//using Objects.equals() because both of the players might not be in a game
		return Objects.equals(player1Game, getPlayerGame(player2.getUniqueId()));
	}
	public Set<AUGame> getActiveGamesView()
	{
		return Collections.unmodifiableSet(this.activeGames);
	}
	private void determineRoles(AUGame game, GameSettings settings) 
	{
		Collection<GamePlayer> players = game.getGamePlayersView();

		//first, set everyones' roles to crewmate
		players.forEach(player -> player.setRole(Role.CREWMATE));

		//select X random players and turn them to impostors
		for(int i = 1; i <= settings.impostorsAmount(); i++) 
		{
			RandomUtilities.from(players).getElementThat(player -> player.getRole() == Role.CREWMATE).setRole(Role.IMPOSTOR);
		}
	}
}