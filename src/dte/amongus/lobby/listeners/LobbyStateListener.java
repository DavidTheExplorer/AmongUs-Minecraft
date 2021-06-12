package dte.amongus.lobby.listeners;

import org.bukkit.entity.Player;

import dte.amongus.lobby.AULobby;

public interface LobbyStateListener 
{
	void onLobbyJoin(AULobby lobby, Player player);
	void onLobbyLeave(AULobby lobby, Player player);
}