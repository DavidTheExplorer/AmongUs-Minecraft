package mazgani.amongus.lobbies;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import mazgani.amongus.events.lobbies.LobbyFullEvent;
import mazgani.amongus.games.AUGame;

public class GameLobby
{
	private final UUID uuid;
	
	@Getter
	private final Location spawnLocation;
	
	private final Set<Player> waitingPlayers = new HashSet<>();
	
	@Getter
	private AUGame currentGame;
	
	//Settings
	private int crewmates, impostors;
	
	private final int PLAYERS_REQUIRED;
	
	private final Set<LobbyStateListener> stateListeners = new HashSet<>();
	
	GameLobby(UUID uuid, Location spawnLocation, int crewmates, int impostors)
	{
		this.uuid = uuid;
		this.spawnLocation = spawnLocation;
		this.crewmates = crewmates;
		this.impostors = impostors;
		
		this.PLAYERS_REQUIRED = (impostors + crewmates);
	}
	public UUID getUUID() 
	{
		return this.uuid;
	}
	public Set<Player> getPlayersView() 
	{
		return Collections.unmodifiableSet(this.waitingPlayers);
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
		return this.PLAYERS_REQUIRED;
	}
	public boolean isFull()
	{
		return this.waitingPlayers.size() == this.PLAYERS_REQUIRED;
	}
	public boolean addPlayer(Player player)
	{
		if(isFull())
		{
			return false;
		}
		this.waitingPlayers.add(player);
		this.stateListeners.forEach(listener -> listener.onJoin(this, player));
		
		if(isFull()) 
		{
			Bukkit.getPluginManager().callEvent(new LobbyFullEvent(this));
		}
		return true;
	}
	public boolean removePlayer(Player player) 
	{
		boolean wasInLobby = this.waitingPlayers.remove(player);
		
		if(wasInLobby) 
		{
			this.stateListeners.forEach(listener -> listener.onLeave(this, player));
		}
		return wasInLobby;
	}
	public boolean contains(Player player) 
	{
		return this.waitingPlayers.contains(player);
	}
	public void clear() 
	{
		this.waitingPlayers.clear();
	}
	
	public void addStateListener(LobbyStateListener listener) 
	{
		this.stateListeners.add(listener);
	}
}
