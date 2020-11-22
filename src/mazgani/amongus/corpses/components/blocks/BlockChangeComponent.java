package mazgani.amongus.corpses.components.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import mazgani.amongus.corpses.AbstractGameCorpse;

public class BlockChangeComponent extends BlockComponent
{
	private final Material newMaterial;
	
	//the data of the block before it's changed to the new data
	protected Material capturedMaterial;
	protected BlockState capturedState;

	public BlockChangeComponent(AbstractGameCorpse corpse, Block block, Material newMaterial)
	{
		super(corpse, block);
		
		this.newMaterial = newMaterial;
	}
	public BlockState getCapturedState() 
	{
		return this.capturedState;
	}

	@Override
	public void spawn() 
	{
		Block block = getBlock();
		
		//save the block data
		this.capturedMaterial = block.getType();
		this.capturedState = block.getState();
		
		//change the block
		block.setType(this.newMaterial);
	}

	@Override
	public void despawn() 
	{
		Block block = getBlock();
		
		block.setType(this.capturedMaterial);
		this.capturedState.update(true);
	}
}