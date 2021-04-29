package dte.amongus.hooks;

import java.util.stream.IntStream;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

public class HolographicDisplaysHook extends PluginHook
{
	public HolographicDisplaysHook()
	{
		super("HolographicsDisplays");
	}

	public void setVisibility(Hologram hologram, boolean mode) 
	{
		hologram.getVisibilityManager().setVisibleByDefault(mode);
	}
	
	//checks whether the holograms' lines and locations equal to each other
	public boolean equals(Hologram holo1, Hologram holo2) 
	{
		if(holo1.size() != holo2.size()) 
			return false;
		
		if(!holo1.getLocation().equals(holo2.getLocation()))
			return false;
		
		final int linesAmount = holo1.size();
		
		return IntStream.range(0, linesAmount)
				.allMatch(i -> equals(holo1.getLine(i), holo2.getLine(i)));
	}

	//checks whether the hologram's lines equal to each other
	public boolean equals(HologramLine line1, HologramLine line2) 
	{
		if(line1.getClass() != line2.getClass())
			return false;
		
		if(line1 instanceof TextLine)
			return equals((TextLine) line1, (TextLine) line2);

		else if(line1 instanceof ItemLine) 
			return equals((ItemLine) line1, (ItemLine) line2);

		return false;
	}

	/*
	 * Specific HologramLine Implementations Checkers
	 */
	public boolean equals(TextLine line1, TextLine line2) 
	{
		return line1.getText().equals(line2.getText());
	}
	public boolean equals(ItemLine line1, ItemLine line2) 
	{
		return line1.getItemStack().equals(line2.getItemStack());
	}

	public int hashCode(HologramLine line) 
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