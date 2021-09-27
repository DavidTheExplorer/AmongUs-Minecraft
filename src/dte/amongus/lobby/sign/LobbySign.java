package dte.amongus.lobby.sign;

import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import dte.amongus.displayers.DisplaySign;
import dte.amongus.lobby.AULobby;
import dte.amongus.lobby.listeners.LobbyStateListener;

public class LobbySign extends DisplaySign implements LobbyStateListener
{
	private final AULobby lobby;

	public LobbySign(Sign sign, AULobby lobby)
	{
		super(sign);

		this.lobby = lobby;	
		
		setBaseLinesSupplier(() -> new String[]
				{
						String.format(getLobbyColor() + "[AmongUs #%s]", this.lobby.getID().toString().substring(0, 5)), 
						String.format("%d/%d", this.lobby.getPlayers().size(), this.lobby.getGameSettings().getPlayersRequired())
				});
	}

	@Override
	public String[] createUpdate(boolean firstUpdate)
	{
		if(this.lobby.isFull())
			return baseLinesWith("Restarting...");
		else
			return baseLinesWith("Map: " + this.lobby.getGameMap().getName());
	}

	@Override
	public void onLobbyJoin(AULobby lobby, Player player)
	{
		update(false);
	}

	@Override
	public void onLobbyLeave(AULobby lobby, Player player)
	{
		update(false);
	}

	private ChatColor getLobbyColor()
	{
		return this.lobby.isFull() ? RED : GREEN;
	}
}