package dte.amongus;

import dte.amongus.commands.AmongUSCommand;
import dte.amongus.cooldown.Cooldown.CooldownBuilder;
import dte.amongus.cooldown.CooldownsManager;
import dte.amongus.corpses.basic.components.holograms.HologramComponent;
import dte.amongus.games.GamesManager;
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
import dte.amongus.lobby.LobbiesManager;
import dte.amongus.player.AUPlayersManager;
import dte.amongus.shiptasks.ShipTaskService;

class Bootstrapper
{
	private AUPlayersManager auPlayersManager;
	private LobbiesManager lobbiesManager;
	private GamesManager gamesManager;
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
		this.auPlayersManager = new AUPlayersManager();
		this.lobbiesManager = new LobbiesManager();
		this.gamesManager = new GamesManager();
		this.shipTaskService = new ShipTaskService();
		
		GamePlayerUtils.setup(this.auPlayersManager);
		CooldownBuilder.setCooldownsManager(new CooldownsManager());
		SimpleEquallableHologram.setHolographicsDisplaysHook(this.hdHook);
		HologramComponent.setHolographicDisplaysHook(this.hdHook);
	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.gamesManager, this.auPlayersManager, this.lobbiesManager, this.shipTaskService);
		AMONG_US.getCommand("amongus").setExecutor(amongUsCommand);
		AMONG_US.getCommand("amongus").setTabCompleter(amongUsCommand);
	}
	private void registerListeners()
	{
		AMONG_US.registerListeners(
				
				//General
				new AUPlayerRegistrationListeners(this.auPlayersManager),
				
				//Retrieving
				new ImpostorKillRetrieverListener(this.gamesManager),

				//Lobby
				new LobbyLeaveListeners(this.lobbiesManager),

				//Game
				new GameStartListener(),
				new LobbyFullListener(this.gamesManager),
				new ImpostorKillListener(), 
				new GameWinListener(),
				new BodyReportListener(),
				new InventoryTasksListener(this.shipTaskService, this.gamesManager)
				);
	}
	private void setupHooks() 
	{
		this.hdHook = new HolographicDisplaysHook();
	}
}