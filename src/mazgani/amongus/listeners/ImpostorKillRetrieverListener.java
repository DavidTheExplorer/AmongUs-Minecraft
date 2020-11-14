package mazgani.amongus.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.games.events.ImpostorKillEvent;
import mazgani.amongus.players.Role;

public class ImpostorKillRetrieverListener implements Listener
{
	private final GamesManager gamesManager;
	
	public ImpostorKillRetrieverListener(GamesManager gamesManager)
	{
		this.gamesManager = gamesManager;
	}

	@EventHandler
	public void onImpostorKill(EntityDamageByEntityEvent event) 
	{
		if(!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player))
		{
			return;
		}
		Player damager = (Player) event.getDamager();
		Player damaged = (Player) event.getEntity();
		
		//verify the damager & damaged players are on the same game
		if(!this.gamesManager.onSameGame(damager, damaged)) 
		{
			return;
		}
		AUGame game = this.gamesManager.getPlayerGame(damager.getUniqueId()).get();
		GamePlayer damagerGP = game.getPlayer(damager.getUniqueId());
		GamePlayer damagedGP = game.getPlayer(damager.getUniqueId());
		
		//verify the damager is an impostor and the damaged must be crewmate
		if(damagerGP.getRole() != Role.IMPOSTOR || damagedGP.getRole() != Role.CREWMATE) 
		{
			return;
		}
		Bukkit.getPluginManager().callEvent(new ImpostorKillEvent(damagedGP, damagerGP, game));
	}
}
