package dte.amongus.lobby;

import static dte.amongus.utils.java.RandomUtils.randomElement;
import static java.util.stream.Collectors.toSet;

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

import dte.amongus.corpses.factory.CorpseFactory;
import dte.amongus.games.settings.GameSettings;
import dte.amongus.lobby.sign.LobbySign;
import dte.amongus.maps.GameMap;
import dte.amongus.utils.java.UUIDProvider;

public class LobbiesManager
{
	private final Map<UUID, AULobby> lobbyByID = new HashMap<>();
	
	public void registerLobby(AULobby lobby)
	{
		this.lobbyByID.put(lobby.getID(), lobby);
	}
	public Optional<AULobby> findRandomLobby() 
	{
		return Optional.ofNullable(randomElement(getLobbies()));
	}
	public Optional<AULobby> findRandomLobbyThat(Predicate<AULobby> lobbyTester) //TODO: Use this for ignore lists - so people can join games without people they hate which could ruin their game
	{
		 Set<AULobby> matchingLobbies = getLobbies().stream()
				 .filter(lobbyTester)
				 .collect(toSet());
		 
		 return Optional.ofNullable(randomElement(matchingLobbies));
	}
	public Optional<AULobby> findLobbyOf(Player player)
	{
		return getLobbies().stream()
				.filter(lobby -> lobby.contains(player))
				.findAny();
	}
	public Collection<AULobby> getLobbies()
	{
		return this.lobbyByID.values();
	}
	
	public static class LobbyBuilder 
	{
		private final Location spawnLocation;
		private final GameMap gameMap;
		private final CorpseFactory corpseFactory;
		private final int crewmates, impostors;
		
		private Sign joinSign;
		
		public LobbyBuilder(Location spawnLocation, GameMap gameMap, CorpseFactory corpseFactory, int crewmates, int impostors)
		{
			this.spawnLocation = spawnLocation;
			this.gameMap = gameMap;
			this.corpseFactory = corpseFactory;
			this.crewmates = crewmates;
			this.impostors = impostors;
		}
		public LobbyBuilder joinableBy(Sign sign) 
		{
			this.joinSign = sign;
			return this;
		}
		public AULobby build(LobbiesManager manager)
		{
			GameSettings settings = new GameSettings(this.crewmates, this.impostors, this.corpseFactory);
			UUID lobbyID = UUIDProvider.generateUUID(AULobby.class);
			
			AULobby lobby = new AULobby(lobbyID, this.spawnLocation, this.gameMap, settings);
			
			if(this.joinSign != null)
			{
				LobbySign sign = new LobbySign(this.joinSign, lobby);
				sign.update(true);
				
				lobby.addStateListener(sign);
			}
			manager.registerLobby(lobby);
			return lobby;
		}
	}
}