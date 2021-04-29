package dte.amongus.holograms;

import java.util.function.BiPredicate;

import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;

public class LinesEqualler<LT extends HologramLine> implements BiPredicate<LT, LT>
{
	public LinesEqualler<LT> withTestValue()
	{
		return this;
	}
	
	@Override
	public boolean test(LT line1, LT line2) 
	{
		return true; //TODO: temp return until this class contains fields when the API is updated
	}
}