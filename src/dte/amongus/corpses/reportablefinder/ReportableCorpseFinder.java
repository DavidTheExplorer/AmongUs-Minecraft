package dte.amongus.corpses.reportablefinder;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.Crewmate;

public interface ReportableCorpseFinder
{
	AbstractCorpse find(Crewmate crewmate);
}