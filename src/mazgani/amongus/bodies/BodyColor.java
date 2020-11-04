package mazgani.amongus.bodies;

import org.bukkit.ChatColor;

import lombok.Getter;

public enum BodyColor
{
	RED,
	GREEN;
	
	@Getter
	private final ChatColor chatColor;
	
	BodyColor()
	{
		this.chatColor = ChatColor.valueOf(name());
	}
	BodyColor(ChatColor color)
	{
		this.chatColor = color;
	}
}
