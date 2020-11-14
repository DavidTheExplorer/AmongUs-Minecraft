package mazgani.amongus.friends;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mazgani.amongus.players.AUPlayer;

public class FriendsManager 
{
	private final Map<AUPlayer, Set<AUPlayer>> playersRequests = new HashMap<>(), playersFriends = new HashMap<>();
	
	public boolean request(AUPlayer from, AUPlayer to)
	{
		return this.playersRequests.computeIfAbsent(from, f -> new HashSet<>()).add(to);
	}
	public boolean sentRequest(AUPlayer from, AUPlayer to) 
	{
		Set<AUPlayer> fromRequests = this.playersRequests.get(from);
		
		if(fromRequests == null) 
		{
			return false;
		}
		return fromRequests.contains(to);
	}
	public void accept(AUPlayer acceptor, AUPlayer whoSent) 
	{
		this.playersRequests.get(whoSent).remove(acceptor);
		this.playersFriends.computeIfAbsent(acceptor, a -> new HashSet<>()).add(whoSent);
		this.playersFriends.computeIfAbsent(whoSent, ws -> new HashSet<>()).add(acceptor);
	}
	public boolean unfriend(AUPlayer player, AUPlayer target)
	{
		Set<AUPlayer> playerFriends = this.playersFriends.get(player);
		
		if(!playerFriends.contains(target)) 
		{
			return false;
		}
		return playerFriends.remove(target);
	}
}
