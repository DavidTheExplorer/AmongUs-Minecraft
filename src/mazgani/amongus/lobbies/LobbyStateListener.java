package mazgani.amongus.lobbies;

import org.bukkit.entity.Player;

public interface LobbyStateListener 
{
	void onLobbyJoin(GameLobby lobby, Player player);
	void onLobbyLeave(GameLobby lobby, Player player);
}
