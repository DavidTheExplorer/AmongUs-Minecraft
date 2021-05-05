package dte.amongus.shiptasks.enterid;

import static dte.amongus.utils.ChatColorUtils.bold;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.AmongUs;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class EnterIDInventoryManager extends TaskInventoryManager<EnterIDTask>
{
	private final Sound digitEnterSound;

	private static final int 
	ENTER_INDEX = 38,
	PAPER_INDEX = 42;

	public EnterIDInventoryManager(EnterIDTask task, Sound digitEnterSound)
	{
		super(task);

		this.digitEnterSound = digitEnterSound;
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener)
	{
		Inventory inventory = new InventoryBuilder(6, "Your ID is " + this.task.getPersonalID(opener).get()).build();

		//add the digits
		for(int digit = 1; digit <= 7; digit++) 
		{
			int i = digit + 9;
			inventory.setItem(i, createDigitItem(digit));
		}
		inventory.setItem(4, createDigitItem(0));
		inventory.setItem(21, createDigitItem(8));
		inventory.setItem(23, createDigitItem(9));

		//add the enter button
		inventory.setItem(37, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));
		inventory.setItem(ENTER_INDEX, new ItemBuilder(Material.GREEN_TERRACOTTA, bold(ChatColor.GREEN) + "Enter ID").createCopy());
		inventory.setItem(39, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));

		//add the current id paper
		inventory.setItem(41, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));
		inventory.setItem(PAPER_INDEX, createPaperItem("Nothing"));
		inventory.setItem(43, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));

		//decorate the remaining slots as walls
		InventoryUtils.fillEmptySlots(inventory, InventoryBuilder.buildWall(Material.BLACK_STAINED_GLASS_PANE));

		return inventory;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked();
		AUGamePlayer gamePlayer = this.task.getGame().getPlayer(player);
		ItemStack item = event.getCurrentItem();

		switch(item.getType()) 
		{
		case LIME_STAINED_GLASS_PANE:
			
			//glow the digit for 8 ticks
			GlowEffect.addGlow(item);
			Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () -> GlowEffect.deleteGlow(item), 8);
			
			try 
			{
				this.task.enterDigit(gamePlayer, getDigit(item));
			}
			catch(ArithmeticException exception) 
			{
				player.sendMessage(ChatColor.RED + "Too Many Digits!");
				return;
			}
			
			//update the new digit
			event.getInventory().setItem(PAPER_INDEX, createPaperItem(this.task.getEnteredID(gamePlayer).get()));
			
			player.playSound(player.getLocation(), this.digitEnterSound, 1, 1);
			break;
			
		case GREEN_TERRACOTTA:
			Integer enteredID = this.task.getEnteredID(gamePlayer).orElse(null);

			if(enteredID == null)
			{
				player.sendMessage(ChatColor.RED + "What's your Personal ID?");
				return;
			}
			if(!this.task.getPersonalID(gamePlayer).get().equals(enteredID))
			{
				player.sendMessage(ChatColor.RED + "Invalid Personal ID!");
				return;
			}
			player.sendMessage(ChatColor.GREEN + "Success - You were successfully identified.");
			break;
		}
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event)
	{
		return testInventory(event, "Your ID is ");
	}

	private static ItemStack createDigitItem(int digit)
	{
		int amount = digit == 0 ? 1 : digit;

		return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + String.valueOf(digit))
				.amountOf(amount)
				.createCopy();
	}
	
	private static ItemStack createPaperItem(int id) 
	{
		return createPaperItem(String.valueOf(id));
	}

	private static ItemStack createPaperItem(String id)
	{
		return new ItemBuilder(Material.PAPER, ChatColor.AQUA + "Entered ID: " + id).createCopy();
	}

	private static int getDigit(ItemStack digitItem)
	{
		String digitText = ChatColor.stripColor(digitItem.getItemMeta().getDisplayName());

		return Integer.valueOf(digitText);
	}
}