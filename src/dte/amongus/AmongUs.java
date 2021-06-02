package dte.amongus;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import dte.amongus.commands.AmongUSCommand;
import dte.amongus.cooldown.Cooldown;
import dte.amongus.cooldown.CooldownService;
import dte.amongus.corpses.basic.components.holograms.HologramComponent;
import dte.amongus.games.AUGameService;
import dte.amongus.holograms.EquallableHologram;
import dte.amongus.hooks.HolographicDisplaysHook;
import dte.amongus.internal.GamePlayerUtils;
import dte.amongus.listeners.games.BodyReportListener;
import dte.amongus.listeners.games.GameStartListener;
import dte.amongus.listeners.games.GameWinListener;
import dte.amongus.listeners.games.ImpostorKillListener;
import dte.amongus.listeners.general.AUPlayerRegistrationListeners;
import dte.amongus.listeners.lobbies.LobbyFullListener;
import dte.amongus.listeners.lobbies.LobbyLeaveListeners;
import dte.amongus.listeners.retrievers.ImpostorKillRetrieverListener;
import dte.amongus.listeners.tasks.InventoryTasksListener;
import dte.amongus.lobby.AULobbyService;
import dte.amongus.player.AUPlayerService;
import dte.amongus.shiptasks.service.ShipTaskService;
import dte.amongus.utils.ModernJavaPlugin;

public class AmongUs extends ModernJavaPlugin
{
	//services
	private AUPlayerService auPlayerService;
	private AULobbyService lobbyService;
	private AUGameService gameService;
	private ShipTaskService shipTaskService;
	
	//hooks
	private HolographicDisplaysHook hdHook;
	
	private static AmongUs INSTANCE;

	@Override
	public void onEnable() 
	{
		INSTANCE = this;
		
		saveDefaultConfig();
		setupHooks();
		setupServices();
		registerCommands();
		registerListeners();

		prepareOnlineHelpers();
	}
	private void setupServices()
	{
		this.auPlayerService = new AUPlayerService();
		this.lobbyService = new AULobbyService();
		this.gameService = new AUGameService();
		this.shipTaskService = new ShipTaskService();

		GamePlayerUtils.setup(this.auPlayerService);
		Cooldown.Builder.setCooldownService(new CooldownService());
		EquallableHologram.setHolographicsDisplaysHook(this.hdHook);
		HologramComponent.setHolographicDisplaysHook(this.hdHook);
	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.gameService, this.auPlayerService, this.lobbyService, this.shipTaskService);
		getCommand("amongus").setExecutor(amongUsCommand);
		getCommand("amongus").setTabCompleter(amongUsCommand);
	}
	private void registerListeners()
	{
		registerListeners(

				//General
				new AUPlayerRegistrationListeners(this.auPlayerService),

				//Retrieving
				new ImpostorKillRetrieverListener(this.gameService),

				//Lobby
				new LobbyLeaveListeners(this.lobbyService),

				//Game
				new GameStartListener(),
				new LobbyFullListener(this.gameService),
				new ImpostorKillListener(), 
				new GameWinListener(),
				new BodyReportListener(),
				new InventoryTasksListener(this.shipTaskService, this.gameService)
				);
	}
	private void setupHooks() 
	{
		this.hdHook = new HolographicDisplaysHook();
	}
	private void prepareOnlineHelpers()
	{
		Location poolLocation = new Location(Bukkit.getWorld("world"), 39, 64, 2);
		
		getConfig().getStringList("Helpers Names").stream()
		.map(Bukkit::getPlayer)
		.filter(Objects::nonNull)
		.forEach(helper -> helper.teleport(poolLocation));
	}
	
	public static AmongUs getInstance()
	{
		return INSTANCE;
	}
}