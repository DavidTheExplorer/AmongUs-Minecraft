package mazgani.amongus.displayers;

import org.bukkit.block.Sign;

public abstract class DisplaySign<T>
{
	protected final T displayed;
	protected final Sign sign;
	
	protected DisplaySign(T displayed, Sign sign) 
	{
		this.displayed = displayed;
		this.sign = sign;
		
		updateLines(true);
	}
	public Sign getSign() 
	{
		return this.sign;
	}
	public T getDisplayed()
	{
		return this.displayed;
	}
	public void updateLines()
	{
		updateLines(false);
	}
	private void updateLines(boolean firstUpdate) 
	{
		String[] newLines = generateUpdate(firstUpdate);
		
		for(int i = 0; i < newLines.length; i++) 
		{
			this.sign.setLine(i, newLines[i]);
		}
		this.sign.update(true);
	}
	public abstract String[] generateUpdate(boolean firstUpdate);
}