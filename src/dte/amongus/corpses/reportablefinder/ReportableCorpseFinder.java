package dte.amongus.corpses.reportablefinder;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.AUGamePlayer;

public interface ReportableCorpseFinder
{
	AbstractCorpse find(AUGamePlayer gamePlayer);
}