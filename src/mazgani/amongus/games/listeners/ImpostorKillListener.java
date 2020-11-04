package mazgani.amongus.games.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import mazgani.amongus.enums.Role;
import mazgani.amongus.events.games.GameWinEvent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.GamesManager;

public class ImpostorKillListener extends GameListener
{
	public ImpostorKillListener(GamesManager gamesManager)
	{
		super(gamesManager);
	}
	
	@EventHandler
	public void onImpostorKill(EntityDamageByEntityEvent event) 
	{
		if(!(event.getDamager() instanceof Player))
		{
			return;
		}
		if(!(event.getEntity() instanceof Player))
		{
			return;
		}
		Player damager = (Player) event.getDamager();
		Player damaged = (Player) event.getEntity();
		
		if(!this.gamesManager.onSameGame(damager, damaged)) 
		{
			return;
		}
		AUGame mutualGame = this.gamesManager.getPlayerGame(damager.getUniqueId()).get();
		
		//verify the damager is an impostor and the damaged must be crewmate
		Role damagerRole = mutualGame.getPlayer(damager.getUniqueId()).getRole();
		Role damagedRole = mutualGame.getPlayer(damaged.getUniqueId()).getRole();
		
		if(damagerRole != Role.IMPOSTOR || damagedRole != Role.CREWMATE) 
		{
			return;
		}
		GamePlayer damagedGamePlayer = mutualGame.getPlayer(damaged.getUniqueId());
		damagedGamePlayer.setSpectator();
		
		damager.sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + "You killed " + ChatColor.GOLD + damaged.getName());
		damager.sendMessage(ChatColor.YELLOW + "Impostor > " + ChatColor.GREEN + mutualGame.playersLeft(Role.CREWMATE) + ChatColor.GRAY + "/" + ChatColor.GREEN + mutualGame.playersLeft());
		
		if(mutualGame.isWin())
		{
			Bukkit.getPluginManager().callEvent(new GameWinEvent(Role.IMPOSTOR));
		}
	}
}
