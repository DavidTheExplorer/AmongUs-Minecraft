package mazgani.amongus.displayers;

import org.bukkit.block.Sign;

public abstract class DisplaySign<T>
{
	protected final T type;
	protected final Sign sign;
	
	protected DisplaySign(T type, Sign sign) 
	{
		this.type = type;
		this.sign = sign;
		
		updateLines(true);
	}
	public Sign getSign() 
	{
		return this.sign;
	}
	public T getDisplayedType() 
	{
		return this.type;
	}
	public void updateLines()
	{
		updateLines(false);
	}
	private void updateLines(boolean firstUpdate) 
	{
		String[] newLines = generateUpdate(this.type, firstUpdate);
		
		for(int i = 0; i < newLines.length; i++) 
		{
			this.sign.setLine(i, newLines[i]);
		}
		this.sign.update(true);
	}
	public abstract String[] generateUpdate(T type, boolean firstUpdate);
}
