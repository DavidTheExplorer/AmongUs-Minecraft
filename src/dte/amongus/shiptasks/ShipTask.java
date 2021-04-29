package dte.amongus.shiptasks;

import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;

public interface ShipTask
{
	String getName();
	String getDescription();
	AUGame getGame();
	
	void setFinished(AUGamePlayer gamePlayer);
	boolean hasFinished(AUGamePlayer gamePlayer);
}