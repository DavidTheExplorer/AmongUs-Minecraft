package mazgani.amongus;

import org.bukkit.plugin.java.JavaPlugin;

import mazgani.amongus.commands.AmongUSCommand;
import mazgani.amongus.ignores.IgnoresManager;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.players.AUPlayersManager;

public class AmongUs extends JavaPlugin
{
	private AUPlayersManager auPlayersManager;
	private LobbiesManager lobbiesManager;
	private IgnoresManager ignoresManager;
	
	private static AmongUs INSTANCE;
	
	@Override
	public void onEnable() 
	{
		INSTANCE = this;
		
		this.auPlayersManager = new AUPlayersManager();
		this.lobbiesManager = new LobbiesManager();
		this.ignoresManager = new IgnoresManager();
		
		registerCommands();
	}
	public static AmongUs getInstance() 
	{
		return INSTANCE;
	}
	public AUPlayersManager getAUPlayersManager() 
	{
		return this.auPlayersManager;
	}
	public LobbiesManager getLobbiesManager() 
	{
		return this.lobbiesManager;
	}
	public IgnoresManager getIgnoresManager() 
	{
		return this.ignoresManager;
	}
	private void registerCommands() 
	{
		getCommand("amongus").setExecutor(new AmongUSCommand(this.lobbiesManager));
	}
}
