package mazgani.amongus.commands;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.maps.TestMap;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.AUPlayersManager;
import mazgani.amongus.players.GameRole;
import mazgani.amongus.shiptasks.ShipTask;
import mazgani.amongus.shiptasks.inventorytasks.wires.WiresTask;
import mazgani.amongus.utilities.items.ClickSuffix;
import mazgani.amongus.utilities.items.ItemBuilder;

public class AmongUSCommand implements CommandExecutor, TabCompleter
{
	private final LobbiesManager lobbiesManager;
	private final GamesManager gamesManager;
	private final AUPlayersManager auPlayersManager;

	private GameLobby tempLobby;

	public AmongUSCommand(LobbiesManager lobbiesManager, GamesManager gamesManager, AUPlayersManager auPlayersManager) 
	{
		this.lobbiesManager = lobbiesManager;
		this.gamesManager = gamesManager;
		this.auPlayersManager = auPlayersManager;
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
				ItemBuilder builder = new ItemBuilder(Material.DIAMOND_SWORD, ChatColor.LIGHT_PURPLE + "Hero's Sword")
						.withLore(ChatColor.WHITE + "Time to kill everyone.", ChatColor.DARK_GREEN + "Right / Left Click to have Anti Knockback.")
						.withClickableSuffix(ClickSuffix.RIGHT_FIRST, ChatColor.GRAY, ChatColor.GREEN, ChatColor.GRAY)
						.enchantedWith(Enchantment.DAMAGE_ALL, 12)
						.glowing();

				player.getInventory().addItem(builder.createCopy());

				/*Block block = player.getTargetBlock(null, 10);

				if(!(block.getState().getBlockData() instanceof Openable)) 
				{
					player.sendMessage(ChatColor.RED + "You must look on an openable block.");
					return true;
				}
				changeDoor(block, Material.IRON_DOOR);*/

				/*GamePlayer gamePlayer = new GamePlayer(new AUPlayer(player.getUniqueId()), null);

				BasicGameCorpse corpse = new TestCorpse(gamePlayer, null);
				corpse.spawn(player.getLocation());

				Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), corpse::despawn, 20 * 5);*/

				/*WoolsCompositeCorpse corpse = new WoolsCompositeCorpse(gamePlayer, null);
				corpse.spawn(player.getLocation());*/


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
				if(requestedTempLobby(player, true)) 
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
						.joinableBy(sign)
						.build();

				player.sendMessage(ChatColor.GREEN + "A lobby has been created in your location.");

				AUPlayer auPlayer = this.auPlayersManager.getPlayer(player.getUniqueId());
				this.tempLobby.addPlayer(auPlayer);
				player.sendMessage(ChatColor.GREEN + "You've been sent to Lobby " + shortenGameID(this.tempLobby.getUUID().toString()));
				return true;
			}
			else if(args[0].equalsIgnoreCase("startLoneGame")) 
			{
				if(requestedTempLobby(sender, false)) 
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
				if(requestedTempLobby(sender, true))
				{
					return false;
				}
				Player target = Bukkit.getPlayer(args[1]);

				if(target == null)
				{
					player.sendMessage(ChatColor.RED + args[1] + " is not online!");
					return false;
				}
				AUPlayer targetAUPlayer = this.auPlayersManager.getPlayer(target.getUniqueId());
				this.tempLobby.addPlayer(targetAUPlayer);
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
				if(this.tempLobby == null) 
				{
					return new ArrayList<>();
				}
				List<String> onlinePlayersNames = Bukkit.getOnlinePlayers().stream()
						.map(Player::getName)
						.collect(toCollection(LinkedList::new));

				//if the sender is a player - they must've been in the game - so their name is excluded
				if(sender instanceof Player) 
				{
					Player player = (Player) sender;
					onlinePlayersNames.remove(player.getName());
				}
				return onlinePlayersNames;
			}
		}
		return null;
	}
	public static void changeDoor(Block bottomDoor, Material newType) 
	{
		//verify the bottom block is a door
		verifyDoor(bottomDoor, "The specified material %s doesn't represent a Door!");

		//verify the top block is a door
		Block topDoor = bottomDoor.getLocation().add(0, 1, 0).getBlock();
		verifyDoor(topDoor, "The block above the specified block(%s) is not a Door!");

		boolean isOpen = ((Door) bottomDoor.getState().getBlockData()).isOpen();

		setDoorProperties(bottomDoor, newType, isOpen, Half.BOTTOM);
		setDoorProperties(topDoor, newType, isOpen, Half.TOP);
	}
	private static void verifyDoor(Block block, String message) 
	{
		if(!isDoor(block))
		{
			throw new IllegalArgumentException(String.format(message, block.getType().name()));
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
	private static void printGameData(AUGame game) 
	{
		Bukkit.broadcastMessage("Impostors Left: " + game.playersLeft(GameRole.IMPOSTOR));
		Bukkit.broadcastMessage("Crewmates Left: " + game.playersLeft(GameRole.CREWMATE));
		Bukkit.broadcastMessage("The game is " + (game.isTie() ? ChatColor.GREEN + "is" : ChatColor.RED + "isn't") + " on tie.");
		Bukkit.broadcastMessage("Tasks: " + game.getTasks().stream().map(ShipTask::getName).collect(joining(", ")));

		for(GamePlayer gamePlayer : game.getGamePlayersView()) 
		{
			String playerName = gamePlayer.getAUPlayer().getPlayer().getName();
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
		WiresTask wiresTask = game.getTasks().stream()
				.filter(task -> task instanceof WiresTask)
				.map(task -> (WiresTask) task)
				.findFirst()
				.orElseThrow(NullPointerException::new);

		for(GamePlayer gamePlayer : game.getGamePlayersView()) 
		{
			Inventory wiresInv = wiresTask.getInventoryManager().createInventory(gamePlayer);

			gamePlayer.getAUPlayer().getPlayer().openInventory(wiresInv);
		}
	}
}