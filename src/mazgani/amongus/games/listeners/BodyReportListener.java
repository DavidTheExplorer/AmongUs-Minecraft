package mazgani.amongus.games.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.games.events.BodyReportEvent;

public class BodyReportListener implements Listener
{
	@EventHandler
	public void onBodyReported(BodyReportEvent event) 
	{
		AbstractGameCorpse corpse = event.getCorpse();
		corpse.despawn();
		
		for(Player player : event.getGame().getPlayersView())
		{
			player.sendMessage(ChatColor.WHITE + corpse.getWhoDied().getColor().getColoredName() + ChatColor.WHITE + "'s corpse was found!");
		}
	}
}