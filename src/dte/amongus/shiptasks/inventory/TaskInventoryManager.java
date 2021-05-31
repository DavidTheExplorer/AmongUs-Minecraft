package dte.amongus.shiptasks.inventory;

import java.util.regex.Pattern;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import dte.amongus.games.players.Crewmate;

public abstract class TaskInventoryManager
{
	private static final Pattern TITLE_PATTERN = Pattern.compile("Task > [a-zA-Z0-9 ]+");

	public abstract Inventory createInventory(Crewmate opener);
	public abstract void onInventoryClick(Crewmate crewmate, InventoryClickEvent event); //TODO: change to return a boolean that indicates whether the player finished the task
	public abstract boolean wasInvolvedAt(InventoryClickEvent event);
	
	protected static String createTitle(String description)
	{
		return TITLE_PATTERN.pattern().replace("[a-zA-Z0-9 ]+", description);
	}
	
	protected static boolean testInventory(InventoryClickEvent event, String description) 
	{
		String title = event.getView().getTitle();
		
		if(TITLE_PATTERN.matcher(description).matches())
			return false;
		
		return title.substring(title.indexOf(">") + 2).startsWith(description);
	}
}