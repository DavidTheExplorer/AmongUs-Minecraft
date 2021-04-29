package dte.amongus.games;

import static dte.amongus.utils.java.Predicates.negate;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.corpses.factory.CorpseFactory;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.lobby.AULobby;
import dte.amongus.maps.GameMap;
import dte.amongus.player.PlayerRole;
import dte.amongus.shiptasks.ShipTask;

public class AUGame
{
	private final UUID id;
	private final AULobby lobby;
	private final GameMap map;
	private final CorpseFactory corpseFactory;
	private final Set<ShipTask> tasks = new HashSet<>();
	private final Map<Player, AUGamePlayer> players = new HashMap<>();

	private GameState state = GameState.INIT;

	public AUGame(UUID id, AULobby lobby, GameMap map, CorpseFactory corpseFactory)
	{
		this.id = id;
		this.lobby = lobby;
		this.map = map;
		this.corpseFactory = corpseFactory;
	}

	public UUID getID()
	{
		return this.id;
	}
	public AULobby getLobby() 
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
	public void add(AUGamePlayer gamePlayer) 
	{
		verifyInInit("Players can only be added during the game's initialization.");

		this.players.put(gamePlayer.getPlayer(), gamePlayer);
	}
	public boolean contains(Player player) 
	{
		return this.players.containsKey(player);
	}
	public void addTask(ShipTask... tasks) 
	{
		verifyInInit("Tasks can only be added during the game's initialization.");

		Arrays.stream(tasks).forEach(this.tasks::add);
	}
	public void setState(GameState state)
	{
		this.state = state;
	}
	public void addPlayer(AUGamePlayer gamePlayer) 
	{
		this.players.put(gamePlayer.getPlayer(), gamePlayer);
	}
	public AUGamePlayer getPlayer(Player player) 
	{
		return this.players.get(player);
	}
	public <T extends AUGamePlayer> T getPlayer(Player player, Class<T> playerType)
	{
		AUGamePlayer gamePlayer = getPlayer(player);
		
		return playerType.isInstance(gamePlayer) ? playerType.cast(gamePlayer) : null;
	}
	public AbstractCorpse spawnCorpse(Crewmate whoDied, Location deathLocation)
	{
		AbstractCorpse corpse = this.corpseFactory.generateCorpse(whoDied, deathLocation);
		whoDied.setCorpse(corpse);
		corpse.spawn();

		return corpse;
	}
	public boolean isWin()
	{
		return isTie();
	}
	public Set<ShipTask> getTasks()
	{
		return Collections.unmodifiableSet(this.tasks);
	}
	
	public Collection<AUGamePlayer> getPlayers()
	{
		return  this.players.values();
	}
	public <T extends AUGamePlayer> Collection<T> getPlayers(Class<T> playerType)
	{
		return getPlayers().stream()
				.filter(playerType::isInstance)
				.map(playerType::cast)
				.collect(toList());
	}
	
	public Collection<AUGamePlayer> getAlivePlayers()
	{
		return getAlivePlayers(AUGamePlayer.class);
	}
	public <T extends AUGamePlayer> List<T> getAlivePlayers(Class<T> playerType)
	{
		return getPlayers(playerType).stream()
				.filter(negate(AUGamePlayer::isDead))
				.collect(toList());
	}
	
	public Collection<AUGamePlayer> getDeadPlayers()
	{
		return getDeadPlayers(AUGamePlayer.class);
	}
	public <T extends AUGamePlayer> List<T> getDeadPlayers(Class<T> playerType)
	{
		return getPlayers(playerType).stream()
				.filter(AUGamePlayer::isDead)
				.collect(toList());
	}
	/*public List<AUGamePlayer> getPlayers(PlayerRole role)
	{
		return getPlayers(role.getPlayerClass()).stream()
				.map(AUGamePlayer.class::cast)
				.collect(toList());
	}*/

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

		AUGame other = (AUGame) object;

		return Objects.equals(this.id, other.id);
	}
	
	@Override
	public String toString()
	{
		//Players
		Collection<Crewmate> crewmates = getPlayers(Crewmate.class);
		Collection<Impostor> impostors = getPlayers(Impostor.class);
		
		String crewmatesNames = crewmates.stream()
				.map(impostor -> impostor.getPlayer().getName())
				.collect(joining(", "));
		
		String impostorsNames = impostors.stream()
				.map(impostor -> impostor.getPlayer().getName())
				.collect(joining(", "));
		
		//Tasks
		String tasksNames = this.tasks.stream()
				.map(ShipTask::getName)
				.collect(joining(",", "[", "]"));
		
		return String.format("AUGame [id=%s, lobby=%s, map=%s, state=%s, tasks=%s, players=[Impostors(%d): %s | Crewmates(%d): %s]]", 
				this.id.toString().substring(0, 5), 
				this.lobby,
				this.map,
				this.state,
				tasksNames, 
				impostors.size(),
				impostorsNames,
				crewmates.size(),
				crewmatesNames);
	}

	private void verifyInInit(String errorMessage) 
	{
		if(this.state != GameState.INIT)
			throw new UnsupportedOperationException(errorMessage + " during the game's initialization.");
	}
	private boolean isTie() 
	{
		Map<PlayerRole, List<AUGamePlayer>> rolesPlayersLeft = this.players.values().stream()
				.filter(negate(AUGamePlayer::isDead))
				.collect(groupingBy(AUGamePlayer::getRole));

		//make sure the distinct amounts of players is 1 - so all the teams have the same amount of players
		return rolesPlayersLeft.values().stream()
				.mapToInt(List::size)
				.distinct()
				.count() == 1;
	}

	public enum GameState 
	{
		INIT,
		PLAYING,
		VOTING;
	}
}