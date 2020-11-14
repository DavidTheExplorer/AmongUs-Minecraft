package mazgani.amongus.games.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.events.GameWinEvent;
import mazgani.amongus.games.events.ImpostorKillEvent;
import mazgani.amongus.players.Role;

public class ImpostorKillListener implements Listener
{
	@EventHandler
	public void onImpostorKill(ImpostorKillEvent event) 
	{
		AUGame game = event.getGame();

		GamePlayer impostor = event.getImpostor();
		GamePlayer crewmate = event.getDeadCrewmate();

		Player impostorPlayer = impostor.getPlayer();
		Player crewmatePlayer = crewmate.getPlayer();

		sendKillMessages(impostorPlayer, crewmatePlayer, game);
		setSpectator(crewmate);

		if(game.isWin()) 
		{
			Bukkit.getPluginManager().callEvent(new GameWinEvent(Role.IMPOSTOR));
			return;
		}
		game.spawnCorpse(crewmate, event.getDeathLocation());
	}
	private void setSpectator(GamePlayer gamePlayer) 
	{
		gamePlayer.setSpectator();

		Player player = gamePlayer.getPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 2));
	}
	private void sendKillMessages(Player impostor, Player crewmate, AUGame game) 
	{
		impostor.sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + "You killed " + ChatColor.GOLD + crewmate.getName());
		impostor.sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + game.playersLeft(Role.CREWMATE) + ChatColor.GRAY + "/" + ChatColor.GREEN + game.playersLeft());

		crewmate.sendMessage(ChatColor.RED + "You were killed by " + impostor.getName() + "!");
		crewmate.sendMessage(ChatColor.RED + "You are now a Ghost. You can only communicate with other ghosts, not with players.");
	}
}
