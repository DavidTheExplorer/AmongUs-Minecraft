package mazgani.amongus.lobbies.events;

import org.bukkit.event.HandlerList;

import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.lobbies.GameLobby;

public class LobbyJoinEvent extends LobbyEvent
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private final GamePlayer player;
	
	public LobbyJoinEvent(GameLobby lobby, GamePlayer player)
	{
		super(lobby);
		
		this.player = player;
	}
	public GamePlayer getPlayer() 
	{
		return this.player;
	}
	
	@Override
    public HandlerList getHandlers() 
	{
        return HANDLERS;
    }
    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }
}
