package dte.amongus.shiptasks;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.type.TaskType;

public interface ShipTask
{
	String getName();
	String getDescription();
	TaskType getType();
	AUGame getGame();
	
	default void onFinish(Crewmate crewmate){}
	default void onStart(Crewmate crewmate){}
}