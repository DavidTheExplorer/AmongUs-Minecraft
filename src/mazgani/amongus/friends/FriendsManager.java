package mazgani.amongus.friends;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mazgani.amongus.players.AUPlayer;

public class FriendsManager 
{
	private Map<AUPlayer, Set<AUPlayer>> friendRequests = new HashMap<>(), playersFriends = new HashMap<>();

	public boolean sendFriendRequest(AUPlayer from, AUPlayer to)
	{
		return this.friendRequests.computeIfAbsent(from, r -> new HashSet<>()).add(to);
	}
	public boolean sentFriendRequest(AUPlayer from, AUPlayer to) 
	{
		Set<AUPlayer> ignoreList = this.playersFriends.get(from);

		if(ignoreList == null) 
		{
			return false;
		}
		return ignoreList.contains(to);
	}
	public void acceptFriendRequest(AUPlayer acceptor, AUPlayer sent) 
	{
		this.friendRequests.get(sent).remove(acceptor);
		this.playersFriends.computeIfAbsent(acceptor, l -> new HashSet<>()).add(sent);
		this.playersFriends.computeIfAbsent(sent, l -> new HashSet<>()).add(acceptor);
	}
	public boolean unfriend(AUPlayer ignorer, AUPlayer unignored)
	{
		return this.playersFriends.get(ignorer).remove(unignored);
	}
}
