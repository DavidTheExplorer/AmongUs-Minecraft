package dte.amongus.shiptasks.stabilizesteering;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.WHITE;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.ItemBuilder;

public class StabilizeSteeringInventoryManager extends TaskInventoryManager<StabilizeSteeringTask>
{
	private static final int[] TARGET_INDEXES = {22, 30, 31, 32, 40};
	
	private static final ItemBuilder TARGET_ITEM_BUILDER = new ItemBuilder(Material.WHITE_WOOL, AQUA + "Target")
			.withLore(WHITE + "Click the " + AQUA + "Middle" + WHITE + "!");

	public StabilizeSteeringInventoryManager(StabilizeSteeringTask task) 
	{
		super(task);
	}

	@Override
	public Inventory createInventory(AUGamePlayer opener) 
	{
		Inventory inventory = Bukkit.createInventory(null, 9 * 6, createTitle("Stabilize The Steering"));
		InventoryUtils.fillEmptySlots(inventory, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
		
		//add the "target cross" in the middle of the inventory
		for(int targetIndex : TARGET_INDEXES)
			inventory.setItem(targetIndex, TARGET_ITEM_BUILDER.createCopy());
		
		//add the "aim" stuff
		InventoryUtils.fillRow(inventory, 1, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		InventoryUtils.fillColumn(inventory, 7, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));

		return inventory;
	}

	@Override
	public void onInventoryClick(InventoryClickEvent event) 
	{
		if(event.getRawSlot() != 31)
			return;
		
		Inventory inventory = event.getInventory();
		
		InventoryUtils.allSlotsThat(inventory, i -> i.getType() == Material.WHITE_STAINED_GLASS_PANE).forEach(index -> inventory.setItem(index, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE)));
		InventoryUtils.fillRow(inventory, 3, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		InventoryUtils.fillColumn(inventory, 4, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		inventory.setItem(31, new ItemBuilder(Material.WHITE_WOOL, GREEN + "Success!").createCopy());
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return testInventory(event, "Stabilize The Steering");
	}
}