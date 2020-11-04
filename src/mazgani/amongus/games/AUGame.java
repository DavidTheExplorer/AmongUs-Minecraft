package mazgani.amongus.games;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.Getter;
import mazgani.amongus.enums.GameState;
import mazgani.amongus.enums.Role;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.shiptasks.ShipTask;

public class AUGame
{
	private final UUID uuid;

	@Getter
	private final GameLobby lobby;

	@Getter
	private Set<ShipTask> tasks = new HashSet<>();

	@Getter
	private GameState state = GameState.INIT;

	private final Map<UUID, GamePlayer> players;

	AUGame(UUID uuid, GameLobby lobby, Set<Player> players) 
	{
		this.uuid = uuid;
		this.lobby = lobby;
		this.players = players.stream().collect(toMap(Player::getUniqueId, player -> new GamePlayer(player, this)));
	}
	public UUID getUUID() 
	{
		return this.uuid;
	}
	public void setState(GameState state) 
	{
		this.state = state;
	}
	public void addTasks(ShipTask... tasks) 
	{
		verifyInInit("The tasks can only be set during the game's initialization.");

		Arrays.stream(tasks).forEach(this.tasks::add);
	}
	public void addPlayer(GamePlayer player) 
	{
		this.players.put(player.getPlayer().getUniqueId(), player);
	}
	public boolean contains(UUID playerUUID) 
	{
		return this.players.containsKey(playerUUID);
	}
	public GamePlayer getPlayer(UUID playerUUID) 
	{
		return this.players.get(playerUUID);
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
	public Collection<GamePlayer> getPlayersView()
	{
		return this.players.values();
	}

	private void verifyInInit(String errorMessage) 
	{
		if(this.state != GameState.INIT)
		{
			throw new UnsupportedOperationException(errorMessage);
		}
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
}