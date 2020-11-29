package mazgani.amongus.parties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Party 
{
	private UUID leaderUUID;
	private final Map<UUID, PartyRole> membersRoles = new HashMap<>();
	
	public Party(UUID leaderUUID) 
	{
		this.leaderUUID = leaderUUID;
	}
	public UUID getLeaderUUID() 
	{
		return this.leaderUUID;
	}
	public boolean promote(UUID playerUUID) 
	{
		PartyRole nextRole = this.membersRoles.get(playerUUID).getNextRole();
		
		if(nextRole == null) 
		{
			return false;
		}
		this.membersRoles.put(playerUUID, nextRole);
		return true;
	}
	public boolean demote(UUID playerUUID) 
	{
		PartyRole previousRank = this.membersRoles.get(playerUUID).getPreviousRole();
		
		if(previousRank == null) 
		{
			return false;
		}
		this.membersRoles.put(playerUUID, previousRank);
		return true;
	}
	public Set<UUID> getUnmodifiableMembersUUIDs()
	{
		return Collections.unmodifiableSet(this.membersRoles.keySet());
	}
}
