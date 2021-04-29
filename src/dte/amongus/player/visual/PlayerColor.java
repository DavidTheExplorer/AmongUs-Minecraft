package dte.amongus.player.visual;

import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;

//TODO: Support hexadecimal colors, so all the colors in the original game are supported.
/*
 * RED("c51111"),
	BLUE("123ed1"),
	GREEN("117f2d"),
	PINK("ed54ba"),
	ORANGE("ef7d0d"),
	YELLOW("f5f557"),
	BLACK("3f474e"),
	WHITE("d6e0f0"),
	PURPLE("6b2fbb"),
	BROWN("71491e"),
	AQUA("38fedc"),
	LIME("50ef39");
 */
public enum PlayerColor
{
	RED,
	BLUE,
	AQUA,
	WHITE,
	YELLOW,
	GREEN(ChatColor.DARK_GREEN),
	PINK(ChatColor.LIGHT_PURPLE),
	ORANGE(ChatColor.GOLD),
	BLACK(ChatColor.GRAY),
	PURPLE(ChatColor.DARK_PURPLE),
	LIME(ChatColor.GREEN);
	
	private final String name = WordUtils.capitalizeFully(name().toLowerCase());
	private final ChatColor chatcolor;

	public static final PlayerColor[] VALUES = values();

	PlayerColor()
	{
		this.chatcolor = ChatColor.valueOf(name());
	}
	PlayerColor(ChatColor chatcolor)
	{
		this.chatcolor = chatcolor;
	}
	public String getName() 
	{
		return this.name;
	}
	public ChatColor getChatColor()
	{
		return this.chatcolor;
	}
}