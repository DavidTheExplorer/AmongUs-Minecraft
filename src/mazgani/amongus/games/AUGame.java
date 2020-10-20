package mazgani.amongus.games;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import mazgani.amongus.enums.Role;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.shiptasks.Task;

public class AUGame
{
	private final GameLobby lobby;
	private Set<Task> tasks;
	private final Map<UUID, GamePlayer> players;
	
	public AUGame(GameLobby lobby, Map<UUID, GamePlayer> players) 
	{
		this.lobby = lobby;
		this.players = players;
	}
	public GameLobby getLobby() 
	{
		return this.lobby;
	}
	public Set<Task> getTasks()
	{
		return this.tasks;
	}
	public void setTasks(Set<Task> tasks) 
	{
		if(this.tasks != null) 
		{
			throw new UnsupportedOperationException("The tasks cannot be set twice.");
		}
		this.tasks = tasks;
	}
	public void addPlayer(GamePlayer player) 
	{
		this.players.put(player.getPlayerUUID(), player);
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
	public boolean isTie() 
	{
		Map<Role, List<GamePlayer>> rolesPlayersLeft = this.players.values().stream()
				.filter(player -> !player.isSpectator())
				.collect(groupingBy(GamePlayer::getRole));
		
		//check if the distinct amounts of players is 1 - so all the teams have the same amount of players
		return rolesPlayersLeft.values().stream()
				.mapToInt(List::size)
				.distinct()
				.count() == 1;
	}
}
