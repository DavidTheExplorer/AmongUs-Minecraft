package mazgani.amongus;

import org.bukkit.plugin.java.JavaPlugin;

public class AmongUs extends JavaPlugin
{
	private static AmongUs instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		new Bootstrapper(this).bootstrap();
	}
	public static AmongUs getInstance() 
	{
		return instance;
	}
}