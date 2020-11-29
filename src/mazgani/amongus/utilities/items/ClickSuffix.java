package mazgani.amongus.utilities.items;

import org.bukkit.ChatColor;

public enum ClickSuffix
{
	LEFT("Left Click"),
	RIGHT("Right Click"),
	LEFT_FIRST("Left / Right Click"),
	RIGHT_FIRST("Right / Left Click");

	private final String suffix;

	ClickSuffix(String suffix)
	{
		this.suffix = suffix;
	}
	public String createSuffix(ChatColor bracketsColor, ChatColor mouseButtonsColor, ChatColor slashColor) 
	{
		return this.suffix
				.replace("Left", mouseButtonsColor + "Left")
				.replace("Right", mouseButtonsColor + "Right")
				.replace("/", slashColor + "/")
				.replace("(", bracketsColor + "(")
				.replace(")", bracketsColor + ")");
	}
}