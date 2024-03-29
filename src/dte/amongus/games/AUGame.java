package dte.amongus.games;

import static dte.amongus.utils.java.Predicates.negate;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import dte.amongus.corpses.Corpse;
import dte.amongus.corpses.factory.CorpseFactory;
import dte.amongus.deathcontext.ImpostorKillContext;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.lobby.AULobby;
import dte.amongus.maps.GameMap;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.PlayerRole;
import dte.amongus.shiptasks.ShipTask;

public class AUGame
{
	private final UUID id;
	private final AULobby lobby;
	private final GameMap map;
	private final CorpseFactory corpseFactory;
	private final Set<AUGamePlayer> players = new HashSet<>();
	private final Map<Location, ShipTask> tasksLocations = new HashMap<>();
	private final Map<Crewmate, ShipTask> crewmatesCurrentTasks = new HashMap<>();

	private GameState state = GameState.INIT;

	public AUGame(UUID id, AULobby lobby, GameMap map, CorpseFactory corpseFactory)
	{
		this.id = id;
		this.lobby = lobby;
		this.map = map;
		this.corpseFactory = corpseFactory;
	}
	
	/*
	 * General
	 */
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
	
	public void setState(GameState state)
	{
		this.state = state;
	}
	
	public boolean isWin()
	{
		return isTie();
	}

	public Set<ShipTask> getTasks()
	{
		return new HashSet<>(this.tasksLocations.values());
	}
	
	public Set<AUGamePlayer> getPlayers()
	{
		return new HashSet<>(this.players);
	}
	
	/*
	 * Players
	 */
	public void addPlayer(AUGamePlayer gamePlayer) 
	{
		verifyInInit("Players can only be added");

		this.players.add(gamePlayer);
	}
	
	public boolean contains(AUPlayer auPlayer)
	{
		Player player = auPlayer.getOfflinePlayer().getPlayer();
		
		return getPlayer(player).isPresent();
	}
	
	public Optional<AUGamePlayer> getPlayer(Player player) 
	{
		UUID playerUUID = player.getUniqueId();
		
		return this.players.stream()
				.filter(gamePlayer -> gamePlayer.getPlayer().getUniqueId().equals(playerUUID))
				.findFirst();
	}

	public <P extends AUGamePlayer> Optional<P> getPlayer(Player player, Class<P> playerType)
	{
		return getPlayer(player)
				.filter(playerType::isInstance)
				.map(playerType::cast);
	}

	public <P extends AUGamePlayer> Collection<P> getPlayers(Class<P> playerType)
	{
		return getPlayers().stream()
				.filter(playerType::isInstance)
				.map(playerType::cast)
				.collect(toList());
	}
	
	public <P extends AUGamePlayer> List<P> getDeadPlayers(Class<P> playerType)
	{
		return getPlayers(playerType).stream()
				.filter(AUGamePlayer::isDead)
				.collect(toList());
	}
	
	public <P extends AUGamePlayer> List<P> getAlivePlayers(Class<P> playerType)
	{
		return getPlayers(playerType).stream()
				.filter(negate(AUGamePlayer::isDead))
				.collect(toList());
	}

	public Collection<AUGamePlayer> getAlivePlayers()
	{
		return getAlivePlayers(AUGamePlayer.class);
	}

	public List<AUGamePlayer> getDeadPlayers()
	{
		return getDeadPlayers(AUGamePlayer.class);
	}
	
	public ImpostorKillContext kill(Crewmate crewmate, Impostor killer) 
	{
		Location deathLocation = crewmate.getPlayer().getLocation();
		
		Corpse corpse = this.corpseFactory.generateCorpse(crewmate, deathLocation);
		corpse.spawn();
		
		return new ImpostorKillContext(deathLocation, killer);
	}
	
	/*
	 * Tasks
	 */
	public Optional<ShipTask> getTaskAt(Block block)
	{
		return Optional.ofNullable(this.tasksLocations.get(block.getLocation()));
	}
	
	public Optional<ShipTask> getCurrentTask(Crewmate crewmate)
	{
		return Optional.ofNullable(this.crewmatesCurrentTasks.get(crewmate));
	}
	
	public void addTask(ShipTask task, Block representative) 
	{
		verifyInInit("Tasks can only be added");
		
		this.tasksLocations.put(representative.getLocation(), task);
	}
	
	public void setCurrentTask(Crewmate crewmate, ShipTask task) 
	{
		this.crewmatesCurrentTasks.put(crewmate, task);
	}
	
	public void setNoTask(Crewmate crewmate)
	{
		this.crewmatesCurrentTasks.remove(crewmate);
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

		AUGame other = (AUGame) object;

		return Objects.equals(this.id, other.id);
	}

	@Override
	public String toString()
	{
		return String.format("AUGame [id=%s, lobby=%s, map=%s, state=%s, tasks=%s, players=[Impostors(%d): %s | Crewmates(%d): %s]]", 
				this.id.toString().substring(0, 5), 
				this.lobby,
				this.map,
				this.state,
				getTasksNames(), 
				getPlayers(Impostor.class).size(),
				getPlayersNames(Impostor.class),
				getPlayers(Crewmate.class).size(),
				getPlayersNames(Crewmate.class));
	}

	private void verifyInInit(String errorMessage) 
	{
		if(this.state != GameState.INIT)
			throw new UnsupportedOperationException(errorMessage + " during the game's initialization.");
	}

	private boolean isTie() 
	{
		Map<PlayerRole, List<AUGamePlayer>> rolesPlayersLeft = this.players.stream()
				.filter(negate(AUGamePlayer::isDead))
				.collect(groupingBy(AUGamePlayer::getRole));

		//make sure the distinct amounts of players is 1 - so all the teams have the same amount of players
		return rolesPlayersLeft.values().stream()
				.mapToInt(List::size)
				.distinct()
				.count() == 1;
	}
	
	private String getPlayersNames(Class<? extends AUGamePlayer> playerClass) 
	{
		return getPlayers(playerClass).stream()
				.map(impostor -> impostor.getPlayer().getName())
				.collect(joining(", "));
	}
	
	private String getTasksNames() 
	{
		return this.tasksLocations.values().stream()
				.map(ShipTask::getName)
				.collect(joining(", ", "[", "]"));
	}

	public enum GameState 
	{
		INIT,
		PLAYING,
		VOTING;
	}
}