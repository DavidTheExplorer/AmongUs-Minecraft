package dte.amongus.utils.blocks.transformers.restorers;

import org.bukkit.block.BlockState;

public class BlockStateRestorer implements Restorer
{
	protected final BlockState originalState;
	
	public BlockStateRestorer(BlockState originalState) 
	{
		this.originalState = originalState;
	}
	
	@Override
	public void restore()
	{
		this.originalState.update(true);
	}
}