package dte.amongus.corpses;

import dte.amongus.games.players.AUGamePlayer;

public interface AbstractCorpse
{
	/**
	 * Returns the player that this corpse belongs to(the player who died).
	 * 
	 * @return The player who died.
	 */
	AUGamePlayer whoDied();
	
	
	void spawn();
	
	/**
	 * Despawns this corpse - so it shouldn't be able to be found nor interacted with.
	 */
	void despawn();
}