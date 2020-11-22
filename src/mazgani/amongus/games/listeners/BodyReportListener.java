package mazgani.amongus.games.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.events.BodyReportEvent;

public class BodyReportListener implements Listener
{
	@EventHandler
	public void onBodyReported(BodyReportEvent event) 
	{
		AbstractGameCorpse corpse = event.getCorpseFound();
		corpse.despawn();
		
		notifyDeath(event.getCorpseFound().getWhoDied());
	}
	private void notifyDeath(GamePlayer dead) 
	{
		String coloredDeadName = dead.getColor().getColoredName();
		
		for(Player player : dead.getGame().getPlayersView())
		{
			player.sendMessage(ChatColor.WHITE + coloredDeadName + ChatColor.WHITE + "'s corpse was found!");
		}
	}
}