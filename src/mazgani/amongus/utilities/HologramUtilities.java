package mazgani.amongus.utilities;

import java.util.stream.IntStream;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

public class HologramUtilities
{
	/**
	 * Makes the provided {@code hologram} visible or invisible.
	 * 
	 * @param hologram The hologram.
	 * @param mode The visibility mode: true to be visible, false to invisible.
	 */
	public static void setVisibility(Hologram hologram, boolean mode) 
	{
		hologram.getVisibilityManager().setVisibleByDefault(mode);
	}
	
	/**
	 * Checks whether the provided {@code holograms}' lines and locations equal to each other.
	 * 
	 * @param holo1 The first hologram.
	 * @param holo2 The second hologram.
	 * @return The equality of the visible parts of the provided holograms.
	 */
	public static boolean equals(Hologram holo1, Hologram holo2) 
	{
		if(holo1.size() != holo2.size()) 
		{
			return false;
		}
		if(!holo1.getLocation().equals(holo2.getLocation()))
		{
			return false;
		}
		final int linesAmount = holo1.size();

		return IntStream.range(0, linesAmount).allMatch(i -> equals(holo1.getLine(i), holo2.getLine(i)));
	}

	/**
	 * Checks whether the provided {@code hologram lines} equal to each other.
	 * <p>
	 * The method automatically returns <b>false</b> if their types are different, for example when 1 line is a <i>TextLine</i> and the second is a <i>ItemLine</i>.
	 * 
	 * @param line1 The first hologram line.
	 * @param line2 The second hologram line.
	 * @return The equality of the provided hologram lines.
	 */
	public static boolean equals(HologramLine line1, HologramLine line2) 
	{
		if(line1.getClass() != line2.getClass())
		{
			return false;
		}
		if(line1 instanceof TextLine)
			return equals((TextLine) line1, (TextLine) line2);

		else if(line1 instanceof ItemLine) 
			return equals((ItemLine) line1, (ItemLine) line2);

		return false;
	}

	/*
	 * Specific HologramLine Implementations Checkers
	 */
	public static boolean equals(TextLine line1, TextLine line2) 
	{
		return line1.getText().equals(line2.getText());
	}
	public static boolean equals(ItemLine line1, ItemLine line2) 
	{
		return line1.getItemStack().equals(line2.getItemStack());
	}

	public static int hashCode(HologramLine line) 
	{
		if(line instanceof TextLine) 
		{
			TextLine textLine = (TextLine) line;

			return textLine.getText().hashCode();
		}
		else if(line instanceof ItemLine)
		{
			ItemLine itemLine = (ItemLine) line;

			return itemLine.getItemStack().hashCode();
		}
		return 0;
	}
}