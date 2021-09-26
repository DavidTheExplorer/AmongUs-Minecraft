package dte.amongus.holograms.equallable;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import dte.amongus.hooks.HolographicDisplaysHook;

public class SimpleEquallableHologram extends EquallableHologram
{
	protected static HolographicDisplaysHook hdHook;
	
	public SimpleEquallableHologram(Hologram hologram)
	{
		super(hologram, hdHook::equals, HashcodeProvider.INSTANCE);
	}
	
	public static void setHolographicsDisplaysHook(HolographicDisplaysHook hdook) 
	{
		SimpleEquallableHologram.hdHook = hdook;
	}
	
	public static class HashcodeProvider implements Function<Hologram, Integer>
	{
		public static final HashcodeProvider INSTANCE = new HashcodeProvider();
		
		@Override
		public Integer apply(Hologram hologram)
		{
			int[] linesHashcodes = IntStream.range(0, hologram.size())
					.mapToObj(hologram::getLine)
					.mapToInt(hdHook::hashCode)
					.toArray();
			
			return Arrays.hashCode(linesHashcodes);
		}
	}
}