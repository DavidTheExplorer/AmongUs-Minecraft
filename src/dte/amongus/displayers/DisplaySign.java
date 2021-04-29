package dte.amongus.displayers;

import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.ArrayUtils;

import dte.amongus.utils.blocks.SignUtils;

public abstract class DisplaySign implements Displayer<String[]>
{
	private final Sign sign;
	
	private String[] baseLines;
	
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
	
	protected void setBaseLines(String... baseLines) 
	{
		this.baseLines = SignUtils.fixLines(baseLines);
	}
	
	protected String[] baseLinesWith(String... additionalLines) 
	{
		if(this.baseLines == null)
			this.baseLines = new String[0];
		
		return ArrayUtils.addAll(this.baseLines, additionalLines);
	}
}