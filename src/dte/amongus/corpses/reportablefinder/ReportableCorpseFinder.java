package dte.amongus.corpses.reportablefinder;

import dte.amongus.corpses.Corpse;
import dte.amongus.games.players.Crewmate;

@FunctionalInterface
public interface ReportableCorpseFinder
{
	Corpse findFor(Crewmate crewmate);
}