package mazgani.amongus.shiptasks;

import org.bukkit.event.Listener;

import mazgani.amongus.games.AUGame;

public interface ShipTask extends Listener
{
	String getName();
	String getDescription();
	AUGame getGame();
}