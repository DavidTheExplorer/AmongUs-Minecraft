package dte.amongus.lobby;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import dte.amongus.events.lobbies.LobbyFullEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.settings.GameSettings;
import dte.amongus.lobby.listeners.LobbyStateListener;
import dte.amongus.maps.GameMap;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.visual.PlayerColor;

/*
 * TODO: before addPlayer() is called, open a GUI where the player can select an untaken skin from their wardrobe.
 * If they have no skins in their wardrobe, put them in a random untaken color.
 */
public class AULobby
{
	private final UUID id;
	private final Location spawnLocation;
	private final GameSettings settings;
	private final GameMap gameMap;
	private final Map<UUID, AUPlayer> waitingPlayers = new HashMap<>();
	private final Set<LobbyStateListener> stateListeners = new HashSet<>();
	private final List<PlayerColor> availableColors = Lists.newArrayList(PlayerColor.VALUES);
	
	private AUGame currentGame;
	
	public AULobby(UUID id, Location spawnLocation, GameMap gameMap, GameSettings settings)
	{
		this.id = id;
		this.spawnLocation = spawnLocation;
		this.gameMap = gameMap;
		this.settings = settings;
		
		Collections.shuffle(this.availableColors);
	}
	
	public UUID getID() 
	{
		return this.id;
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
	
	public Collection<AUPlayer> getPlayers()
	{
		return this.waitingPlayers.values();
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
			return false;
		
		Player player = auPlayer.getOfflinePlayer().getPlayer();
		
		//register the player
		this.waitingPlayers.put(player.getUniqueId(), auPlayer);
		
		//update the state listeners
		this.stateListeners.forEach(listener -> listener.onLobbyJoin(this, player));
		
		if(isFull())
			Bukkit.getPluginManager().callEvent(new LobbyFullEvent(this, player));
		
		return true;
	}
	
	public boolean removePlayer(Player player) 
	{
		boolean wasInLobby = this.waitingPlayers.remove(player.getUniqueId()) != null;
		
		if(wasInLobby) 
			this.stateListeners.forEach(listener -> listener.onLobbyLeave(this, player));
		
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
	public String toString() 
	{
		return this.id.toString().substring(0, 5);
	}
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(this.id);
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
		
		AULobby lobby = (AULobby) object;
		
		return this.id.equals(lobby.id);
	}
}