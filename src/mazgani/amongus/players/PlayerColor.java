package mazgani.amongus.players;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

public enum PlayerColor 
{
	RED,
	GREEN;
	
	private final ChatColor chatColor;
	private final String name = WordUtils.capitalizeFully(name().toLowerCase());
	
	public static final PlayerColor[] VALUES = values();
	
	PlayerColor()
	{
		this.chatColor = ChatColor.valueOf(name());
	}
	PlayerColor(ChatColor color)
	{
		this.chatColor = color;
	}
	public ChatColor getColor() 
	{
		return this.chatColor;
	}
	public String getName() 
	{
		return this.name;
	}
	public String getColoredName() 
	{
		return this.chatColor + this.name;
	}
}
