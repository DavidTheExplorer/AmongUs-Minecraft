package mazgani.amongus.corpses.components.blocks;

import java.util.Arrays;

import org.bukkit.block.Sign;

public class SignComponent extends BlockComponent
{
	protected final Sign sign;
	private final String[] oldLines, newLines;

	private static final int LINES_AMOUNT = 4;

	public SignComponent(Sign sign, String... newLines)
	{
		super(sign.getBlock());

		this.sign = sign;
		this.oldLines = sign.getLines().clone();
		this.newLines = (newLines.length == LINES_AMOUNT) ? newLines : Arrays.copyOf(newLines, 4);
	}
	public Sign getSign()
	{
		return this.sign;
	}
	public String[] getOldLines() 
	{
		return this.oldLines;
	}
	public String[] getNewLines() 
	{
		return this.newLines;
	}

	@Override
	public void spawn() 
	{
		for(int i = 0; i < LINES_AMOUNT; i++) 
		{
			this.sign.setLine(i, this.newLines[i]);
		}
		this.sign.update(true);
	}

	@Override
	public void despawn()
	{
		for(int i = 0; i < LINES_AMOUNT; i++)
		{
			this.sign.setLine(i, this.oldLines[i]);
		}
		this.sign.update(true);
	}
}