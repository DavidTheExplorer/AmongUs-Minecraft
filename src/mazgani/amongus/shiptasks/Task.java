package mazgani.amongus.shiptasks;

import org.bukkit.event.Listener;

import mazgani.amongus.games.AUGame;

public interface Task extends Listener
{
	String getName();
	AUGame getGame();
}