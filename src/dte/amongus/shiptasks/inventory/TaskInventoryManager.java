package dte.amongus.shiptasks.inventory;

import static org.bukkit.ChatColor.BLACK;

import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.utils.items.ItemBuilder;

public abstract class TaskInventoryManager<T extends InventoryTask<?>>
{
	protected final T task;

	private static final Pattern TITLE_PATTERN = Pattern.compile("Task > [a-zA-Z0-9 ]+");

	public TaskInventoryManager(T task) 
	{
		this.task = task;
	}
	public abstract Inventory createInventory(AUGamePlayer opener);
	public abstract void onInventoryClick(InventoryClickEvent event); //TODO: change to return a boolean that indicates whether the player finished the task
	public abstract boolean wasInvolvedAt(InventoryClickEvent event);

	protected static boolean testInventory(InventoryClickEvent event, String description) 
	{
		String title = event.getView().getTitle();

		if(TITLE_PATTERN.matcher(description).matches())
			return false;
		
		return title.substring(title.indexOf(">") + 2).startsWith(description);
	}

	protected static String createTitle(String description)
	{
		return TITLE_PATTERN.pattern().replace("[a-zA-Z0-9 ]+", description);
	}

	protected static ItemStack createDummyItem(Material material) 
	{
		return new ItemBuilder(material, BLACK + ".").createCopy();
	}
}