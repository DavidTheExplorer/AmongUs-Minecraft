package dte.amongus.holograms.equallable;

import java.util.function.BiPredicate;
import java.util.function.Function;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import dte.amongus.holograms.HologramDecorator;

public class EquallableHologram extends HologramDecorator
{
	private final BiPredicate<Hologram, Hologram> equaller;
	private final Function<Hologram, Integer> hashcodeProvider;
	
	public EquallableHologram(Hologram hologram, BiPredicate<Hologram, Hologram> equaller, Function<Hologram, Integer> hashcodeProvider) 
	{
		super(hologram);
		
		this.equaller = equaller;
		this.hashcodeProvider = hashcodeProvider;
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
		
		return this.equaller.test(this.hologram, other);
	}
	
	@Override
	public int hashCode()
	{
		return this.hashcodeProvider.apply(this.hologram);
	}
}