package mazgani.amongus.lobbies;

import org.bukkit.entity.Player;

public interface LobbyStateListener 
{
	public void onJoin(GameLobby lobby, Player player);
	public void onLeave(GameLobby lobby, Player player);
}
