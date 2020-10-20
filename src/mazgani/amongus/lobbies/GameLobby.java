package mazgani.amongus.lobbies;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;

import com.google.common.collect.Sets;

import mazgani.amongus.enums.Role;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.shiptasks.list.WiresTask;
import mazgani.amongus.utilities.RandomUtilities;

public class GameLobby
{
	private UUID uuid;
	private final Location spawnLocation;
	private final int crewmates, impostors;
	private final Map<UUID, GamePlayer> players = new HashMap<>();
	
	GameLobby(UUID uuid, Location spawnLocation, int crewmates, int impostors)
	{
		this.uuid = uuid;
		this.spawnLocation = spawnLocation;
		this.crewmates = crewmates;
		this.impostors = impostors;
	}
	public UUID getUUID() 
	{
		return this.uuid;
	}
	public Location getSpawnLocation() 
	{
		return this.spawnLocation;
	}
	public boolean addPlayer(UUID playerUUID)
	{
		if(hasEnoughPlayers())
		{
			return false;
		}
		this.players.put(playerUUID, new GamePlayer(playerUUID));
		return true;
	}
	public boolean hasEnoughPlayers()
	{
		int playersRequired = (this.crewmates + this.impostors);
		
		return this.players.size() == playersRequired;
	}
	public boolean removePlayer(UUID playerUUID) 
	{
		return this.players.remove(playerUUID) != null;
	}
	public boolean contains(UUID playerUUID) 
	{
		return this.players.containsKey(playerUUID);
	}
	public AUGame startGame() 
	{
		determineRoles();
		
		AUGame game = new AUGame(this, new HashMap<>(this.players));
		game.setTasks(Sets.newHashSet(new WiresTask(game)));
		
		return game;
	}
	private void determineRoles() 
	{
		for(int i = 1; i <= this.impostors; i++) 
		{
			GamePlayer randomCrewmate = RandomUtilities.from(this.players.values()).getElementThat(player -> player.getRole() == Role.CREWMATE);
			
			randomCrewmate.setRole(Role.IMPOSTOR);
		}
	}
}
