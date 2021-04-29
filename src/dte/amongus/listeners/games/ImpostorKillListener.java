package dte.amongus.listeners.games;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dte.amongus.deathcontext.DeathContext;
import dte.amongus.deathcontext.ImpostorKillContext;
import dte.amongus.events.games.GameWinEvent;
import dte.amongus.events.games.ImpostorKillEvent;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.player.PlayerRole;

public class ImpostorKillListener implements Listener
{
	@EventHandler
	public void onImpostorKill(ImpostorKillEvent event) 
	{
		AUGame game = event.getGame();
		Impostor impostor = event.getContext().getKiller();
		Crewmate crewmate = event.whoDied();
		
		impostor.addKill(crewmate);

		sendKillMessages(impostor, crewmate, game);
		setSpectator(crewmate, new ImpostorKillContext(crewmate.getPlayer().getLocation(), impostor));

		if(game.isWin()) 
		{
			Bukkit.getPluginManager().callEvent(new GameWinEvent(game, PlayerRole.IMPOSTOR));
			return;
		}
		game.spawnCorpse(crewmate, event.getContext().getDeathLocation());
	}
	private void setSpectator(AUGamePlayer gamePlayer, DeathContext deathContext) 
	{
		gamePlayer.setDead(deathContext);

		Player player = gamePlayer.getPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 2));
		
		if(gamePlayer instanceof Impostor) 
		{
			//TODO: add the sabotage map
		}
	}
	private void sendKillMessages(AUGamePlayer impostor, AUGamePlayer crewmate, AUGame game) 
	{
		int totalPlayers = game.getPlayers().size();
		int crewmatesLeft = game.getDeadPlayers(Crewmate.class).size();

		impostor.getPlayer().sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + "You killed " + ChatColor.GOLD + crewmate.getPlayer().getName());
		impostor.getPlayer().sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + crewmatesLeft + ChatColor.GRAY + "/" + ChatColor.GREEN + totalPlayers);

		crewmate.getPlayer().sendMessage(ChatColor.RED + "You were killed by " + impostor.getPlayer().getName() + "!");
		crewmate.getPlayer().sendMessage(ChatColor.RED + "You are now a Ghost. You can only communicate with other ghosts, not with players.");
	}
}
