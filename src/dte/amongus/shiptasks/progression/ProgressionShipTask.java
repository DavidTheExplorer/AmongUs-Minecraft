package dte.amongus.shiptasks.progression;

import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.ShipTask;

public interface ProgressionShipTask extends ShipTask
{
	int getProgression(Crewmate crewmate);
	boolean addProgression(Crewmate crewmate, int amount);
}
