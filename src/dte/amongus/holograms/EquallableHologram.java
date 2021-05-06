package dte.amongus.holograms;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import dte.amongus.holograms.decorator.HologramDecorator;
import dte.amongus.hooks.HolographicDisplaysHook;

public class EquallableHologram extends HologramDecorator
{
	protected static HolographicDisplaysHook hdHook;
	
	public EquallableHologram(Hologram hologram) 
	{
		super(hologram);
	}
	
	@Override
	public boolean equals(Object object) 
	{
		if(this == object)
			return true;
		
		if(object == null)
			return false;
		
		if(!(object instanceof Hologram))
			return false;
		
		Hologram other = (Hologram) object;
		
		return hdHook.equals(this.hologram, other);
	}
	
	@Override
	public int hashCode()
	{
		int[] linesHashcodes = IntStream.range(0, hologram.size())
				.mapToObj(hologram::getLine)
				.mapToInt(hdHook::hashCode)
				.toArray();
		
		return Arrays.hashCode(linesHashcodes);
	}
	
	public static void setHolographicsDisplaysHook(HolographicDisplaysHook hdook) 
	{
		EquallableHologram.hdHook = hdook;
	}
}