package mazgani.amongus.utilities;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PluginUtilities 
{
	//Container of static methods
	private PluginUtilities(){}
	
	public static void registerListeners(Plugin plugin, Listener... listeners) 
	{
		for(Listener listener : listeners)
		{
			Bukkit.getPluginManager().registerEvents(listener, plugin);
		}
	}
}
