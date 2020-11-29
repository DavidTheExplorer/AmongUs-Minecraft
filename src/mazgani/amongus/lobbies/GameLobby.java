package mazgani.amongus.lobbies;

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

import com.google.common.collect.Lists;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.lobbies.events.LobbyFullEvent;
import mazgani.amongus.maps.GameMap;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.visual.PlayerColor;

public class GameLobby
{
	private UUID uuid;
	private final Location spawnLocation;
	private final GameSettings settings;
	private final GameMap gameMap;
	private final Map<UUID, AUPlayer> waitingPlayers = new HashMap<>();
	private final Set<LobbyStateListener> stateListeners = new HashSet<>();
	private final List<PlayerColor> availableColors = Lists.newArrayList(PlayerColor.VALUES);
	
	private AUGame currentGame;
	
	GameLobby(UUID uuid, Location spawnLocation, GameMap gameMap, GameSettings settings)
	{
		this.uuid = uuid;
		this.spawnLocation = spawnLocation;
		this.gameMap = gameMap;
		this.settings = settings;
		
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
	public GameSettings getSettings() 
	{
		return this.settings;
	}
	public Collection<AUPlayer> getPlayersView()
	{
		return Collections.unmodifiableCollection(this.waitingPlayers.values());
	}
	public void setCurrentGame(AUGame game) 
	{
		this.currentGame = game;
	}
	public boolean isFull()
	{
		return this.waitingPlayers.size() == this.settings.getPlayersRequired();
	}
	public boolean addPlayer(AUPlayer auPlayer)
	{
		if(isFull())
		{
			return false;
		}
		Player player = auPlayer.getPlayer();
		
		//register the player
		this.waitingPlayers.put(player.getUniqueId(), auPlayer);
		
		//update the state listeners
		this.stateListeners.forEach(listener -> listener.onLobbyJoin(this, player));
		
		if(isFull())
		{
			Bukkit.getPluginManager().callEvent(new LobbyFullEvent(this, player));
		}
		return true;
	}
	public boolean removePlayer(Player player) 
	{
		boolean wasInLobby = this.waitingPlayers.remove(player.getUniqueId()) != null;
		
		if(wasInLobby) 
		{
			this.stateListeners.forEach(listener -> listener.onLobbyLeave(this, player));
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
	
	@Override
	public boolean equals(Object object)
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(getClass() != object.getClass())
			return false;
		
		GameLobby other = (GameLobby) object;
		
		return this.uuid.equals(other.uuid);
	}
	
	@Override
	public int hashCode() 
	{
		return this.uuid.hashCode();
	}
}
