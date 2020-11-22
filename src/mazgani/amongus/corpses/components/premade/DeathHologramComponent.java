package mazgani.amongus.corpses.components.premade;

import org.bukkit.ChatColor;
import org.bukkit.Location;

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
	
	public DeathHologramComponent(BasicGameCorpse corpse, EquallbleHologram base, GamePlayer whoDied, AUGame game) 
	{
		super(corpse, base);
		
		this.corpse = corpse;
		this.whoDied = whoDied;
		this.game = game;
	}
	
	@Override
	public void spawn()
	{
		Location spawnLocation = this.spawnedHologram.getLocation().add(0, 2, 0);
		this.spawnedHologram.teleport(spawnLocation);
		
		this.spawnedHologram.appendTextLine(this.whoDied.getColor().getColor() + this.whoDied.getPlayer().getName() + ChatColor.WHITE + " died here.");
		this.spawnedHologram.appendTextLine(ChatColor.WHITE + ">> " + ChatColor.GOLD + "Click to REPORT" + ChatColor.WHITE + " <<").setTouchHandler(getReportTouchHandler());
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