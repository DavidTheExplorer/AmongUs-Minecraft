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
	public LobbySign(GameLobby type, Sign sign)
	{
		super(type, sign);
	}
	
	@Override
	public String[] generateUpdate(GameLobby lobby, boolean firstUpdate)
	{
		return lobby.isFull() ? getBaseLinesWith(lobby, "Restarting...") : getBaseLinesWith(lobby, "Map: Default");
	}
	
	@Override
	public void onJoin(GameLobby lobby, Player player) 
	{
		updateLines();
	}

	@Override
	public void onLeave(GameLobby lobby, Player player) 
	{
		updateLines();
	}
	
	//gets the basic sign lines(game id, players amount, etc) including the given additional lines
	private String[] getBaseLinesWith(GameLobby lobby, String... additionalLines) 
	{
		ChatColor lobbyStatusColor = !lobby.isFull() ? ChatColor.GREEN : ChatColor.RED;
		String lobbyIDLine = String.format(lobbyStatusColor + "[AmongUs #%s]", lobby.getUUID().toString().substring(0, 5));
		String playersLine = String.format("%d/%d", lobby.getPlayersView().size(), lobby.getPlayersRequired());
		
		List<String> lines = Lists.newArrayList(lobbyIDLine, playersLine);
		lines.addAll(Arrays.asList(additionalLines));
		
		return lines.toArray(new String[lines.size()]);
	}
}