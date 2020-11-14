package mazgani.amongus;

import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import mazgani.amongus.commands.AmongUSCommand;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.listeners.ImpostorKillRetrieverListener;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.utilities.cooldown.Cooldown.CooldownBuilder;
import mazgani.amongus.utilities.cooldown.CooldownsManager;

public class Bootstrapper
{
	private final AmongUs amongUs;
	
	private LobbiesManager lobbiesManager;
	private GamesManager gamesManager;
	private CooldownsManager cooldownsManager;
	
	public Bootstrapper(AmongUs amongUs)
	{
		this.amongUs = amongUs;
	}
	public void setup() 
	{
		setupManagers();
		registerCommands();
		registerListeners();
		sendHelpersToAfkPool("PhoenixBust", "MrGeneralQ");
	}
	private void setupManagers() 
	{
		this.lobbiesManager = new LobbiesManager();
		this.gamesManager = new GamesManager();

		this.cooldownsManager = new CooldownsManager();
		CooldownBuilder.setCooldownsManager(this.cooldownsManager);
	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.lobbiesManager, this.gamesManager);
		this.amongUs.getCommand("amongus").setExecutor(amongUsCommand);
		this.amongUs.getCommand("amongus").setTabCompleter(amongUsCommand);
	}
	private void registerListeners() 
	{
		Bukkit.getPluginManager().registerEvents(new ImpostorKillRetrieverListener(this.gamesManager), this.amongUs);
	}
	private void sendHelpersToAfkPool(String... helpersNames)
	{
		Location afkPoolLocation = new Location(Bukkit.getWorld("world"), 39, 64, 2);
		
		Arrays.stream(helpersNames)
		.map(Bukkit::getPlayer)
		.filter(Objects::nonNull)
		.forEach(helper -> helper.teleport(afkPoolLocation));
	}
	public static class lol
	{
		@Override
		protected void finalize() throws Throwable 
		{
			System.out.println("sys");
		}
	}
}