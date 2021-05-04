package dte.amongus.shiptasks.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.utils.InventoryUtils;
import dte.amongus.utils.items.ItemBuilder;

public abstract class TaskInventoryManager<T extends InventoryTask<?>>
{
	protected final T task;
	
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
		
		if(!title.startsWith("Task > ")) 
			return false;
		
		int descriptionIndex = title.indexOf("Task > ") + "Task > ".length();
		
		return title.substring(descriptionIndex).startsWith(description);
	}
	
	protected static class InventoryBuilder
	{
		private int lines;
		private String title;
		
		//walls data
		private boolean buildWalls;
		private ItemStack wallsItem;
		
		public InventoryBuilder(int lines, String title)
		{
			this.lines = lines;
			this.title = createTitle(title);
		}
		public InventoryBuilder withWalls(ItemStack wallsItem) 
		{
			this.buildWalls = true;
			this.wallsItem = wallsItem;
			return this;
		}
		public InventoryBuilder withWalls() 
		{
			return withWalls(buildWall(Material.BLACK_STAINED_GLASS_PANE));
		}
		public Inventory build()
		{
			Inventory inventory = Bukkit.createInventory(null, 9 * this.lines, this.title);
			
			if(this.buildWalls)
				InventoryUtils.buildWalls(inventory, this.wallsItem);
			
			return inventory;
		}
		
		private String createTitle(String title) 
		{
			return String.format("Task > %s", title);
		}
		
		public static ItemStack buildWall(Material material) 
		{
			return new ItemBuilder(material, ChatColor.BLACK + "-").createCopy();
		}
	}
}