package dte.amongus.corpses;

import dte.amongus.games.players.Crewmate;

public interface Corpse
{
	/**
	 * Returns the crewmate this corpse belongs to.
	 * 
	 * @return The crewmate who died.
	 */
	Crewmate whoDied();
	
	void spawn();
	
	/**
	 * Despawns this corpse - so it shouldn't be able to be found nor interacted with in any way.
	 */
	void despawn();
}