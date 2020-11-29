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

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import mazgani.amongus.AmongUs;
import mazgani.amongus.lobbies.displayers.LobbySign;
import mazgani.amongus.lobbies.listeners.LobbyLeaveListeners;
import mazgani.amongus.maps.GameMap;
import mazgani.amongus.utilities.PluginUtilities;
import mazgani.amongus.utilities.UUIDProvider;

public class LobbiesManager
{
	private final Map<UUID, GameLobby> lobbyByUUID = new HashMap<>();
	
	public LobbiesManager() 
	{
		PluginUtilities.registerListeners(AmongUs.getInstance(), new LobbyLeaveListeners(this));
	}
	public GameLobby registerLobby(GameLobbyBuilder lobbyBuilder)
	{
		GameLobby lobby = lobbyBuilder.build();
		
		this.lobbyByUUID.put(lobby.getUUID(), lobby);
		
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
	private UUID generateLobbyID()
	{
		return UUIDProvider.generateUUID(GameLobby.class);
	}
	
	public class GameLobbyBuilder 
	{
		private Location spawnLocation;
		private GameMap gameMap;
		private int crewmates;
		private int impostors;
		private Sign joinSign;
		
		public GameLobbyBuilder(Location spawnLocation, GameMap gameMap, int crewmates, int impostors)
		{
			this.spawnLocation = spawnLocation;
			this.gameMap = gameMap;
			this.crewmates = crewmates;
			this.impostors = impostors;
		}
		public GameLobbyBuilder joinableBy(Sign sign) 
		{
			this.joinSign = sign;
			return this;
		}
		public GameLobby build() 
		{
			//create the game settings holder
			GameSettings gameSettings = new GameSettings(this.crewmates, this.impostors);
			
			//create a new game lobby, and register settings holder in it
			UUID lobbyID = generateLobbyID();
			GameLobby lobby = new GameLobby(lobbyID, this.spawnLocation, this.gameMap, gameSettings);
			
			if(this.joinSign != null) 
			{
				lobby.addStateListener(new LobbySign(lobby, this.joinSign));
			}
			return lobby;
		}
	}
}