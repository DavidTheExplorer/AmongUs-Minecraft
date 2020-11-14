package mazgani.amongus.commands;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.games.GamesManager;
import mazgani.amongus.games.maps.TestMap;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbiesManager;
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
				//testBody(player);
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

				this.tempLobby = this.lobbiesManager.createLobby(player.getLocation(), new TestMap(), 1, 1, sign);
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
	private boolean requestedBeingInAGame(Player player) 
	{
		if(this.gamesManager.getPlayerGame(player.getUniqueId()) == null) 
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
		WiresTask wiresTask = (WiresTask) game.getTasks().iterator().next();

		for(Player player : game.getPlayersView()) 
		{
			player.openInventory(wiresTask.getInventoryManager().createInventory());
		}
	}
}
