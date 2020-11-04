package mazgani.amongus.events.lobbies;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mazgani.amongus.lobbies.GameLobby;

@RequiredArgsConstructor
public class LobbyFullEvent extends Event
{
	@Getter
	private final GameLobby lobby;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
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
