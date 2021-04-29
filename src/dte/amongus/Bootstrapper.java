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
import dte.amongus.listeners.games.GameWinListener;
import dte.amongus.listeners.games.ImpostorKillListener;
import dte.amongus.listeners.games.StartGameListener;
import dte.amongus.listeners.lobbies.LobbyLeaveListeners;
import dte.amongus.listeners.retrievers.ImpostorKillRetrieverListener;
import dte.amongus.lobby.LobbiesManager;
import dte.amongus.player.AUPlayersManager;

class Bootstrapper
{
	private AUPlayersManager auPlayersManager;
	private LobbiesManager lobbiesManager;
	private GamesManager gamesManager;
	
	private HolographicDisplaysHook hdHook;
	
	public void bootstrap()
	{
		AmongUs.getInstance().saveDefaultConfig();
		
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
		
		GamePlayerUtils.setup(this.auPlayersManager);
		CooldownBuilder.setCooldownsManager(new CooldownsManager());
		SimpleEquallableHologram.setHolographicsDisplaysHook(this.hdHook);
		HologramComponent.setHolographicDisplaysHook(this.hdHook);
	}
	private void registerCommands() 
	{
		AmongUSCommand amongUsCommand = new AmongUSCommand(this.gamesManager, this.auPlayersManager, this.lobbiesManager);
		AmongUs.getInstance().getCommand("amongus").setExecutor(amongUsCommand);
		AmongUs.getInstance().getCommand("amongus").setTabCompleter(amongUsCommand);
	}
	private void registerListeners()
	{
		AmongUs.getInstance().registerListeners(
				
				//Retrieving
				new ImpostorKillRetrieverListener(this.gamesManager),

				//Lobby
				new LobbyLeaveListeners(this.lobbiesManager),

				//Game
				new StartGameListener(this.gamesManager),
				new ImpostorKillListener(), 
				new GameWinListener(),
				new BodyReportListener()
				);
	}
	private void setupHooks() 
	{
		this.hdHook = new HolographicDisplaysHook();
	}
}