package dte.amongus.listeners.games;

import static org.bukkit.ChatColor.WHITE;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dte.amongus.corpses.Corpse;
import dte.amongus.events.games.BodyReportEvent;
import dte.amongus.games.players.Crewmate;
import dte.amongus.internal.AUGameUtils;
import dte.amongus.internal.GamePlayerUtils;

public class BodyReportListener implements Listener
{
	@EventHandler
	public void onBodyReported(BodyReportEvent event) 
	{
		Corpse corpse = event.getFoundCorpse();
		corpse.despawn();
		
		notifyDeath(corpse.whoDied());
	}
	
	private void notifyDeath(Crewmate whoDied) 
	{
		String coloredDeadName = GamePlayerUtils.getColoredName(whoDied);
		
		for(Player player : AUGameUtils.getBukkitPlayers(whoDied.getGame()))
			player.sendMessage(coloredDeadName + WHITE + "'s corpse was found!");
	}
}