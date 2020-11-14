package mazgani.amongus.games;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.games.corpsefactory.GameCorpsesFactory;
import mazgani.amongus.games.maps.GameMap;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.players.Role;
import mazgani.amongus.shiptasks.ShipTask;

public class AUGame
{
	private final UUID uuid;
	private final GameLobby lobby;
	private final GameMap map;
	private Set<ShipTask> tasks = new HashSet<>();
	private final GameCorpsesFactory corpsesFactory;
	private final Map<UUID, GamePlayer> players;
	
	private GameState state = GameState.INIT;

	AUGame(UUID uuid, GameLobby lobby, GameMap map, GameCorpsesFactory corpsesFactory)
	{
		this.uuid = uuid;
		this.lobby = lobby;
		this.map = map;
		this.corpsesFactory = corpsesFactory;

		this.players = lobby.getGamePlayersView().stream().collect(toMap(
						lobbyPlayer -> lobbyPlayer.getPlayer().getUniqueId(),
						lobbyPlayer -> new GamePlayer(lobbyPlayer.getPlayer(), lobbyPlayer.getColor(), this)));
	}
	public UUID getUUID() 
	{
		return this.uuid;
	}
	public GameLobby getLobby() 
	{
		return this.lobby;
	}
	public GameMap getMap() 
	{
		return this.map;
	}
	public GameState getState() 
	{
		return this.state;
	}
	public GamePlayer getPlayer(UUID playerUUID) 
	{
		return this.players.get(playerUUID);
	}
	public boolean contains(UUID playerUUID) 
	{
		return this.players.containsKey(playerUUID);
	}
	public void addTasks(ShipTask... tasks) 
	{
		verifyInInit("The tasks can only be set during the game's initialization.");

		Arrays.stream(tasks).forEach(this.tasks::add);
	}
	public void setState(GameState state)
	{
		this.state = state;
	}
	public void addPlayer(GamePlayer player) 
	{
		this.players.put(player.getPlayer().getUniqueId(), player);
	}
	public void spawnCorpse(GamePlayer player, Location location) 
	{
		BasicGameCorpse corpse = this.corpsesFactory.generateCorpse(player, this);
		corpse.spawn(corpse.computeBestLocation(location));
		
		player.setCorpse(corpse);
	}
	public long playersLeft() 
	{
		return this.players.size();
	}
	public long playersLeft(Role role)
	{
		return this.players.values().stream()
				.filter(player -> player.getRole() == role)
				.count();
	}
	public boolean isWin() 
	{
		return isTie();
	}
	public boolean isTie() 
	{
		Map<Role, List<GamePlayer>> rolesPlayersLeft = this.players.values().stream()
				.filter(player -> !player.isSpectator())
				.collect(groupingBy(GamePlayer::getRole));

		//make sure the distinct amounts of players is 1 - so all the teams have the same amount of players
		return rolesPlayersLeft.values().stream()
				.mapToInt(List::size)
				.distinct()
				.count() == 1;
	}
	public Set<ShipTask> getTasks()
	{
		return Collections.unmodifiableSet(this.tasks);
	}
	public Collection<GamePlayer> getGamePlayersView()
	{
		return this.players.values();
	}
	public Set<Player> getPlayersView()
	{
		return this.players.values().stream()
				.map(GamePlayer::getPlayer)
				.collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(this.uuid);
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
		
		AUGame other = (AUGame) object;
		
		return Objects.equals(this.uuid, other.uuid);
	}
	private void verifyInInit(String errorMessage) 
	{
		if(this.state != GameState.INIT)
		{
			throw new UnsupportedOperationException(errorMessage);
		}
	}
}