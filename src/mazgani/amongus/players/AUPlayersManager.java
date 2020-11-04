package mazgani.amongus.players;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AUPlayersManager
{
	private final Map<UUID, AUPlayer> playerByUUID = new HashMap<>();
	
	public AUPlayer getPlayer(UUID playerUUID) 
	{
		return this.playerByUUID.get(playerUUID);
	}
	public void register(UUID playerUUID) 
	{
		this.playerByUUID.put(playerUUID, new AUPlayer(playerUUID));
	}
	public void unregister(UUID playerUUID) 
	{
		this.playerByUUID.remove(playerUUID);
	}
}