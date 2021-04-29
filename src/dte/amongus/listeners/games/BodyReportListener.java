package dte.amongus.listeners.games;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.events.games.BodyReportEvent;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.internal.AUGameUtils;
import dte.amongus.internal.GamePlayerUtils;

public class BodyReportListener implements Listener
{
	@EventHandler
	public void onBodyReported(BodyReportEvent event) 
	{
		AbstractCorpse corpse = event.getFoundCorpse();
		corpse.despawn();
		
		notifyDeath(corpse.whoDied());
	}
	
	private void notifyDeath(AUGamePlayer whoDied) 
	{
		String coloredDeadName = GamePlayerUtils.getColoredName(whoDied);
		
		for(Player player : AUGameUtils.getBukkitPlayers(whoDied.getGame()))
			player.sendMessage(coloredDeadName + ChatColor.WHITE + "'s corpse was found!");
	}
}