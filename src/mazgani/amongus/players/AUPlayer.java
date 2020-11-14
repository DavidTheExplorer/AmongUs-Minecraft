package mazgani.amongus.players;

import java.util.Objects;
import java.util.UUID;

public class AUPlayer 
{
	private final UUID playerUUID; 
	
	private PlayerStatistics stats;
	
	public AUPlayer(UUID playerUUID) 
	{
		this.playerUUID = playerUUID;
	}
	public UUID getPlayerUUID() 
	{
		return this.playerUUID;
	}
	public PlayerStatistics getStats()
	{
		if(this.stats == null)
			this.stats = new PlayerStatistics();
		
		return this.stats;
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