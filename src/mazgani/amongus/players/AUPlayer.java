package mazgani.amongus.players;

import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AUPlayer 
{
	@Getter
	private final UUID playerUUID; 
	
	private PlayerStatistics stats;
	
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
