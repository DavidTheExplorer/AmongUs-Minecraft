package dte.amongus.shiptasks.list.stabilizesteering;

import static dte.amongus.utils.InventoryUtils.createDummyItem;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.WHITE;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import dte.amongus.games.players.Crewmate;
import dte.amongus.shiptasks.inventory.TaskInventoryManager;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.ItemBuilder;

public class StabilizeSteeringInventoryManager extends TaskInventoryManager
{
	private final Sound steeringSound;

	private static final int PREVIOUS_TARGET_INDEX = 16, NEW_TARGET_INDEX = 31;

	public StabilizeSteeringInventoryManager(Sound steeringSound) 
	{
		this.steeringSound = steeringSound;
	}

	@Override
	public Inventory createInventory(Crewmate opener) 
	{
		Inventory inventory = Bukkit.createInventory(null, 9 * 6, createTitle("Stabilize The Steering"));
		InventoryUtils.fillEmptySlots(inventory, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
		
		//add the new target to the middle of the inventory
		inventory.setItem(NEW_TARGET_INDEX, new ItemBuilder(Material.WHITE_WOOL, GREEN + "New Target")
				.withLore(WHITE + "Click here to Retarget!")
				.createCopy());
		
		//add the "aim" stuff
		InventoryUtils.fillRow(inventory, 1, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		InventoryUtils.fillColumn(inventory, 7, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		
		inventory.setItem(PREVIOUS_TARGET_INDEX, new ItemBuilder(Material.WHITE_WOOL, RED + "Current Target")
				.withLore(WHITE + "Click on another one.")
				.createCopy());
		
		return inventory;
	}
	
	@Override
	public void onInventoryClick(Crewmate crewmate, InventoryClickEvent event) 
	{
		if(event.getRawSlot() != NEW_TARGET_INDEX)
			return;
		
		Inventory inventory = event.getInventory();
		InventoryUtils.allSlotsThat(inventory, i -> i.getType() == Material.WHITE_STAINED_GLASS_PANE).forEach(index -> inventory.setItem(index, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE)));
		InventoryUtils.fillRow(inventory, 3, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		InventoryUtils.fillColumn(inventory, 4, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		inventory.setItem(NEW_TARGET_INDEX, new ItemBuilder(Material.WHITE_WOOL, GREEN + "Success!").createCopy());
		inventory.setItem(PREVIOUS_TARGET_INDEX, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
		
		Player crewmatePlayer = (Player) event.getWhoClicked();
		crewmatePlayer.playSound(crewmatePlayer.getLocation(), this.steeringSound, 1, 1);
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return testInventory(event, "Stabilize The Steering");
	}
}