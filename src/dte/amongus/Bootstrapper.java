package dte.amongus;

import dte.amongus.commands.AmongUSCommand;
import dte.amongus.cooldown.Cooldown.CooldownBuilder;
import dte.amongus.cooldown.CooldownService;
import dte.amongus.corpses.basic.components.holograms.HologramComponent;
import dte.amongus.games.AUGameService;
import dte.amongus.holograms.equallable.SimpleEquallableHologram;
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

class Bootstrapper
{
	private AUPlayerService auPlayerService;
	private AULobbyService lobbyService;
	private AUGameService gameService;
	private ShipTaskService shipTaskService;

	private HolographicDisplaysHook hdHook;

	private static final AmongUs AMONG_US = AmongUs.getInstance();

	public void bootstrap()
	{
		AMONG_US.saveDefaultConfig();

		setupHooks();
		setupManagers();
		registerCommands();
		registerListeners();
	}
	private void setupManagers()
	{
		this.auPlayerService = new AUPlayerService();
		this.lobbyService = new AULobbyService();
		this.gameService = new AUGameService();
		this.shipTaskService = new ShipTaskService();

		GamePlayerUtils.setup(this.auPlayerService);
		CooldownBuilder.setCooldownService(new CooldownService());
		SimpleEquallableHologram.setHolographicsDisplaysHook(this.hdHook);
		HologramComponent.setHolographicDisplaysHook(this.hdHook);
	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.gameService, this.auPlayerService, this.lobbyService, this.shipTaskService);
		AMONG_US.getCommand("amongus").setExecutor(amongUsCommand);
		AMONG_US.getCommand("amongus").setTabCompleter(amongUsCommand);
	}
	private void registerListeners()
	{
		AMONG_US.registerListeners(

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
}