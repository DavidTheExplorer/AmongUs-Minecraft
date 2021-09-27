package dte.amongus;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import dte.amongus.commands.AmongUSCommand;
import dte.amongus.corpses.compound.components.holograms.HologramComponent;
import dte.amongus.games.service.AUGameService;
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
import dte.amongus.lobby.AULobby;
import dte.amongus.lobby.service.AULobbyService;
import dte.amongus.player.service.AUPlayerService;
import dte.amongus.utils.ModernJavaPlugin;

public class AmongUs extends ModernJavaPlugin
{
	//Services
	private AUPlayerService auPlayerService;
	private AULobbyService lobbyService;
	private AUGameService gameService;
	
	//Hooks
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
	
	public static AmongUs getInstance()
	{
		return INSTANCE;
	}
	
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.gameService, this.auPlayerService);
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
				new LobbyLeaveListeners(this.lobbyService, this.auPlayerService),

				//Game
				new GameStartListener(),
				new LobbyFullListener(this.gameService),
				new ImpostorKillListener(), 
				new GameWinListener(),
				new BodyReportListener(),
				new InventoryTasksListener(this.gameService)
				);
	}
	
	private void setupServices()
	{
		this.auPlayerService = new AUPlayerService();
		this.lobbyService = new AULobbyService();
		this.gameService = new AUGameService();

		GamePlayerUtils.setup(this.auPlayerService);
		AULobby.Builder.setLobbyService(this.lobbyService);
	}
	
	private void setupHooks() 
	{
		this.hdHook = new HolographicDisplaysHook();
		
		EquallableHologram.setHolographicsDisplaysHook(this.hdHook);
		HologramComponent.setHolographicDisplaysHook(this.hdHook);
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