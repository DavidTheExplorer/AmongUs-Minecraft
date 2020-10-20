package mazgani.amongus.games;

import java.util.UUID;

import mazgani.amongus.enums.Role;

public class GamePlayer
{
	private final UUID playerUUID;
	
	private Role role = Role.CREWMATE;
	private boolean spectator = false;
	
	public GamePlayer(UUID playerUUID) 
	{
		this.playerUUID = playerUUID;
	}
	public UUID getPlayerUUID() 
	{
		return this.playerUUID;
	}
	public Role getRole() 
	{
		return this.role;
	}
	public boolean isSpectator() 
	{
		return this.spectator;
	}
	public void setRole(Role role) 
	{
		this.role = role;
	}
}
