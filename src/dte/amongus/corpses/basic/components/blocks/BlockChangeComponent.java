package dte.amongus.corpses.basic.components.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.utils.blocks.transformers.BlockTransformer;
import dte.amongus.utils.blocks.transformers.restorers.Restorer;

public class BlockChangeComponent extends AbstractBlockComponent
{
	private final BlockTransformer transformer;
	
	private Restorer restorer;

	/**
	 * Creates a component that changes the provided block's material to the <i>provided</i> material.
	 * 
	 * @param corpse The corpse that consists of this block component.
	 * @param block The block this component operates on.
	 */
	public BlockChangeComponent(BasicCorpse corpse, Block block, Material newMaterial) 
	{
		this(corpse, block, newMaterial, false);
	}

	/**
	 * Creates a component that changes the provided block's material to the <i>provided</i> material.
	 * 
	 * @param corpse The corpse that consists of this block component.
	 * @param block The block this component operates on.
	 * @param applyPhysics Whether the surroundings blocks will be affected from the material change. 
	 */
	public BlockChangeComponent(BasicCorpse corpse, Block block, Material newMaterial, boolean applyPhysics) 
	{
		this(corpse, block, new BlockTransformer().toMaterial(newMaterial, applyPhysics));
	}

	/**
	 * Creates a component that changes the provided block in some way.
	 * 
	 * @param corpse The corpse that consists of this block component.
	 * @param block The block this component operates on.
	 * @param transformer The transformer which will change the block.
	 */
	public BlockChangeComponent(BasicCorpse corpse, Block block, BlockTransformer transformer) 
	{
		super(corpse, block);
		
		this.transformer = transformer;
	}
	
	@Override
	public void spawn() 
	{
		this.restorer = this.transformer.transform(getBlock());
	}

	@Override
	public void despawn() 
	{
		this.restorer.restore();
	}
}