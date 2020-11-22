package mazgani.amongus.lobbies.displayers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import mazgani.amongus.displayers.DisplaySign;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbyStateListener;

public class LobbySign extends DisplaySign<GameLobby> implements LobbyStateListener
{
	private GameLobby lobby;
	
	public LobbySign(GameLobby type, Sign sign)
	{
		super(type, sign);
		
		this.lobby = this.displayed;
	}

	@Override
	public String[] generateUpdate(boolean firstUpdate)
	{
		if(!this.lobby.isFull()) 
		{
			String mapName = this.lobby.getGameMap().getName();
			
			return getBaseLinesWith("Map: " + mapName);
		}
		return getBaseLinesWith("Restarting...");
	}

	@Override
	public void onLobbyJoin(GameLobby lobby, Player player)
	{
		updateLines();
	}

	@Override
	public void onLobbyLeave(GameLobby lobby, Player player)
	{
		updateLines();
	}

	//returns the constant lines(game id, players amount, etc) and then adds the given additional lines
	private String[] getBaseLinesWith(String... additionalLines) 
	{
		ChatColor statusColor = !this.lobby.isFull() ? ChatColor.GREEN : ChatColor.RED;
		
		List<String> lines = Lists.newArrayList(
				String.format(statusColor + "[AmongUs #%s]", this.lobby.getUUID().toString().substring(0, 5)),
				String.format("%d/%d", this.lobby.getPlayersView().size(), this.lobby.getSettings().getPlayersRequired()));

		lines.addAll(Arrays.asList(additionalLines));

		return lines.toArray(new String[lines.size()]);
	}
}