package dte.amongus.shiptasks.inventory.enterid;

import static dte.amongus.utils.ChatColorUtils.bold;
import static dte.amongus.utils.InventoryUtils.createDummyItem;
import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.AmongUs;
import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.EnterIDTask;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;

public class EnterIDInventoryManager extends TaskInventoryManager
{
	private final EnterIDTask enterIDTask;
	private final Sound digitEnterSound;
	private final Sound identificationSucceedSound;

	private static final int 
	ENTER_INDEX = 15,
	PAPER_INDEX = 42;
	
	private static final int[] DIGITS_INDEXES = {38, 10, 11, 12, 19, 20, 21, 28, 29, 30};

	public EnterIDInventoryManager(EnterIDTask enterIDTask, Sound digitEnterSound, Sound identificationSucceedSound)
	{
		this.enterIDTask = enterIDTask;
		this.digitEnterSound = digitEnterSound;
		this.identificationSucceedSound = identificationSucceedSound;
	}

	@Override
	public Inventory createInventory(Crewmate opener)
	{
		Inventory inventory = Bukkit.createInventory(null, 6 * 9, createTitle(String.format("Your ID is %d", this.enterIDTask.getPersonalID(opener).get())));
		
		//add the digits
		inventory.setItem(DIGITS_INDEXES[0], createDigitItem(0)); //the zero digit comes first
		
		for(int i = 1; i < DIGITS_INDEXES.length; i++)
			inventory.setItem(DIGITS_INDEXES[i], createDigitItem(i));
		
		//add the enter button
		inventory.setItem(ENTER_INDEX, new ItemBuilder(Material.GREEN_TERRACOTTA, bold(GREEN) + "Enter ID").createCopy());

		//add the current id paper
		inventory.setItem(PAPER_INDEX, createPaperItem("Nothing"));

		//decorate the remaining slots as walls
		InventoryUtils.fillEmptySlots(inventory, createDummyItem(Material.BLACK_STAINED_GLASS_PANE));
		
		return inventory;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void onInventoryClick(Crewmate crewmate, InventoryClickEvent event) 
	{
		Player crewmatePlayer = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();

		switch(item.getType()) 
		{
		case LIME_STAINED_GLASS_PANE:
			
			//glow the digit for 7 ticks
			GlowEffect.addGlow(item);
			Bukkit.getScheduler().runTaskLater(AmongUs.getInstance(), () -> GlowEffect.deleteGlow(item), 7);
			
			try 
			{
				this.enterIDTask.enterDigit(crewmate, getDigit(item));
			}
			catch(ArithmeticException exception) 
			{
				crewmatePlayer.sendMessage(RED + "Too Many Digits!");
				return;
			}
			
			//update the new digit
			event.getInventory().setItem(PAPER_INDEX, createPaperItem(this.enterIDTask.getEnteredID(crewmate).get()));
			crewmatePlayer.playSound(crewmatePlayer.getLocation(), this.digitEnterSound, 1, 1);
			break;
		case GREEN_TERRACOTTA:
			Integer enteredID = this.enterIDTask.getEnteredID(crewmate).orElse(null);

			if(enteredID == null)
			{
				crewmatePlayer.sendMessage(RED + "What's your Personal ID?");
				return;
			}
			if(!this.enterIDTask.getPersonalID(crewmate).get().equals(enteredID))
			{
				crewmatePlayer.sendMessage(RED + "Invalid Personal ID!");
				return;
			}
			crewmatePlayer.sendMessage(GREEN + "Success - You were successfully identified.");
			crewmatePlayer.playSound(crewmatePlayer.getLocation(), this.identificationSucceedSound, 1, 1);
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

		return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, GREEN + String.valueOf(digit))
				.amountOf(amount)
				.createCopy();
	}
	
	private static ItemStack createPaperItem(int id) 
	{
		return createPaperItem(String.valueOf(id));
	}
	
	private static ItemStack createPaperItem(String id)
	{
		return new ItemBuilder(Material.PAPER, AQUA + "Entered ID: " + id).createCopy();
	}
	
	private static int getDigit(ItemStack digitItem)
	{
		String digitText = ChatColor.stripColor(digitItem.getItemMeta().getDisplayName());

		return Integer.valueOf(digitText);
	}
}