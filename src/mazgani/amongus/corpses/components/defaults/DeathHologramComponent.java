package mazgani.amongus.corpses.components.defaults;

import org.bukkit.ChatColor;

import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.holograms.HologramComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.holograms.EquallbleHologram;

public class DeathHologramComponent extends HologramComponent
{
	private final BasicGameCorpse corpse;
	private final GamePlayer whoDied;
	private final AUGame game;
	
	public DeathHologramComponent(EquallbleHologram base, BasicGameCorpse corpse, GamePlayer whoDied, AUGame game) 
	{
		super(base);
		
		this.corpse = corpse;
		this.whoDied = whoDied;
		this.game = game;
	}
	
	@Override
	public void spawn()
	{
		super.spawn();
		
		this.hologram.teleport(this.hologram.getLocation().add(0, 2, 0));
		this.hologram.appendTextLine(this.whoDied.getColor().getColor() + this.whoDied.getPlayer().getName() + ChatColor.WHITE + " died here.");
		this.hologram.appendTextLine(ChatColor.WHITE + ">> " + ChatColor.GOLD + "Click to REPORT" + ChatColor.WHITE + " <<").setTouchHandler(getReportTouchHandler());
	}
	private TouchHandler getReportTouchHandler() 
	{
		return reporter -> 
		{
			GamePlayer reporterGP = this.game.getPlayer(reporter.getUniqueId());
			
			this.corpse.report(reporterGP);
		};
	}
}