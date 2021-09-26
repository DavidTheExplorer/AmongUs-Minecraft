package dte.amongus.shiptasks;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;

public interface ShipTask
{
	String getName();
	String getDescription();
	TaskType getType();
	AUGame getGame();
	
	default void onStart(Crewmate crewmate){}
	default void onFinish(Crewmate finisher){}
	enum TaskType 
	{
		SHORT, LONG, COMMON, VISUAL;
	}
}