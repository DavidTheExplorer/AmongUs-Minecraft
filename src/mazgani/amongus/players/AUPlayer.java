package mazgani.amongus.players;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import mazgani.amongus.stats.PlayerStatistics;

public class AUPlayer 
{
	private final UUID playerUUID;

	private PlayerStatistics stats;

	private final Set<AUPlayer> friends = new HashSet<>();

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
	public boolean addFriend(AUPlayer player) 
	{
		return this.friends.add(player);
	}
	public boolean removeFriend(AUPlayer player) 
	{
		return this.friends.remove(player);
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
