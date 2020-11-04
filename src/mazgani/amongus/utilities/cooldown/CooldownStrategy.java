package mazgani.amongus.utilities.cooldown;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface CooldownStrategy
{
	/**
	 * Executes a behaviour that involves the provided {@code player}'s cooldown.
	 * @param player the cooldown'ed player
	 * @param cooldown the player's cooldown
	 */
	public void run(Player player, Cooldown cooldown);
}