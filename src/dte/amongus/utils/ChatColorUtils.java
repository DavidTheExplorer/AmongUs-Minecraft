package dte.amongus.utils;

import static org.bukkit.ChatColor.BOLD;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class ChatColorUtils 
{
	//Container of static methods
	private ChatColorUtils(){}
	
	private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
	
	public static String colorize(String text) 
	{
		String result = text;
		
		result = colorizeChatColors(result);
		result = colorizeHex(result);
		
		return result;
	}
	public static String colorizeChatColors(String text) 
	{
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	public static String colorizeHex(String text)
	{
		Matcher matcher = HEX_COLOR_PATTERN.matcher(text);
		
		while(matcher.find())
		{
			String color = text.substring(matcher.start(), matcher.end()); 
			
			text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString());
		}
		return text;
	}
	public static String bold(ChatColor color) 
	{
		return color.toString() + BOLD;
	}
}
