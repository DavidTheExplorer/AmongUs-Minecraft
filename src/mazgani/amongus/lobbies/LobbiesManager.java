package mazgani.amongus.lobbies;

import static java.util.stream.Collectors.toSet;
import static mazgani.amongus.utilities.JavaUtilities.randomElement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.bukkit.Location;

public class LobbiesManager 
{
	private final Map<UUID, GameLobby> lobbyByUUID = new HashMap<>();
	
	public GameLobby createLobby(Location spawnLocation, int crewmates, int impostors)
	{
		UUID untakenID;
		
		do 
		{
			untakenID = UUID.randomUUID();
		}
		while(this.lobbyByUUID.keySet().contains(untakenID));
		
		GameLobby lobby = new GameLobby(untakenID, spawnLocation, crewmates, impostors);
		this.lobbyByUUID.put(untakenID, lobby);
		
		return lobby;
	}
	public Optional<GameLobby> findRandomLobby() 
	{
		return Optional.ofNullable(randomElement(this.lobbyByUUID.values()));
	}
	public Optional<GameLobby> findRandomGameThat(Predicate<GameLobby> lobbyTester) //TODO: Use this for ignore lists - so people can join games without people they hate which could ruin their game
	{
		 Set<GameLobby> matchingLobbies = getLobbies().stream()
				 .filter(lobbyTester)
				 .collect(toSet());
		 
		 return Optional.ofNullable(randomElement(matchingLobbies));
	}
	public Optional<GameLobby> findGameOf(UUID playerUUID)
	{
		return getLobbies().stream()
				.filter(lobby -> lobby.contains(playerUUID))
				.findAny();
	}
	
	private Collection<GameLobby> getLobbies()
	{
		return this.lobbyByUUID.values();
	}
}
