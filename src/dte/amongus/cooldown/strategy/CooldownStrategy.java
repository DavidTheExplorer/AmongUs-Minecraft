package dte.amongus.cooldown.strategy;

import org.bukkit.entity.Player;

import dte.amongus.cooldown.Cooldown;

@FunctionalInterface
public interface CooldownStrategy
{
	/**
	 * Executes a behaviour that involves the provided {@code player}'s cooldown.
	 * 
	 * @param player The player on cooldown.
	 * @param cooldown The player's cooldown.
	 */
	void run(Player player, Cooldown cooldown);
}