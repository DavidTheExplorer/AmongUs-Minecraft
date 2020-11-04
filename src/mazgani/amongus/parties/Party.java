package mazgani.amongus.parties;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Party 
{
	private UUID leaderUUID;
	private final Set<UUID> membersUUIDs = new HashSet<>();
	
	public Party(UUID leaderUUID) 
	{
		this.leaderUUID = leaderUUID;
	}
	public UUID getLeaderUUID() 
	{
		return this.leaderUUID;
	}
	public Set<UUID> getMembersUUIDs()
	{
		return this.membersUUIDs;
	}
}
