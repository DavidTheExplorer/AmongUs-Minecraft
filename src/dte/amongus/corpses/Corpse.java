package dte.amongus.corpses;

import dte.amongus.games.players.Crewmate;

public interface Corpse
{
	/**
	 * Returns the player that this corpse belongs to(the player who died).
	 * 
	 * @return The player who died.
	 */
	Crewmate whoDied();
	
	
	void spawn();
	
	/**
	 * Despawns this corpse - so it shouldn't be able to be found nor interacted with.
	 */
	void despawn();
}