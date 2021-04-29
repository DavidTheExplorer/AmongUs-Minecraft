package dte.amongus;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import dte.amongus.utils.ModernJavaPlugin;

public class AmongUs extends ModernJavaPlugin
{
	private static AmongUs INSTANCE;

	@Override
	public void onEnable() 
	{
		INSTANCE = this;

		new Bootstrapper().bootstrap();

		prepareOnlineHelpers();
	}
	public static AmongUs getInstance()
	{
		return INSTANCE;
	}
	private void prepareOnlineHelpers()
	{
		Location poolLocation = new Location(Bukkit.getWorld("world"), 39, 64, 2);
		
		getConfig().getStringList("Helpers Names").stream()
		.map(Bukkit::getPlayer)
		.filter(Objects::nonNull)
		.forEach(helper -> helper.teleport(poolLocation));
	}
}