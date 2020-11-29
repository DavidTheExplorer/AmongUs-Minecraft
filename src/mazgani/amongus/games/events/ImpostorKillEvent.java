package mazgani.amongus.games.events;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class ImpostorKillEvent extends Event
{
	private final AUGame game;
	private final GamePlayer impostor, deadCrewmate;
	private final Location deathLocation;
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	public ImpostorKillEvent(GamePlayer impostor, GamePlayer deadCrewmate, AUGame game) 
	{
		this.impostor = impostor;
		this.deadCrewmate = deadCrewmate;
		this.deathLocation = deadCrewmate.getAUPlayer().getPlayer().getLocation();
		this.game = game;
	}
	public GamePlayer getImpostor() 
	{
		return this.impostor;
	}
	public GamePlayer getDeadCrewmate() 
	{
		return this.deadCrewmate;
	}
	public AUGame getGame() 
	{
		return this.game;
	}
	public Location getDeathLocation() 
	{
		return this.deathLocation;
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
