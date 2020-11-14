package mazgani.amongus.lobbies;

import static java.util.stream.Collectors.toSet;
import static mazgani.amongus.utilities.RandomUtilities.randomElement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import mazgani.amongus.AmongUs;
import mazgani.amongus.games.maps.GameMap;
import mazgani.amongus.lobbies.displayers.LobbySign;
import mazgani.amongus.lobbies.listeners.LobbyLeaveListeners;
import mazgani.amongus.utilities.UUIDProvider;

public class LobbiesManager
{
	private final Map<UUID, GameLobby> lobbyByUUID = new HashMap<>();
	
	public LobbiesManager() 
	{
		Bukkit.getPluginManager().registerEvents(new LobbyLeaveListeners(this), AmongUs.getInstance());
	}
	public GameLobby createLobby(Location spawnLocation, GameMap gameMap, int crewmates, int impostors) 
	{
		return createLobby(spawnLocation, gameMap, crewmates, impostors, null);
	}
	public GameLobby createLobby(Location spawnLocation, GameMap gameMap, int crewmates, int impostors, Sign joinSign)
	{
		//create the lobby
		UUID gameID = UUIDProvider.generateUUID(GameLobby.class);
		GameLobby lobby = new GameLobby(gameID, spawnLocation, gameMap, crewmates, impostors);
		
		//register the lobby
		this.lobbyByUUID.put(gameID, lobby);
		
		//setup the lobby
		if(joinSign != null) 
		{
			lobby.addStateListener(new LobbySign(lobby, joinSign));
		}
		return lobby;
	}
	public Optional<GameLobby> findRandomLobby() 
	{
		return Optional.ofNullable(randomElement(getLobbies()));
	}
	public Optional<GameLobby> findRandomLobbyThat(Predicate<GameLobby> lobbyTester) //TODO: Use this for ignore lists - so people can join games without people they hate which could ruin their game
	{
		 Set<GameLobby> matchingLobbies = getLobbies().stream()
				 .filter(lobbyTester)
				 .collect(toSet());
		 
		 return Optional.ofNullable(randomElement(matchingLobbies));
	}
	public Optional<GameLobby> findLobbyOf(Player player)
	{
		return getLobbies().stream()
				.filter(lobby -> lobby.contains(player))
				.findAny();
	}
	public Collection<GameLobby> getLobbies()
	{
		return this.lobbyByUUID.values();
	}
}
