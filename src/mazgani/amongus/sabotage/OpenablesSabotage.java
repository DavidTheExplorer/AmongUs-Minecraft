package mazgani.amongus.sabotage;

import java.util.Arrays;
import java.util.function.Consumer;

import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;

public class OpenablesSabotage implements Sabotage
{
	protected final Block[] blocks;
	protected final Openable[] openables;

	public OpenablesSabotage(Block... openableBlocks) 
	{
		this.blocks = openableBlocks;
		
		this.openables = new Openable[openableBlocks.length];
		
		for(int i = 0; i < openableBlocks.length; i++) 
		{
			this.openables[i] = (Openable) openableBlocks[i].getBlockData();
		}
	}
	public Block[] getBlocks() 
	{
		return this.blocks;
	}
	public Openable[] getOpenables() 
	{
		return this.openables;
	}

	@Override
	public void sabotage() 
	{
		forOpenables(openable -> openable.setOpen(false));
	}

	@Override
	public void unsabotage() 
	{
		forOpenables(openable -> openable.setOpen(true));
	}

	protected void forOpenables(Consumer<Openable> action) 
	{
		Arrays.stream(this.openables).forEach(action);
	}
}
