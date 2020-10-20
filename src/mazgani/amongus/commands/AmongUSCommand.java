package mazgani.amongus.commands;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mazgani.amongus.enums.Role;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.lobbies.GameLobby;
import mazgani.amongus.lobbies.LobbiesManager;
import mazgani.amongus.shiptasks.Task;

public class AmongUSCommand implements CommandExecutor
{
	private final LobbiesManager lobbiesManager;
	
	private GameLobby tempLobby;
	
	public AmongUSCommand(LobbiesManager lobbiesManager) 
	{
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

		switch(args.length) 
		{
		case 1:
			if(args[0].equalsIgnoreCase("createlobby")) 
			{
				this.tempLobby = this.lobbiesManager.createLobby(player.getLocation(), 1, 1);
				player.sendMessage(ChatColor.GREEN + "A lobby has been created in your location.");

				this.tempLobby.addPlayer(player.getUniqueId());
				player.sendMessage(ChatColor.YELLOW + "You've been sent to Lobby " + ChatColor.GREEN + "#" + this.tempLobby.getUUID().toString());
			}
			break;
		case 2:
			if(args[0].equalsIgnoreCase("add")) 
			{
				if(this.tempLobby == null) 
				{
					player.sendMessage(ChatColor.RED + "The temp lobby was not created! Use /amongus createlobby");
					return false;
				}
				Player target = Bukkit.getPlayer(args[1]);
				
				if(target == null) 
				{
					player.sendMessage(ChatColor.RED + args[1] + " is not online!");
					return false;
				}
				this.tempLobby.addPlayer(target.getUniqueId());
				target.teleport(this.tempLobby.getSpawnLocation());
				
				player.sendMessage(ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " has been sent to Lobby #" + this.tempLobby.getUUID().toString());
				target.sendMessage(ChatColor.GREEN + "You have been sent to Lobby " + ChatColor.YELLOW + "#" + this.tempLobby.getUUID().toString());
				
				AUGame game = this.tempLobby.startGame();
				
				Bukkit.broadcastMessage("Impostors Left: " + game.playersLeft(Role.IMPOSTOR));
				Bukkit.broadcastMessage("Crewmates Left: " + game.playersLeft(Role.CREWMATE));
				Bukkit.broadcastMessage("Tie? " + game.isTie());
				Bukkit.broadcastMessage("Tasks Names: " + game.getTasks().stream().map(Task::getName).collect(Collectors.joining(", ")));
				Bukkit.broadcastMessage(player.getName() + "'s Role is " + game.getPlayer(player.getUniqueId()).getRole());
				Bukkit.broadcastMessage(target.getName() + "'s Role is " + game.getPlayer(target.getUniqueId()).getRole());
			}
		}
		return true;
	}
}
