package dte.amongus.shiptasks.list.enterid;

import static dte.amongus.utils.ChatColorUtils.bold;
import static dte.amongus.utils.InventoryUtils.createDummyItem;

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

public class EnterIDInventoryManager extends TaskInventoryManager
{
	private final EnterIDTask enterIDTask;
	private final Sound digitEnterSound;

	private static final int 
	ENTER_INDEX = 15,
	PAPER_INDEX = 42;
	
	private static final int[] DIGITS_INDEXES = {38, 10, 11, 12, 19, 20, 21, 28, 29, 30};

	public EnterIDInventoryManager(EnterIDTask enterIDTask, Sound digitEnterSound)
	{
		this.enterIDTask = enterIDTask;
		this.digitEnterSound = digitEnterSound;
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener)
	{
		Inventory inventory = Bukkit.createInventory(null, 6 * 9, createTitle(String.format("Your ID is %d", this.enterIDTask.getPersonalID(opener).get())));
		
		//add the digits
		inventory.setItem(DIGITS_INDEXES[0], createDigitItem(0)); //the zero digit comes first
		
		for(int i = 1; i < DIGITS_INDEXES.length; i++)
			inventory.setItem(DIGITS_INDEXES[i], createDigitItem(i));
		
		//add the enter button
		inventory.setItem(ENTER_INDEX, new ItemBuilder(Material.GREEN_TERRACOTTA, bold(ChatColor.GREEN) + "Enter ID").createCopy());

		//add the current id paper
		inventory.setItem(PAPER_INDEX, createPaperItem("Nothing"));

		//decorate the remaining slots as walls
		InventoryUtils.fillEmptySlots(inventory, createDummyItem(Material.BLACK_STAINED_GLASS_PANE));
		
		return inventory;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		Player player = (Player) event.getWhoClicked();
		AUGamePlayer gamePlayer = this.enterIDTask.getGame().getPlayer(player);
		ItemStack item = event.getCurrentItem();

		switch(item.getType()) 
		{
		case LIME_STAINED_GLASS_PANE:
			
			//glow the digit for 7 ticks
			GlowEffect.addGlow(item);
			Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () -> GlowEffect.deleteGlow(item), 7);
			
			try 
			{
				this.enterIDTask.enterDigit(gamePlayer, getDigit(item));
			}
			catch(ArithmeticException exception) 
			{
				player.sendMessage(ChatColor.RED + "Too Many Digits!");
				return;
			}
			
			//update the new digit
			event.getInventory().setItem(PAPER_INDEX, createPaperItem(this.enterIDTask.getEnteredID(gamePlayer).get()));
			player.playSound(player.getLocation(), this.digitEnterSound, 1, 1);
			break;
		case GREEN_TERRACOTTA:
			Integer enteredID = this.enterIDTask.getEnteredID(gamePlayer).orElse(null);

			if(enteredID == null)
			{
				player.sendMessage(ChatColor.RED + "What's your Personal ID?");
				return;
			}
			if(!this.enterIDTask.getPersonalID(gamePlayer).get().equals(enteredID))
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