package dte.amongus.shiptasks.inventory.stabilizesteering;

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
		Inventory taskInventory = Bukkit.createInventory(null, 9 * 6, createTitle("Stabilize The Steering"));
		InventoryUtils.fillEmptySlots(taskInventory, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
		
		//add the new target to the middle of the inventory
		taskInventory.setItem(NEW_TARGET_INDEX, new ItemBuilder(Material.WHITE_WOOL, GREEN + "New Target")
				.withLore(WHITE + "Click here to Retarget!")
				.createCopy());
		
		//add the "aim" stuff
		InventoryUtils.fillRow(taskInventory, 1, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		InventoryUtils.fillColumn(taskInventory, 7, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		
		taskInventory.setItem(PREVIOUS_TARGET_INDEX, new ItemBuilder(Material.WHITE_WOOL, RED + "Current Target")
				.withLore(WHITE + "Click on another one.")
				.createCopy());
		
		return taskInventory;
	}
	
	@Override
	public void onInventoryClick(Crewmate crewmate, InventoryClickEvent event) 
	{
		if(event.getRawSlot() != NEW_TARGET_INDEX)
			return;
		
		Inventory taskInventory = event.getInventory();
		InventoryUtils.allSlotsThat(taskInventory, i -> i.getType() == Material.WHITE_STAINED_GLASS_PANE).forEach(index -> taskInventory.setItem(index, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE)));
		InventoryUtils.fillRow(taskInventory, 3, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		InventoryUtils.fillColumn(taskInventory, 4, createDummyItem(Material.WHITE_STAINED_GLASS_PANE));
		taskInventory.setItem(NEW_TARGET_INDEX, new ItemBuilder(Material.WHITE_WOOL, GREEN + "Success!").createCopy());
		taskInventory.setItem(PREVIOUS_TARGET_INDEX, createDummyItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
		
		Player crewmatePlayer = crewmate.getPlayer();
		crewmatePlayer.playSound(crewmatePlayer.getLocation(), this.steeringSound, 1, 1);
	}

	@Override
	public boolean wasInvolvedAt(InventoryClickEvent event) 
	{
		return testInventory(event, "Stabilize The Steering");
	}
}