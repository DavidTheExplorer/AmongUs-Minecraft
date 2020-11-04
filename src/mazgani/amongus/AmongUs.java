package mazgani.amongus;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class AmongUs extends JavaPlugin
{
	@Getter
	private static AmongUs instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		new Bootstrapper(this);
	}
}