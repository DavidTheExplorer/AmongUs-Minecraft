package mazgani.amongus.commands;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Door;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import mazgani.amongus.AmongUs;
import mazgani.amongus.corpses.types.WoolCorpse;
import mazgani.amongus.corpses.types.WoolsCompositeCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.maps.TestMap;
import mazgani.amongus.players.PlayerColor;
import mazgani.amongus.players.Role;
import mazgani.amongus.shiptasks.ShipTask;
import mazgani.amongus.shiptasks.inventorytasks.wires.WiresTask;

public class AmongUSCommand implements CommandExecutor, TabCompleter
{
	private final LobbiesManager lobbiesManager;
	private final GamesManager gamesManager;

	private GameLobby tempLobby;

	public AmongUSCommand(LobbiesManager lobbiesManager, GamesManager gamesManager) 
	{
		this.lobbiesManager = lobbiesManager;
		this.gamesManager = gamesManager;
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
				/*BlockChangeComponent component = new BlockChangeComponent(null, location.getBlock(), Material.OBSIDIAN);
				component.spawn();

				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), component::despawn, 20 * 3);*/

				/*Block block = player.getTargetBlock(null, 10);

				if(!(block.getState().getBlockData() instanceof Openable)) 
				{
					player.sendMessage(ChatColor.RED + "You must look on an openable block.");
					return true;
				}
				changeDoor(block, Material.IRON_DOOR);*/
				
				GamePlayer gamePlayer = new GamePlayer(player, PlayerColor.RED, null);
				
				WoolsCompositeCorpse corpse = new WoolsCompositeCorpse(gamePlayer, null);
				
				corpse.addCorpse(new WoolCorpse(Material.RED_WOOL, gamePlayer, null));
				corpse.addCorpse(new WoolCorpse(Material.WHITE_WOOL, gamePlayer, null));
				
				corpse.spawn(player.getLocation());
				
				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), corpse::despawn, 20 * 5);

				/*SignCorpse corpse = new SignCorpse(gamePlayer, null, "Mizrahi", "raped a ", "goat here.");
				corpse.spawn(player.getLocation());
				
				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), corpse::despawn, 20 * 5);*/

				/*Openable openable = (Openable) block.getState().getBlockData();
				openable.setOpen(!openable.isOpen());

				state.setBlockData(openable);
				state.update();*/

				/*Sabotage sabotage = new DoorsSabotage(block);
				sabotage.sabotage();

				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), sabotage::unsabotage, 20 * 5);*/

				/*Hologram h1 = new CommonEquallableHologram(HologramsAPI.createHologram(AmongUs.getInstance(), player.getLocation().add(0, 3, 0)));
				Hologram h2 = new CommonEquallableHologram(HologramsAPI.createHologram(AmongUs.getInstance(), player.getLocation().add(0, 6, 0)));

				h1.appendTextLine("Hey!");
				h2.appendTextLine("Hey2");

				player.sendMessage(h1.equals(h2) + " equals.");*/

				/*if(requestedBeingInAGame(player)) 
				{
					return false;
				}
				AUGame playerGame = this.gamesManager.getPlayerGame(player.getUniqueId()).get();*/

				/*Location[] spawns = new TestMap().getSpawnLocations();

				for(Location spawn : spawns) 
				{
					player.sendMessage(spawn.getBlock().getType().name());
				}*/



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
				if(requestedAbsentTempLobby(player)) 
				{
					return false;
				}
				Block block = player.getTargetBlock(null, 10);

				if(!(block.getState() instanceof Sign)) 
				{
					player.sendMessage(ChatColor.RED + "You must be looking at the Join Sign.");
					return false;
				}
				Sign sign = (Sign) block.getState();
				
				this.tempLobby = this.lobbiesManager.new GameLobbyBuilder(player.getLocation(), new TestMap(), 1, 1)
						.withJoinSign(sign)
						.build();
				
				player.sendMessage(ChatColor.GREEN + "A lobby has been created in your location.");

				this.tempLobby.addPlayer(player);
				player.sendMessage(ChatColor.GREEN + "You've been sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));
				return true;
			}
			else if(args[0].equalsIgnoreCase("startLoneGame")) 
			{
				if(requestedAbsentTempLobby(sender)) 
				{
					return false;
				}
				player.performCommand("amongus createlobby");
				AUGame game = this.gamesManager.createGame(this.tempLobby, new TestMap());
				printGameData(game);
				openWireTaskInventory(game);
			}
			break;
		case 2:
			if(args[0].equalsIgnoreCase("add"))
			{
				if(requestedTempLobby(sender))
				{
					return false;
				}
				Player target = Bukkit.getPlayer(args[1]);

				if(target == null)
				{
					player.sendMessage(ChatColor.RED + args[1] + " is not online!");
					return false;
				}
				this.tempLobby.addPlayer(target);
				target.teleport(this.tempLobby.getSpawnLocation());

				player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " was sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));
				target.sendMessage(ChatColor.GREEN + "You were sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));

				AUGame game = this.tempLobby.getCurrentGame();

				if(game == null)
				{
					return false; //a game didn't start because there aren't enough players
				}
				printGameData(game);
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
				return Bukkit.getOnlinePlayers().stream()
						.map(Player::getName)
						.filter(playerName -> !playerName.equals(sender.getName()))
						.collect(toList());
			}
		}
		return null;
	}
	public static void changeDoor(Block bottomDoor, Material newType) 
	{
		verifyDoor(bottomDoor, "The specified material %s is not a door type!");
		Block topDoor = bottomDoor.getLocation().add(0, 1, 0).getBlock();
		verifyDoor(topDoor, "The block above the specified block is not a door type!");

		boolean isOpen = ((Door) bottomDoor.getState().getBlockData()).isOpen();

		setDoorProperties(bottomDoor, newType, isOpen, Half.BOTTOM);
		setDoorProperties(topDoor, newType, isOpen, Half.TOP);
	}
	private static void verifyDoor(Block block, String message) 
	{
		if(!isDoor(block))
		{
			String typeName = block.getType().name();

			throw new IllegalArgumentException(String.format(message, typeName));
		}
	}
	private static boolean isDoor(Block block) 
	{
		return block.getType().name().endsWith("DOOR");
	}
	private static void setDoorProperties(Block block, Material newMaterial, boolean opened, Half newHalf) 
	{
		block.setType(newMaterial, false);

		Door door = (Door) block.getState().getBlockData();
		door.setHalf(newHalf);
		door.setOpen(opened);

		block.getState().setBlockData(door);
	}
	private boolean requestedBeingInAGame(Player player) 
	{
		UUID playerUUID = player.getUniqueId();

		if(this.gamesManager.getPlayerGame(playerUUID) == null) 
		{
			player.sendMessage(ChatColor.RED + "You must be in a game to do this.");
			return true;
		}
		return false;
	}
	private boolean requestedTempLobby(CommandSender sender) 
	{
		if(this.tempLobby == null) 
		{
			sender.sendMessage(ChatColor.RED + "The temp lobby was not created! Use /amongus createlobby");
			return true;
		}
		return false;
	}
	private boolean requestedAbsentTempLobby(CommandSender sender) 
	{
		if(this.tempLobby != null)
		{
			sender.sendMessage(ChatColor.RED + "The temporary lobby already exists!");
			return true;
		}
		return false;
	}
	private static void printGameData(AUGame game) 
	{
		Bukkit.broadcastMessage("Impostors Left: " + game.playersLeft(Role.IMPOSTOR));
		Bukkit.broadcastMessage("Crewmates Left: " + game.playersLeft(Role.CREWMATE));
		Bukkit.broadcastMessage("Tie? " + game.isTie());
		Bukkit.broadcastMessage("Tasks Names: " + game.getTasks().stream()
				.map(ShipTask::getName)
				.collect(joining(", ")));

		for(GamePlayer gamePlayer : game.getGamePlayersView()) 
		{
			String playerName = gamePlayer.getPlayer().getName();
			String roleName = gamePlayer.getRole().getName();

			Bukkit.broadcastMessage(String.format("%s's Role is %s", playerName, roleName));
		}
	}
	private static String shortenGameID(String stringUUID) 
	{
		return ChatColor.YELLOW + "#" + stringUUID.substring(0, 8);
	}
	private static void openWireTaskInventory(AUGame game)
	{
		List<WiresTask> wiresTasks = game.getTasks().stream()
				.filter(task -> task instanceof WiresTask)
				.map(task -> (WiresTask) task)
				.collect(toList());

		WiresTask wiresTask = wiresTasks.get(0);

		for(GamePlayer gamePlayer : game.getGamePlayersView()) 
		{
			Player player = gamePlayer.getPlayer();
			
			player.openInventory(wiresTask.getInventoryManager().createInventory(gamePlayer));
		}
	}
}