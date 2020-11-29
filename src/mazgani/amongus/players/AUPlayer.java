package mazgani.amongus.players;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mazgani.amongus.players.visual.VisibilityManager;

public class AUPlayer 
{
	private final UUID playerUUID; 
	private final VisibilityManager visibilityManager;
	private PlayerStatistics stats;
	
	public AUPlayer(UUID playerUUID) 
	{
		this.playerUUID = playerUUID;
		this.visibilityManager = new VisibilityManager(this);
	}
	public UUID getPlayerUUID() 
	{
		return this.playerUUID;
	}
	public Player getPlayer() 
	{
		return Bukkit.getPlayer(this.playerUUID);
	}
	public PlayerStatistics getStats()
	{
		if(this.stats == null)
			this.stats = new PlayerStatistics();
		
		return this.stats;
	}
	public VisibilityManager getVisibilityManager() 
	{
		return this.visibilityManager;
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(this.playerUUID);
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
		
		AUPlayer other = (AUPlayer) object;
		
		return Objects.equals(this.playerUUID, other.playerUUID);
	}
}