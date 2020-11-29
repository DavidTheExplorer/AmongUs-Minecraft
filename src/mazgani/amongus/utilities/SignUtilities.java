package mazgani.amongus.utilities;

import java.util.Arrays;

import org.apache.commons.lang.Validate;
import org.bukkit.block.Sign;

public class SignUtilities
{
	//Container of static methods
	private SignUtilities(){}

	public static final int LINES_AMOUNT = 4;
	
	public static String[] setLines(Sign sign, String[] lines) 
	{
		Validate.isTrue(lines.length == LINES_AMOUNT, String.format("Signs has only %d lines! (%d were provided)", LINES_AMOUNT, lines.length));
		
		String[] oldLines = sign.getLines().clone();
		
		for(int i = 0; i < LINES_AMOUNT; i++) 
		{
			sign.setLine(i, lines[i]);
		}
		sign.update();
		
		return oldLines;
	}
	public static String[] fixLinesIfNeeded(String[] lines) 
	{
		//if the lines amount is exactly the needed, just return the array
		if(lines.length == LINES_AMOUNT) 
			return lines;
		
		String[] cloned = lines.clone();
		
		//if there are too many lines
		if(cloned.length > 4) 
			return Arrays.copyOf(cloned, 4);

		//if there are less than 4 lines
		else
			return insertEmptyLines(cloned);
	}
	private static String[] insertEmptyLines(String[] lines)
	{
		String[] fixedArray = Arrays.copyOf(lines, 4);

		return Arrays.stream(fixedArray)
				.map(line -> line == null ? "" : line) //change the nulls(inserted by the resizing method) to empty lines
				.toArray(String[]::new);
	}
}
