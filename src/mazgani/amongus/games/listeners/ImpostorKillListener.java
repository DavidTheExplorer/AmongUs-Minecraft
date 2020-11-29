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
import mazgani.amongus.players.GameRole;

public class ImpostorKillListener implements Listener
{
	@EventHandler
	public void onImpostorKill(ImpostorKillEvent event) 
	{
		AUGame game = event.getGame();
		GamePlayer impostor = event.getImpostor();
		GamePlayer crewmate = event.getDeadCrewmate();
		
		sendKillMessages(impostor, crewmate, game);
		setSpectator(crewmate);
		
		if(game.isWin()) 
		{
			Bukkit.getPluginManager().callEvent(new GameWinEvent(GameRole.IMPOSTOR));
			return;
		}
		game.spawnCorpse(crewmate, event.getDeathLocation());
	}
	private void setSpectator(GamePlayer gamePlayer) 
	{
		gamePlayer.setSpectator();

		Player player = gamePlayer.getAUPlayer().getPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 2));
		
		if(gamePlayer.getRole() == GameRole.IMPOSTOR) 
		{
			//add the sabotage map
		}
	}
	private void sendKillMessages(GamePlayer impostor, GamePlayer crewmate, AUGame game) 
	{
		Player impostorPlayer = impostor.getAUPlayer().getPlayer();
		Player crewmatePlayer = crewmate.getAUPlayer().getPlayer();
		
		impostorPlayer.sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + "You killed " + ChatColor.GOLD + crewmatePlayer.getName());
		impostorPlayer.sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + game.playersLeft(GameRole.CREWMATE) + ChatColor.GRAY + "/" + ChatColor.GREEN + game.playersLeft());

		crewmatePlayer.sendMessage(ChatColor.RED + "You were killed by " + impostorPlayer.getName() + "!");
		crewmatePlayer.sendMessage(ChatColor.RED + "You are now a Ghost. You can only communicate with other ghosts, not with players.");
	}
}
