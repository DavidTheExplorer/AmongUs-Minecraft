package dte.amongus.sabotages.types;

import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;

import dte.amongus.utils.blocks.transformers.BlockTransformer;

public class OpenableSabotage extends BlockSabotage
{
	public static final BlockTransformer REVERSE_OPEN = new BlockTransformer()
			.withDataSettings(Openable.class, BlockTransformer.OPENABLE_REVERSE);
	
	public OpenableSabotage(Block... openables)
	{
		super(openables, REVERSE_OPEN);
	}
	
	public static OpenableSabotage from(Block... openableBlocks) 
	{
		verifyData(openableBlocks, Openable.class);
		
		return new OpenableSabotage(openableBlocks);
	}
}