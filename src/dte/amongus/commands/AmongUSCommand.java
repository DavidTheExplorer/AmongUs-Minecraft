package dte.amongus.commands;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dte.amongus.AmongUs;
import dte.amongus.corpses.factory.SimpleCorpseFactory;
import dte.amongus.games.AUGame;
import dte.amongus.games.GamesManager;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.lobby.AULobby;
import dte.amongus.lobby.LobbiesManager;
import dte.amongus.lobby.LobbiesManager.LobbyBuilder;
import dte.amongus.maps.TestMap;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.AUPlayersManager;
import dte.amongus.player.visual.PlayerColor;
import dte.amongus.sabotages.GatesSabotage;
import dte.amongus.sabotages.Sabotage;
import dte.amongus.shiptasks.list.wires.WiresTask;
import dte.amongus.utils.java.IterableUtils;

public class AmongUSCommand implements CommandExecutor, TabCompleter
{
	private final GamesManager gamesManager;
	private final LobbiesManager lobbiesManager;
	private final AUPlayersManager auPlayersManager;

	private AULobby tempLobby;

	public AmongUSCommand(GamesManager gamesManager, AUPlayersManager auPlayersManager, LobbiesManager lobbiesManager) 
	{
		this.gamesManager = gamesManager;
		this.auPlayersManager = auPlayersManager;
		this.lobbiesManager = lobbiesManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
			return false;
		}
		Player player = (Player) sender;

		if(!player.isOp())
		{
			player.sendMessage(ChatColor.RED + "You don't have enough permissions to execute this command.");
			return false;
		}

		switch(args.length)
		{
		case 1:
			if(args[0].equalsIgnoreCase("test"))
			{
				Sabotage sabotage = GatesSabotage.from(player.getLocation().getBlock());

				sabotage.activate();
				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), sabotage::disable, 20 * 3);

				/*Hologram h1 = new CommonEquallableHologram(HologramsAPI.createHologram(AmongUs.getInstance(), player.getLocation().add(0, 3, 0)));
				Hologram h2 = new CommonEquallableHologram(HologramsAPI.createHologram(AmongUs.getInstance(), player.getLocation().add(0, 6, 0)));

				h1.appendTextLine("Hey!");
				h2.appendTextLine("Hey2");

				player.sendMessage(h1.equals(h2) + " equals.");*/

				/*if(requestedBeingInAGame(player)) 
					return false;

				AUGame playerGame = this.gamesManager.getPlayerGame(player.getUniqueId()).get();*/

				/*Location[] spawns = new TestMap().getSpawnLocations();

				for(Location spawn : spawns) 
					player.sendMessage(spawn.getBlock().getType().name());
				 */

				/*Queue<Location> spawnsQueue = Arrays.stream(spawns).collect(toCollection(LinkedList::new));

				new BukkitRunnable()
				{
					@Override
					public void run() 
					{
						if(spawnsQueue.isEmpty()) 
						{
							player.sendMessage(ChatColor.GREEN + "All locations were checked.");
							cancel();
							return;
						}
						Location nextSpawn = spawnsQueue.poll();
						player.teleport(nextSpawn);

						if(nextSpawn.getBlock() == null || !nextSpawn.getBlock().getType().name().endsWith("WOOL")) 
						{
							cancel();
							player.sendMessage(ChatColor.RED + "What are you standing on? It's not a wool.");
							return;
						}
					}
				}.runTaskTimer(AmongUs.getInstance(), 0, 20 * 2);*/

				return true;
			}
			else if(args[0].equalsIgnoreCase("createlobby")) 
			{
				if(requestedTempLobby(player, true)) 
					return false;

				Block block = player.getTargetBlock(null, 10);

				if(!(block.getState() instanceof Sign)) 
				{
					player.sendMessage(ChatColor.RED + "You must be looking at the Join Sign.");
					return false;
				}
				Sign sign = (Sign) block.getState();

				this.tempLobby = new LobbyBuilder(player.getLocation(), new TestMap(), new SimpleCorpseFactory(), 1, 1)
						.joinableBy(sign)
						.build(this.lobbiesManager);

				player.sendMessage(ChatColor.GREEN + "A lobby has been created in your location.");

				AUPlayer auPlayer = this.auPlayersManager.getAUPlayer(player.getUniqueId());
				this.tempLobby.addPlayer(auPlayer);
				player.sendMessage(ChatColor.GREEN + "You've been sent to Lobby " + shortenGameID(this.tempLobby.getID().toString()));
				return true;
			}
			else if(args[0].equalsIgnoreCase("startLoneGame")) 
			{
				if(requestedTempLobby(sender, false)) 
					return false;

				player.performCommand("amongus createlobby");

				AUGame game = this.gamesManager.registerNewGame(this.tempLobby, new TestMap());
				Bukkit.broadcastMessage(game.toString());
				openWireTaskInventory(game);
			}
			break;
		case 2:
			if(args[0].equalsIgnoreCase("add"))
			{
				if(requestedTempLobby(sender, true))
					return false;

				Player target = Bukkit.getPlayer(args[1]);

				if(target == null)
				{
					player.sendMessage(ChatColor.RED + args[1] + " is not online!");
					return false;
				}
				AUPlayer targetAUPlayer = this.auPlayersManager.getAUPlayer(target.getUniqueId());
				this.tempLobby.addPlayer(targetAUPlayer);
				target.teleport(this.tempLobby.getSpawnLocation());

				player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " was sent to Lobby " + shortenGameID(this.tempLobby.getID().toString()));
				target.sendMessage(ChatColor.GREEN + "You were sent to Lobby " + shortenGameID(this.tempLobby.getID().toString()));

				AUGame game = this.tempLobby.getCurrentGame();

				if(game == null)
					return false; //a game didn't start because there aren't enough players

				Bukkit.broadcastMessage(game.toString());
				openWireTaskInventory(game);
			}
			break;
		}
		sender.sendMessage(ChatColor.RED + "/amongus test");
		sender.sendMessage(ChatColor.RED + "/amongus createlobby");
		sender.sendMessage(ChatColor.RED + "/amongus startLoneLobby");
		sender.sendMessage(ChatColor.RED + "/amongus add [target]");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) 
	{
		switch(args.length)
		{
		case 1:
			return Arrays.asList("test", "createlobby", "add", "startLoneGame");
		case 2:
			if(args[0].equalsIgnoreCase("add"))
			{
				if(this.tempLobby == null) 
					return new ArrayList<>();

				List<String> onlinePlayersNames = Bukkit.getOnlinePlayers().stream()
						.map(Player::getName)
						.collect(toCollection(LinkedList::new));

				//if the sender is a player - they must've been in the game - so their name is excluded
				if(sender instanceof Player) 
					onlinePlayersNames.remove(((Player) sender).getName());

				return onlinePlayersNames;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private boolean requestedBeingInAGame(Player player) 
	{
		if(this.gamesManager.getPlayerGame(player) == null) 
		{
			player.sendMessage(ChatColor.RED + "You must be in a game to do this.");
			return true;
		}
		return false;
	}
	private boolean requestedTempLobby(CommandSender sender, boolean toExist)
	{
		if(toExist && this.tempLobby == null)
		{
			sender.sendMessage(ChatColor.RED + "The temp lobby was not created! Use /amongus createlobby");
			return true;
		}
		if(!toExist && this.tempLobby != null) 
		{
			sender.sendMessage(ChatColor.RED + "The temp lobby already exists!");
			return true;
		}
		return false;
	}
	private static String shortenGameID(String stringUUID) 
	{
		return ChatColor.YELLOW + "#" + stringUUID.substring(0, 8);
	}
	private static void openWireTaskInventory(AUGame game)
	{
		List<WiresTask> wiresTasks = IterableUtils.getElementsOf(WiresTask.class, game.getTasks());

		if(wiresTasks.isEmpty()) 
		{
			Bukkit.broadcastMessage(ChatColor.RED + "The game doesn't have a Wires Task!");
			return;
		}
		WiresTask wiresTask = wiresTasks.get(0);

		for(AUGamePlayer gamePlayer : game.getAlivePlayers()) 
		{
			Inventory wiresInv = wiresTask.getInventoryManager().createInventory(gamePlayer);

			gamePlayer.getPlayer().openInventory(wiresInv);
		}
	}

	@SuppressWarnings("unused")
	private AUGamePlayer createGamePlayer(Player player, PlayerColor color) 
	{
		this.auPlayersManager.register(player.getUniqueId());

		AUPlayer auPlayer = this.auPlayersManager.getAUPlayer(player.getUniqueId());
		auPlayer.getVisibilityManager().setCurrentColor(PlayerColor.GREEN);

		if(ThreadLocalRandom.current().nextBoolean())
			return new Crewmate(player, null);
		else
			return new Impostor(player, null);
	}
}