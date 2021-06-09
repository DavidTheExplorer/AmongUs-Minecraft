package dte.amongus.corpses.reportablefinder;

import java.util.List;

import org.bukkit.Bukkit;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import dte.amongus.corpses.AbstractCorpse;
import dte.amongus.games.AUGame;
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
	public AbstractCorpse find(Crewmate crewmate) 
	{
		List<ProtectedRegion> playerRegions = this.wgHook.getPlayerRegions(crewmate.getPlayer());
		AUGame game = crewmate.getGame();
		
		return playerRegions.stream()
				.filter(ReportableCorpsesWGFinder::isCorpseRegion)
				.findFirst() //the player might be inside multiple corpse regions, so choose an arbitrary one
				.map(region -> getDeadCrewmate(game, region))
				.map(deadCrewmate -> deadCrewmate.getDeathContext().get().getCorpse())
				.orElse(null);
	}
	
	private static boolean isCorpseRegion(ProtectedRegion region)
	{
		return region.getId().endsWith("-body");
	}
	
	private static Crewmate getDeadCrewmate(AUGame game, ProtectedRegion region) 
	{
		String crewmateName = region.getId().substring(0, region.getId().indexOf("-body"));
		
		return game.getPlayer(Bukkit.getPlayer(crewmateName), Crewmate.class);
	}
}