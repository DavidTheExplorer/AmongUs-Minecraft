package dte.amongus.lobby;

import static dte.amongus.player.PlayerRole.CREWMATE;
import static dte.amongus.player.PlayerRole.IMPOSTOR;

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
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import dte.amongus.corpses.factory.CorpseFactory;
import dte.amongus.events.lobbies.LobbyFullEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.settings.GameSettings;
import dte.amongus.lobby.listeners.LobbyStateListener;
import dte.amongus.lobby.service.AULobbyService;
import dte.amongus.lobby.sign.LobbySign;
import dte.amongus.maps.GameMap;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.PlayerRole;
import dte.amongus.player.visual.PlayerColor;
import dte.amongus.utils.java.UUIDProvider;

/*
 * TODO: before addPlayer() is called, open a GUI where the player can select an untaken skin from their wardrobe.
 * If they have no skins in their wardrobe, put them in a random untaken color.
 */
public class AULobby
{
	private final UUID id;
	private final Location spawnLocation;
	private final GameSettings gameSettings;
	private final GameMap gameMap;
	private final Set<AUPlayer> waitingPlayers = new HashSet<>();
	private final Set<LobbyStateListener> stateListeners = new HashSet<>();
	private final List<PlayerColor> availableColors = Lists.newArrayList(PlayerColor.VALUES);
	
	private AUGame currentGame;
	
	public AULobby(UUID id, Location spawnLocation, GameMap gameMap, GameSettings gameSettings)
	{
		this.id = id;
		this.spawnLocation = spawnLocation;
		this.gameMap = gameMap;
		this.gameSettings = gameSettings;
		
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
	
	public GameSettings getGameSettings() 
	{
		return this.gameSettings;
	}
	
	public Set<AUPlayer> getPlayers()
	{
		return new HashSet<>(this.waitingPlayers);
	}
	
	public void setCurrentGame(AUGame game) 
	{
		this.currentGame = game;
	}
	
	public boolean isFull()
	{
		return this.waitingPlayers.size() == this.gameSettings.getPlayersRequired();
	}
	
	public void addPlayer(AUPlayer auPlayer)
	{
		if(isFull())
			throw new IllegalStateException("Can't add a player to a Full Among Us Lobby!");
		
		Player player = auPlayer.getOfflinePlayer().getPlayer();
		
		//add the player and update the listeners
		this.waitingPlayers.add(auPlayer);
		this.stateListeners.forEach(listener -> listener.onLobbyJoin(this, player));
		
		//if the lobby became full - start the game
		if(isFull())
			Bukkit.getPluginManager().callEvent(new LobbyFullEvent(this, player));
	}
	
	public void removePlayer(AUPlayer auPlayer) 
	{
		boolean wasInLobby = this.waitingPlayers.remove(auPlayer);
		
		if(wasInLobby) 
			this.stateListeners.forEach(listener -> listener.onLobbyLeave(this, auPlayer.getOfflinePlayer().getPlayer()));
	}
	
	public boolean contains(AUPlayer auPlayer) 
	{
		return this.waitingPlayers.contains(auPlayer);
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
	
	public static class Builder 
	{
		Location spawnLocation;
		GameMap gameMap;
		CorpseFactory corpseFactory;
		Map<PlayerRole, Integer> requiredPlayers = new HashMap<>();
		Sign joinSign;
		
		private static AULobbyService lobbyService;
		
		public Builder withSpawnLocation(Location spawnLocation) 
		{
			this.spawnLocation = spawnLocation;
			return this;
		}
		
		public Builder withGameMap(GameMap gameMap) 
		{
			this.gameMap = gameMap;
			return this;
		}
		
		public Builder thatGetsCorpsesFrom(CorpseFactory corpseFactory) 
		{
			this.corpseFactory = corpseFactory;
			return this;
		}
		
		public Builder thatRequires(PlayerRole role, int amount) 
		{
			this.requiredPlayers.put(role, amount);
			return this;
		}
		
		public Builder joinableBy(Sign sign) 
		{
			this.joinSign = sign;
			return this;
		}
		
		public static void setLobbyService(AULobbyService lobbyService) 
		{
			Builder.lobbyService = lobbyService;
		}
		
		public AULobby build()
		{
			Integer crewmatesAmount = this.requiredPlayers.get(CREWMATE);
			Integer impostorsAmount = this.requiredPlayers.get(IMPOSTOR);
			
			Validate.notNull(lobbyService, "The lobby service has to be set prior to creating a new lobby!");
			Validate.notNull(crewmatesAmount, "The impostors amount must be set!");
			Validate.notNull(impostorsAmount, "The crewmates amount must be set!");
			
			GameSettings gameSettings = new GameSettings(crewmatesAmount, impostorsAmount, this.corpseFactory);
			AULobby lobby = new AULobby(UUIDProvider.generateFor(AULobby.class), this.spawnLocation, this.gameMap, gameSettings);
			
			if(this.joinSign != null)
			{
				LobbySign sign = new LobbySign(this.joinSign, lobby);
				sign.update(true);
				
				lobby.addStateListener(sign);
			}
			lobbyService.registerLobby(lobby);
			
			return lobby;
		}
	}
}