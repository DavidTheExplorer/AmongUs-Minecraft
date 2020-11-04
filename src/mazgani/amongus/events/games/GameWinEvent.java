package mazgani.amongus.events.games;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import mazgani.amongus.enums.Role;

@RequiredArgsConstructor
public class GameWinEvent extends Event
{
	@Getter
	private final Role winningRole;
	
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
