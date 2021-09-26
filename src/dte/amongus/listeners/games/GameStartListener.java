package dte.amongus.listeners.games;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.RED;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import dte.amongus.events.games.GameStartEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.utils.items.ItemBuilder;

public class GameStartListener implements Listener
{
	private static final ItemBuilder 
	IMPOSTOR_ICON_BUILDER = new ItemBuilder(Material.REDSTONE)
	.named(RED + "You are an Impostor"),
	
	CREWMATE_ICON_BUILDER = new ItemBuilder(Material.FEATHER)
	.named(AQUA + "You are a Crewmate");

	@EventHandler
	public void onGameStart(GameStartEvent event) 
	{
		AUGame game = event.getGame();
		
		sendRoleTitle(game, Crewmate.class, AQUA, "Crewmate", "Work hard to save the Ship!", CREWMATE_ICON_BUILDER.createCopy());
		sendRoleTitle(game, Impostor.class, RED, "IMPOSTOR", "Work smart to conquer the Ship!", IMPOSTOR_ICON_BUILDER.createCopy());
	}

	private void sendRoleTitle(AUGame game, Class<? extends AUGamePlayer> roleClass, ChatColor color, String displayName, String description, ItemStack icon) 
	{
		game.getAlivePlayers(roleClass).stream()
		.map(AUGamePlayer::getPlayer)
		.forEach(gamePlayer -> 
		{
			//title
			gamePlayer.sendTitle(color + "Role: " + displayName, color + description, 0, 80, 20);

			//icon in inventory
			gamePlayer.getInventory().setItem(8, icon);
		});
	}
}
