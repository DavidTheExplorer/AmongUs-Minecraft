package mazgani.amongus.corpses.components.premade;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.holograms.HologramComponent;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.holograms.equallable.EquallableHologram;

public class DeathHologramComponent extends HologramComponent
{
	private final int height;
	
	public DeathHologramComponent(BasicGameCorpse corpse, EquallableHologram hiddenBase, int height) 
	{
		super(corpse, hiddenBase);
		
		this.height = height;
	}
	
	@Override
	public void spawn()
	{
		super.spawn();
		
		Location spawnLocation = this.hologram.getLocation().add(0, this.height, 0);
		this.hologram.teleport(spawnLocation);
		
		this.hologram.appendTextLine(getColoredPlayerName() + ChatColor.WHITE + " died here.");
		this.hologram.appendTextLine(ChatColor.WHITE + ">> " + ChatColor.GOLD + "Click to REPORT" + ChatColor.WHITE + " <<").setTouchHandler(getReportTouchHandler());
	}
	private TouchHandler getReportTouchHandler() 
	{
		return reporter -> 
		{
			GamePlayer reporterGP = this.parentCorpse.getGame().getPlayer(reporter.getUniqueId());
			
			this.parentCorpse.report(reporterGP);
		};
	}
}