package dte.amongus.commands;

import static dte.amongus.player.PlayerRole.CREWMATE;
import static dte.amongus.player.PlayerRole.IMPOSTOR;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.YELLOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dte.amongus.AmongUs;
import dte.amongus.corpses.factory.SimpleCorpseFactory;
import dte.amongus.games.AUGame;
import dte.amongus.games.players.Crewmate;
import dte.amongus.games.players.Impostor;
import dte.amongus.games.service.AUGameService;
import dte.amongus.lobby.AULobby;
import dte.amongus.maps.TestMap;
import dte.amongus.player.AUPlayer;
import dte.amongus.player.service.AUPlayerService;
import dte.amongus.sabotages.GatesSabotage;
import dte.amongus.sabotages.Sabotage;
import dte.amongus.shiptasks.CleanO2FilterTask;
import dte.amongus.shiptasks.EnterIDTask;
import dte.amongus.shiptasks.ShipTask;
import dte.amongus.shiptasks.StabilizeSteeringTask;
import dte.amongus.shiptasks.WiresTask;
import dte.amongus.shiptasks.inventory.InventoryTask;
import dte.amongus.utils.blocks.SignUtils;
import dte.amongus.utils.java.IterableUtils;

public class AmongUSCommand implements CommandExecutor, TabCompleter
{
	private final AUGameService gameService;
	private final AUPlayerService auPlayerService;
	
	private AULobby tempLobby;
	
	private static final Map<String, Class<? extends ShipTask>> TASK_CLASS_BY_NAME = new HashMap<>();
	
	static 
	{
		TASK_CLASS_BY_NAME.put("wires", WiresTask.class);
		TASK_CLASS_BY_NAME.put("id", EnterIDTask.class);
		TASK_CLASS_BY_NAME.put("steering", StabilizeSteeringTask.class);
		TASK_CLASS_BY_NAME.put("o2", CleanO2FilterTask.class);
	}

	public AmongUSCommand(AUGameService gameService, AUPlayerService auPlayerService) 
	{
		this.gameService = gameService;
		this.auPlayerService = auPlayerService;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(!(sender instanceof Player))
		{
			sender.sendMessage(RED + "Only players may execute this command.");
			return false;
		}
		Player player = (Player) sender;
		
		if(!player.isOp())
		{
			player.sendMessage(RED + "You don't have enough permissions to execute this command.");
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
							player.sendMessage(GREEN + "All locations were checked.");
							cancel();
							return;
						}
						Location nextSpawn = spawnsQueue.poll();
						player.teleport(nextSpawn);

						if(nextSpawn.getBlock() == null || !nextSpawn.getBlock().getType().name().endsWith("WOOL")) 
						{
							cancel();
							player.sendMessage(RED + "What are you standing on? It's not a wool.");
							return;
						}
					}
				}.runTaskTimer(AmongUs.getInstance(), 0, 20 * 2);*/
				return true;
			}
			else if(args[0].equalsIgnoreCase("createlobby")) 
			{
				if(requestedTempLobby(player, false))
					return false;

				Block block = player.getTargetBlock(null, 10);

				if(!(block.getState() instanceof Sign)) 
				{
					player.sendMessage(RED + "You must be looking at an Empty Sign.");
					return false;
				}
				Sign sign = (Sign) block.getState();
				
				if(!SignUtils.isEmpty(sign))
				{
					player.sendMessage(RED + "The Sign you are looking at must be Empty!");
					return false;
				}
				
				this.tempLobby = new AULobby.Builder()
						.withSpawnLocation(player.getLocation())
						.withGameMap(new TestMap())
						.thatGetsCorpsesFrom(new SimpleCorpseFactory())
						.thatRequires(CREWMATE, 1)
						.thatRequires(IMPOSTOR, 1)
						.joinableBy(sign)
						.build();
				
				player.sendMessage(GREEN + "You successfully created a new lobby in your Location.");
				
				this.tempLobby.addPlayer(this.auPlayerService.getAUPlayer(player.getUniqueId()));
				player.sendMessage(GREEN + "You were sent to lobby " + toDisplay(this.tempLobby));
				return true;
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
					player.sendMessage(RED + args[1] + " is not online!");
					return false;
				}
				AUPlayer targetAUPlayer = this.auPlayerService.getAUPlayer(target.getUniqueId());
				this.tempLobby.addPlayer(targetAUPlayer);

				player.sendMessage(YELLOW + target.getName() + GREEN + " was sent to Lobby " + toDisplay(this.tempLobby));
				target.sendMessage(GREEN + "You were sent to Lobby " + toDisplay(this.tempLobby));

				AUGame game = this.tempLobby.getCurrentGame();

				if(game == null)
					return false; //a game didn't start because there aren't enough players

				Bukkit.broadcastMessage(GREEN + "Crewmates: " + game.getAlivePlayers(Crewmate.class).stream()
						.map(crewmate -> crewmate.getPlayer().getName())
						.collect(joining(", ")));

				Bukkit.broadcastMessage(RED + "Impostors: " + game.getAlivePlayers(Impostor.class).stream()
						.map(impostor -> impostor.getPlayer().getName())
						.collect(joining(", ")));
				
				return true;
			}
			else if(args[0].equalsIgnoreCase("openinv")) 
			{
				if(requestedBeingInAGame(player))
					return false;
				
				Class<? extends ShipTask> taskClass = TASK_CLASS_BY_NAME.get(args[1].toLowerCase());
				
				if(taskClass == null) 
				{
					player.sendMessage(RED + "The specified task " + DARK_RED + args[1] + RED + " wasn't found!");
					return false;
				}
				if(!InventoryTask.class.isAssignableFrom(taskClass)) 
				{
					player.sendMessage(RED + "The chosen task is not an Inventory Task.");
					return false;
				}
				openTaskInventory(this.gameService.getPlayerGame(player).get(), (Class<? extends InventoryTask>) taskClass);
				return true;
			}
			break;
		}
		sender.sendMessage(RED + "/amongus test");
		sender.sendMessage(RED + "/amongus createLobby");
		sender.sendMessage(RED + "/amongus openinv [task name]");
		sender.sendMessage(RED + "/amongus add [target]");
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) 
	{
		switch(args.length)
		{
		case 1:
			return Arrays.asList("test", "createlobby", "add", "openinv");
			
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
			else if(args[0].equalsIgnoreCase("openinv")) 
			{
				return new ArrayList<>(TASK_CLASS_BY_NAME.keySet());
			}
		}
		return null;
	}

	private boolean requestedBeingInAGame(Player player) 
	{
		if(!this.gameService.getPlayerGame(player).isPresent()) 
		{
			player.sendMessage(RED + "You must be in a game to do this.");
			return true;
		}
		return false;
	}

	private boolean requestedTempLobby(CommandSender sender, boolean toExist)
	{
		if(toExist && this.tempLobby == null)
		{
			sender.sendMessage(RED + "The temp lobby was not created! Use /amongus createlobby");
			return true;
		}
		if(!toExist && this.tempLobby != null) 
		{
			sender.sendMessage(RED + "The temp lobby already exists!");
			return true;
		}
		return false;
	}

	private static String toDisplay(AULobby lobby) 
	{
		return YELLOW + "#" + lobby.getID().toString().substring(0, 8);
	}

	private <T extends InventoryTask> void openTaskInventory(AUGame game, Class<T> taskClass)
	{
		List<T> matchingTasks = IterableUtils.getElementsOf(taskClass, game.getTasks());

		if(matchingTasks.isEmpty())
		{
			game.getAlivePlayers(Crewmate.class).stream()
			.map(Crewmate::getPlayer)
			.forEach(player -> player.sendMessage(RED + "Couldn't find such a Task!"));
			return;
		}
		T task = matchingTasks.get(0);

		for(Crewmate crewmate : game.getAlivePlayers(Crewmate.class)) 
		{
			game.setCurrentTask(crewmate, task);
			task.onStart(crewmate);

			crewmate.getPlayer().openInventory(task.getInventoryManager().createInventory(crewmate));
		}
	}
}