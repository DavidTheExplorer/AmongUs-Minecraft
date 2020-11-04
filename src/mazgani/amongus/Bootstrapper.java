package mazgani.amongus;

import java.util.Arrays;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import lombok.Getter;
import mazgani.amongus.commands.AmongUSCommand;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.ignores.IgnoresManager;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.players.AUPlayersManager;
import mazgani.amongus.utilities.cooldown.Cooldown.CooldownBuilder;
import mazgani.amongus.utilities.cooldown.CooldownsManager;

public class Bootstrapper
{
	private final AmongUs amongUs;

	@Getter
	private AUPlayersManager auPlayersManager;

	@Getter
	private LobbiesManager lobbiesManager;

	@Getter
	private IgnoresManager ignoresManager;

	@Getter
	private GamesManager gamesManager;

	@Getter
	private CooldownsManager cooldownsManager;

	public Bootstrapper(AmongUs amongUs)
	{
		this.amongUs = amongUs;

		setupManagers();
		registerCommands();
		registerListeners();
		sendHelpersToAfkPool();
	}
	private void setupManagers() 
	{
		this.auPlayersManager = new AUPlayersManager();
		this.lobbiesManager = new LobbiesManager();
		this.ignoresManager = new IgnoresManager();
		this.gamesManager = new GamesManager();

		this.cooldownsManager = new CooldownsManager();
		CooldownBuilder.setCooldownsManager(this.cooldownsManager);
	}
	private void registerListeners() 
	{

	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.lobbiesManager, this.gamesManager);
		this.amongUs.getCommand("amongus").setExecutor(amongUsCommand);
		this.amongUs.getCommand("amongus").setTabCompleter(amongUsCommand);
	}
	private void sendHelpersToAfkPool()
	{
		String[] helpersNames = {"PhoenixBust", "MrGeneralQ"};
		Location afkPoolLocation = new Location(Bukkit.getWorld("world"), 39, 64, 2);

		Arrays.stream(helpersNames)
		.map(Bukkit::getPlayer)
		.filter(Objects::nonNull)
		.forEach(helper -> helper.teleport(afkPoolLocation));
	}
}