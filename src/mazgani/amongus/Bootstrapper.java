package mazgani.amongus;

import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import mazgani.amongus.commands.AmongUSCommand;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.games.corpsesfactory.SimpleCorpsesFactory;
import mazgani.amongus.listeners.ImpostorKillRetrieverListener;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.players.AUPlayersManager;
import mazgani.amongus.utilities.cooldown.Cooldown.CooldownBuilder;
import mazgani.amongus.utilities.cooldown.CooldownsManager;

public class Bootstrapper
{
	private final AmongUs amongUs;
	
	private LobbiesManager lobbiesManager;
	private GamesManager gamesManager;
	private CooldownsManager cooldownsManager;
	private AUPlayersManager auPlayersManager;
	
	public Bootstrapper(AmongUs amongUs)
	{
		this.amongUs = amongUs;
	}
	public void bootstrap() 
	{
		setupManagers();
		registerCommands();
		registerListeners();
		sendHelpersToAfkPool("PhoenixBust", "MrGeneralQ");
	}
	private void setupManagers() 
	{
		this.auPlayersManager = new AUPlayersManager();
		this.lobbiesManager = new LobbiesManager();
		this.gamesManager = new GamesManager(new SimpleCorpsesFactory());
		
		//Cooldown
		this.cooldownsManager = new CooldownsManager();
		CooldownBuilder.setCooldownsManager(this.cooldownsManager);
	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.lobbiesManager, this.gamesManager, this.auPlayersManager);
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
}