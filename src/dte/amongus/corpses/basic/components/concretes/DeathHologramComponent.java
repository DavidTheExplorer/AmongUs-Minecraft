package dte.amongus.corpses.basic.components.concretes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.holograms.HologramComponent;
import dte.amongus.events.games.BodyReportEvent;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.holograms.EquallableHologram;
import dte.amongus.internal.GamePlayerUtils;

public class DeathHologramComponent extends HologramComponent
{
	private final int height;
	
	public DeathHologramComponent(BasicCorpse corpse, EquallableHologram hiddenBase, int height) 
	{
		super(corpse, hiddenBase);
		
		this.height = height;
	}
	
	@Override
	public void spawn()
	{
		super.spawn();
		
		Location fixedLocation = this.hologram.getLocation().add(0, this.height, 0);
		this.hologram.teleport(fixedLocation);
		
		this.hologram.appendTextLine(GamePlayerUtils.getColoredName(getParentCorpse().whoDied()) + ChatColor.WHITE + " died here.");
		this.hologram.appendTextLine(ChatColor.WHITE + ">> " + ChatColor.GOLD + "Click to REPORT" + ChatColor.WHITE + " <<").setTouchHandler(createReportTouchHandler());
	}
	
	private TouchHandler createReportTouchHandler() 
	{
		return reporter ->
		{
			BasicCorpse corpse = getParentCorpse();
			AUGamePlayer reporterGP = corpse.whoDied().getGame().getPlayer(reporter);
			
			Bukkit.getPluginManager().callEvent(new BodyReportEvent(corpse, reporterGP));
		};
	}
}