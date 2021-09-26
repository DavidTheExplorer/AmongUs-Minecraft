package dte.amongus.hooks;

import java.util.List;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuardHook extends PluginHook
{
	public WorldGuardHook(String pluginName) 
	{
		super("WorldGuard");
	}
	
	public ProtectedRegion getRegion(String regionName) 
	{
		return null;
	}
	
	public boolean isInRegion(Player player, String regionName) 
	{
		return false;
	}
	
	public List<ProtectedRegion> getPlayerRegions(Player player)
	{
		return null;
	}
}
