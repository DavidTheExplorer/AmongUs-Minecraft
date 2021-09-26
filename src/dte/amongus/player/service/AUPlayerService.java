package dte.amongus.player.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.player.AUPlayer;

public class AUPlayerService
{
	private final Map<UUID, AUPlayer> playerByUUID = new HashMap<>();
	
	public AUPlayer getAUPlayer(UUID playerUUID)
	{
		return this.playerByUUID.get(playerUUID);
	}
	
	public AUPlayer getAUPlayer(AUGamePlayer gamePlayer)
	{
		UUID playerUUID = gamePlayer.getPlayer().getUniqueId();
		
		return getAUPlayer(playerUUID);
	}
	
	public void load(UUID playerUUID)
	{
		this.playerByUUID.put(playerUUID, new AUPlayer(playerUUID));
	}
	
	public void unload(UUID playerUUID)
	{
		this.playerByUUID.remove(playerUUID);
	}
}