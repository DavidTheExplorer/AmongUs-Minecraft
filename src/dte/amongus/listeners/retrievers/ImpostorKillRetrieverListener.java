package dte.amongus.listeners.retrievers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dte.amongus.deathcontext.ImpostorKillContext;
import dte.amongus.events.games.ImpostorKillEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.AUGameService;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;

public class ImpostorKillRetrieverListener implements Listener
{
	private final AUGameService gameService;

	public ImpostorKillRetrieverListener(AUGameService gameService)
	{
		this.gameService = gameService;
	}

	@EventHandler
	public void onImpostorKill(EntityDamageByEntityEvent event) 
	{
		if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))
			return;

		Player damager = (Player) event.getDamager();
		Player damaged = (Player) event.getEntity();

		if(!this.gameService.onSameGame(damager, damaged))
			return;

		AUGame game = this.gameService.getPlayerGame(damager).get();
		AUGamePlayer damagerGP = game.getPlayer(damager);
		AUGamePlayer damagedGP = game.getPlayer(damaged);

		if(damagerGP instanceof Crewmate)
		{
			damager.sendMessage(ChatColor.RED + "Attack cancelled! He is your friend... or is he?");
			event.setCancelled(true);
			return;
		}
		if(damagerGP instanceof Impostor && damagedGP instanceof Impostor)
		{
			damager.sendMessage(ChatColor.RED + "You cannot kill other impostors.");
			event.setCancelled(true);
			return;
		}
		ImpostorKillContext context = new ImpostorKillContext(damaged.getLocation(), (Impostor) damagerGP);
		Bukkit.getPluginManager().callEvent(new ImpostorKillEvent(game, (Crewmate) damagedGP, context));
	}

	/*private void sendAntiCrewmateAttackMessage(AUGame game, Player damager) 
	{
		String randomTaskName = RandomUtils.randomElement(game.getTasks()).getName();

		damager.sendMessage(ChatColor.RED + String.format("You can't damage while being a crewmate. Maybe Go and %s?!", randomTaskName));
	}*/
}
