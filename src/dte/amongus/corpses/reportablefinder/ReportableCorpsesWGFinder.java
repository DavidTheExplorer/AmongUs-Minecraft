package dte.amongus.corpses.reportablefinder;

import java.util.List;

import org.bukkit.Bukkit;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.hooks.WorldGuardHook;

/**
 * Finds a corpse that a player can report based on WorldGuard regions; The player needs to be within a body's region in order to be able to find it.
 */
public class ReportableCorpsesWGFinder implements ReportableCorpseFinder
{
	private final WorldGuardHook wgHook;

	public ReportableCorpsesWGFinder(WorldGuardHook wgHook) 
	{
		this.wgHook = wgHook;
	}

	@Override
	public AbstractCorpse find(AUGamePlayer gamePlayer) 
	{
		List<ProtectedRegion> playerRegions = this.wgHook.getPlayerRegions(gamePlayer.getPlayer());
		
		return playerRegions.stream()
				.filter(this::isCorpseRegion)
				.findFirst() //the player might be inside multiple corpse regions, so choose an arbitrary one
				.map(region -> Bukkit.getPlayer(getDeadCrewmateName(region)))
				.map(deadCrewmate -> gamePlayer.getGame().getPlayer(deadCrewmate, Crewmate.class))
				.map(deadCrewmate -> deadCrewmate.getDeathContext().get().getCorpse())
				.orElse(null);
	}
	private boolean isCorpseRegion(ProtectedRegion region)
	{
		return region.getId().endsWith("-body");
	}
	private String getDeadCrewmateName(ProtectedRegion region) 
	{
		return region.getId().substring(0, region.getId().indexOf("-body"));
	}
}