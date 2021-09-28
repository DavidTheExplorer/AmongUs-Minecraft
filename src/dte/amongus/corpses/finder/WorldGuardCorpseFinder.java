package dte.amongus.corpses.finder;

import java.util.Optional;

import org.bukkit.Bukkit;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import dte.amongus.corpses.Corpse;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.hooks.WorldGuardHook;

/**
 * Finds a corpse that a player can report based on WorldGuard regions; The player needs to be within a body's region in order to be able to find it.
 */
public class WorldGuardCorpseFinder implements CorpseFinder
{
	private final WorldGuardHook wgHook;

	public WorldGuardCorpseFinder(WorldGuardHook wgHook) 
	{
		this.wgHook = wgHook;
	}

	@Override
	public Optional<Corpse> findFor(Crewmate crewmate) 
	{
		return this.wgHook.getPlayerRegions(crewmate.getPlayer()).stream()
				.filter(this::isCorpseRegion)
				.findFirst() //the player might be inside multiple corpse regions, so an arbitrary one is chosen
				.map(region -> getDeadCrewmate(region, crewmate.getGame()))
				.flatMap(deadCrewmate -> deadCrewmate.getDeathContext().get().getCorpse());
	}
	
	private boolean isCorpseRegion(ProtectedRegion region)
	{
		return region.getId().endsWith("-body");
	}
	
	private Crewmate getDeadCrewmate(ProtectedRegion region, AUGame game) 
	{
		String crewmateName = region.getId().substring(0, region.getId().indexOf("-body"));
		
		return game.getPlayer(Bukkit.getPlayer(crewmateName), Crewmate.class).get();
	}
}