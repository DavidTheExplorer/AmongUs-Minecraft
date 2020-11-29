package mazgani.amongus.players.visual;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

public enum PlayerColor 
{
	RED,
	GREEN;
	
	private final ChatColor color;
	private final String name = WordUtils.capitalizeFully(name().toLowerCase());
	
	public static final PlayerColor[] VALUES = values();
	
	PlayerColor()
	{
		this.color = ChatColor.valueOf(name());
	}
	PlayerColor(ChatColor color)
	{
		this.color = color;
	}
	public ChatColor getColor() 
	{
		return this.color;
	}
	public String getName() 
	{
		return this.name;
	}
	public String getColoredName() 
	{
		return this.color + this.name;
	}
}
