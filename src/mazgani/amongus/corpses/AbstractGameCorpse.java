package mazgani.amongus.corpses;

import org.bukkit.Location;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public interface AbstractGameCorpse
{
	/**
	 * Gets the player that this corpse belongs to(the player who died).
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
	 * Attempts to spawns this corpse at the provided {@code death location}.
	 * Some corpses might spawn in nearby locations if the provided location is inappropriate for them.
	 * 
	 * @param deathLocation The death location.
	 */
	void spawn(Location deathLocation);
	
	/** Despawns this corpse - so it shouldn't be able to be found nor interacted with. */
	void despawn();
	
	/**
	 * Makes the provided {@code player} to report this corpse, should open a new discussion.
	 * 
	 * @param reporter The player who found the corpse.
	 */
	void report(GamePlayer reporter);
}