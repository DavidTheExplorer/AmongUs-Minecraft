package dte.amongus.player;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import dte.amongus.player.statistics.PlayerStatistics;
import dte.amongus.player.visual.VisibilityManager;

public class AUPlayer 
{
	private final UUID playerUUID;
	private PlayerStatistics stats;
	private final VisibilityManager visibilityManager = new VisibilityManager(this);
	
	public AUPlayer(UUID playerUUID) 
	{
		this.playerUUID = playerUUID;
	}
	public UUID getUUID() 
	{
		return this.playerUUID;
	}
	public OfflinePlayer getOfflinePlayer() 
	{
		return Bukkit.getOfflinePlayer(this.playerUUID);
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