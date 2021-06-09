package dte.amongus.corpses.reportablefinder;

import dte.amongus.corpses.Corpse;
import dte.amongus.games.players.Crewmate;

public interface ReportableCorpseFinder
{
	Corpse find(Crewmate crewmate);
}