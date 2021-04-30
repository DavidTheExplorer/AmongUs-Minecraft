package dte.amongus.displayers;

import java.util.function.Supplier;

import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;

import dte.amongus.utils.blocks.SignUtils;

public abstract class DisplaySign implements Displayer<String[]>
{
	private final Sign sign;
	
	private Supplier<String[]> baseLinesSupplier;
	
	protected DisplaySign(Sign sign)
	{
		this.sign = sign;
	}
	
	@Override
	public void update(boolean firstUpdate)
	{
		String[] newLines = createUpdate(firstUpdate);
		
		SignUtils.setLines(this.sign, true, newLines);
	}
	
	public Sign getSign() 
	{
		return this.sign;
	}
	
	protected void setBaseLinesSupplier(Supplier<String[]> baseLinesSupplier) 
	{
		this.baseLinesSupplier = baseLinesSupplier;
	}
	protected void setBaseLines(String... baseLines) 
	{
		this.baseLinesSupplier = () -> baseLines;
	}
	
	protected String[] baseLinesWith(String... additionalLines) 
	{
		if(this.baseLinesSupplier == null)
			throw new IllegalStateException("Base lines were not defined!");
		
		String[] finalLines = ArrayUtils.addAll(this.baseLinesSupplier.get(), additionalLines);
		
		//finalLines' length might be less than 4; if so, add empty lines
		return SignUtils.fixLines(finalLines);
	}
}