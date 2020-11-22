package mazgani.amongus.corpses.components.blocks;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import mazgani.amongus.corpses.BasicGameCorpse;

public class BlockStateComponent<S extends BlockState> extends BlockComponent
{
	protected BlockState capturedState;
	
	public BlockStateComponent(BasicGameCorpse corpse, Block block) 
	{
		super(corpse, block);
		
		this.capturedState = block.getState();
	}

	@Override
	public void spawn()
	{
		
	}
	@Override
	public void despawn() 
	{
		
	}
}
