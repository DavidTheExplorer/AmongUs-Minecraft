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
import mazgani.amongus.games.corpsesfactory.GameCorpsesFactory;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.maps.GameMap;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.GameRole;
import mazgani.amongus.shiptasks.ShipTask;

public class AUGame
{
	private final UUID uuid;
	private final GameLobby lobby;
	private final GameMap map;
	private Set<ShipTask> tasks = new HashSet<>();
	private final GameCorpsesFactory corpsesFactory;
	final Map<UUID, GamePlayer> players;

	private GameState state = GameState.INIT;

	AUGame(UUID uuid, GameLobby lobby, GameMap map, GameCorpsesFactory corpsesFactory)
	{
		this.uuid = uuid;
		this.lobby = lobby;
		this.map = map;
		this.corpsesFactory = corpsesFactory;
		this.players = lobby.getPlayersView().stream().collect(toMap(AUPlayer::getPlayerUUID, auPlayer -> new GamePlayer(auPlayer, this)));
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
		UUID playerUUID = player.getAUPlayer().getPlayer().getUniqueId();
		
		this.players.put(playerUUID, player);
	}
	public void spawnCorpse(GamePlayer whoDied, Location deathLocation) 
	{
		BasicGameCorpse corpse = this.corpsesFactory.generateCorpse(whoDied, this);
		whoDied.setCorpse(corpse);

		corpse.spawn(corpse.computeBestLocation(deathLocation));
	}
	public long playersLeft() 
	{
		return this.players.size();
	}
	public long playersLeft(GameRole role)
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
		Map<GameRole, List<GamePlayer>> rolesPlayersLeft = this.players.values().stream()
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
				.map(gamePlayer -> gamePlayer.getAUPlayer().getPlayer())
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