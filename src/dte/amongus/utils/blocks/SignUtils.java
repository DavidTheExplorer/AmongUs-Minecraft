package dte.amongus.utils.blocks;

import java.util.Arrays;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Sign;

public class SignUtils 
{
	//Container of static methods
	private SignUtils(){}
	
	public static final int LINES_AMOUNT = 4;

	//returns the old lines of the sign
	public static String[] setLines(Sign sign, boolean forceUpdate, String... newLines)
	{
		Validate.isTrue(newLines.length == LINES_AMOUNT, String.format("Signs have only %d lines! (%d were provided)", LINES_AMOUNT, newLines.length));

		String[] oldLines = sign.getLines().clone();

		for(int i = 0; i < LINES_AMOUNT; i++) 
			sign.setLine(i, newLines[i]);

		sign.update(forceUpdate);

		return oldLines;
	}
	public static String[] fixLines(String[] lines)
	{
		if(lines.length == LINES_AMOUNT)
			return lines; //the lines amount is exactly 4 - so no fix needed
		
		else if(lines.length > 4)
			return Arrays.copyOf(lines, 4); //there are too many lines
		
		else
			return insertEmptyLines(lines); //there are less than 4 lines
	}
	public static boolean isEmpty(Sign sign)
	{
		return Arrays.stream(sign.getLines()).allMatch(String::isEmpty);
	}
	public static boolean isSign(Material material) 
	{
		return material.name().endsWith("SIGN");
	}
	private static String[] insertEmptyLines(String[] lines)
	{
		String[] fixedLines = new String[LINES_AMOUNT];
		
		Arrays.setAll(fixedLines, i -> (i < lines.length ? lines[i] : ""));
		
		return fixedLines;
	}
}
