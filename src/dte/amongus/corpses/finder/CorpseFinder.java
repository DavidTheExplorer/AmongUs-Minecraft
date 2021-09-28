package dte.amongus.corpses.finder;

import java.util.Optional;

import dte.amongus.corpses.Corpse;
import dte.amongus.games.players.Crewmate;

@FunctionalInterface
public interface CorpseFinder
{
	Optional<Corpse> findFor(Crewmate crewmate);
}