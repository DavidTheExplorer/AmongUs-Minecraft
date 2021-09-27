package dte.amongus.listeners.games;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.UNDERLINE;

import org.bukkit.Bukkit;
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
	private static final String 
	IMPOSTOR_PREFIX = DARK_RED + "Impostor" + RED + " > ",
	CREWMATE_PREFIX = GREEN + "Crewmate" + AQUA + " > ";
	
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
		game.kill(crewmate, impostor);
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
	
	private void sendKillMessages(Impostor impostor, Crewmate crewmate, AUGame game) 
	{
		int crewmatesLeft = game.getAlivePlayers(Crewmate.class).size() - game.getAlivePlayers(Impostor.class).size();
		impostor.getPlayer().sendMessage(IMPOSTOR_PREFIX + DARK_AQUA + crewmate.getPlayer().getName() + GRAY + " ate it! (" + AQUA + crewmatesLeft + GRAY + " Crewmates Left).");

		crewmate.getPlayer().sendMessage(CREWMATE_PREFIX + GRAY + "The Impostor " + RED + impostor.getPlayer().getName() + GRAY + " killed you :(");
		crewmate.getPlayer().sendMessage(CREWMATE_PREFIX + GRAY + "You are now a " + AQUA + "Ghost! " + UNDERLINE + "You can still finish your tasks" + GRAY + ", but not communicate with alive players.");
	}
}
