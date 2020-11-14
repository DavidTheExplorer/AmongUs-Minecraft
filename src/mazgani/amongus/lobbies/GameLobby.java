package mazgani.amongus.lobbies;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.maps.GameMap;
import mazgani.amongus.lobbies.events.LobbyFullEvent;
import mazgani.amongus.players.PlayerColor;
import mazgani.amongus.utilities.RandomUtilities;

public class GameLobby
{
	private final UUID uuid;
	private final Location spawnLocation;
	private final GameMap gameMap;
	private final Map<UUID, LobbyPlayer> waitingPlayers = new HashMap<>();
	
	private AUGame currentGame;
	
	private final Set<LobbyStateListener> stateListeners = new HashSet<>();
	
	private final List<PlayerColor> availableColors = new ArrayList<>(Arrays.asList(PlayerColor.VALUES));
	
	//Settings
	private int crewmates, impostors;
	
	GameLobby(UUID uuid, Location spawnLocation, GameMap gameMap, int crewmates, int impostors)
	{
		this.uuid = uuid;
		this.spawnLocation = spawnLocation;
		this.gameMap = gameMap;
		this.crewmates = crewmates;
		this.impostors = impostors;
		
		Collections.shuffle(this.availableColors);
	}
	public UUID getUUID() 
	{
		return this.uuid;
	}
	public Location getSpawnLocation() 
	{
		return this.spawnLocation;
	}
	public AUGame getCurrentGame() 
	{
		return this.currentGame;
	}
	public GameMap getGameMap() 
	{
		return this.gameMap;
	}
	public Set<Player> getPlayersView()
	{
		return this.waitingPlayers.values().stream()
				.map(LobbyPlayer::getPlayer)
				.collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
	}
	public Collection<LobbyPlayer> getGamePlayersView() 
	{
		return Collections.unmodifiableCollection(this.waitingPlayers.values());
	}
	public void setCurrentGame(AUGame game) 
	{
		this.currentGame = game;
	}
	public int crewmatesAmount() 
	{
		return this.crewmates;
	}
	public int impostorsAmount() 
	{
		return this.impostors;
	}
	public int getPlayersRequired() 
	{
		return this.impostors + this.crewmates;
	}
	public boolean isFull()
	{
		return this.waitingPlayers.size() == getPlayersRequired();
	}
	public boolean addPlayer(Player player)
	{
		if(isFull())
		{
			return false;
		}
		
		//register the player
		LobbyPlayer lobbyPlayer = createLobbyPlayer(player);
		this.waitingPlayers.put(player.getUniqueId(), lobbyPlayer);
		
		//update the state listeners
		this.stateListeners.forEach(listener -> listener.onJoin(this, player));
		
		if(isFull())
		{
			Bukkit.getPluginManager().callEvent(new LobbyFullEvent(this, lobbyPlayer));
		}
		return true;
	}
	public boolean removePlayer(Player player) 
	{
		boolean wasInLobby = this.waitingPlayers.remove(player.getUniqueId()) != null;
		
		if(wasInLobby) 
		{
			this.stateListeners.forEach(listener -> listener.onLeave(this, player));
		}
		return wasInLobby;
	}
	public boolean contains(Player player) 
	{
		return this.waitingPlayers.containsKey(player.getUniqueId());
	}
	public void clear() 
	{
		this.waitingPlayers.clear();
	}
	public void addStateListener(LobbyStateListener listener) 
	{
		this.stateListeners.add(listener);
	}
	
	private LobbyPlayer createLobbyPlayer(Player player) 
	{
		PlayerColor randomColor = RandomUtilities.randomElement(this.availableColors);
		
		return new LobbyPlayer(player, randomColor);
	}
}
