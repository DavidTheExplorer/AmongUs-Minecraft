package dte.amongus.shiptasks.enterid;

import static dte.amongus.utils.ChatColorUtils.bold;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.GlowEffect;
import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.RandomUtils;

public class EnterIDInventoryManager extends TaskInventoryManager<EnterIDTask>
{
	public EnterIDInventoryManager(EnterIDTask task)
	{
		super(task);
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener)
	{
		Inventory inventory = new InventoryBuilder(6, "Your ID is " + generateRandomID()).build();
		
		//add the digits
		for(int i = 10, digit = 1; i <= 16; i++, digit++)
			inventory.setItem(i, createDigitItem(digit));

		inventory.setItem(21, createDigitItem(8));
		inventory.setItem(23, createDigitItem(9));
		
		//add the enter button
		inventory.setItem(37, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));
		inventory.setItem(38, new ItemBuilder(Material.GREEN_TERRACOTTA, bold(ChatColor.GREEN) + "Enter ID").createCopy());
		inventory.setItem(39, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));
		
		//add the current id paper
		inventory.setItem(41, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));
		inventory.setItem(42, new ItemBuilder(Material.PAPER, ChatColor.AQUA + "Current Code: Nothing").createCopy());
		inventory.setItem(43, InventoryBuilder.buildWall(Material.GREEN_STAINED_GLASS_PANE));
		
		//decorate the remaining slots as walls
		InventoryUtils.fillEmptySlots(inventory, InventoryBuilder.buildWall(Material.BLACK_STAINED_GLASS_PANE));

		return inventory;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		ItemStack item = event.getCurrentItem();
		
		switch(item.getType()) 
		{
		case LIME_STAINED_GLASS_PANE:
			int lastDigitIndex = InventoryUtils.firstSlotWhoseItem(event.getInventory(), GlowEffect::hasGlow);
			
			if(lastDigitIndex == -1) 
			{
				GlowEffect.addGlow(item);
				return;
			}
		}
		
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event)
	{
		return testInventory(event, "Your ID is ");
	}

	//returns a 3-5 digits number
	private static int generateRandomID() 
	{
		return RandomUtils.randomInt(100, 99999, true, true);
	}

	private static ItemStack createDigitItem(int digit) 
	{
		return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN.toString() + digit)
				.amountOf(digit)
				.createCopy();
	}
}
