package dte.amongus.shiptasks;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.type.TaskType;

public interface ShipTask
{
	String getName();
	String getDescription();
	TaskType getType();
	AUGame getGame();
	
	void setFinished(AUGamePlayer gamePlayer);
	boolean hasFinished(AUGamePlayer gamePlayer);
	
	default void onFinish(AUGamePlayer gamePlayer){}
	default void onStart(AUGamePlayer gamePlayer){}
}