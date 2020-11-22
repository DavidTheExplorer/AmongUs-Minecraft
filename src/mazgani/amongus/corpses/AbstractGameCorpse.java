package mazgani.amongus.corpses;

import org.bukkit.Location;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public interface AbstractGameCorpse
{
	/**
	 * Gets the player that this corpse belongs to, aka the player who died.
	 * 
	 * @return The player who died.
	 */
	GamePlayer getWhoDied();
	
	/**
	 * Gets the game which this corpse belongs to one of its dead players.
	 * 
	 * @return The game to which this corpse belongs.
	 */
	AUGame getGame();
	
	/**
	 * Spawns this corpse at the provided {@code location}.
	 * <p>
	 * <b>* Note</b> that some corpses might spawn the in nearby locations instead of the provided one, if it's bad to spawn it there.
	 * 
	 * @param location The death location.
	 */
	void spawn(Location deathLocation);
	
	/** Despawns this corpse - so it shouldn't be able to be found nor interacted with. */
	void despawn();
	
	/**
	 * Makes the provided {@code player} to report this body, should open a new discussion.
	 * 
	 * @param reporter The player who found the body.
	 */
	void report(GamePlayer reporter);
}