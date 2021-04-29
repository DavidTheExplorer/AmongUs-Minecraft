package dte.amongus.utils.blocks.transformers;

import java.util.function.Consumer;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;

import dte.amongus.utils.blocks.SignUtils;
import dte.amongus.utils.blocks.transformers.restorers.BlockStateRestorer;
import dte.amongus.utils.blocks.transformers.restorers.Restorer;

@SuppressWarnings("unchecked") //all casts in this class are safe
public class BlockTransformer
{
	//material
	private Material newMaterial;
	private boolean applyPhysics = false;

	//state
	private Class<? extends BlockState> stateClass;
	private Consumer<BlockState> stateConsumer;

	//data
	private Class<? extends BlockData> dataClass;
	private Consumer<BlockData> dataConsumer;
	
	public static final Consumer<Openable> OPENABLE_REVERSE = openable -> openable.setOpen(!openable.isOpen());
	
	public BlockTransformer(){}

	public BlockTransformer(BlockTransformer other) 
	{
		this.newMaterial = other.newMaterial;
		this.applyPhysics = other.applyPhysics;
		this.stateClass = other.stateClass;
		this.stateConsumer = other.stateConsumer;
		this.dataClass = other.dataClass;
		this.dataConsumer = other.dataConsumer;
	}
	public BlockTransformer toMaterial(Material newMaterial) 
	{
		return toMaterial(newMaterial, true);
	}
	public BlockTransformer toMaterial(Material newMaterial, boolean applyPhysics) 
	{
		this.applyPhysics = applyPhysics;
		this.newMaterial = newMaterial;
		return this;
	}
	public <S extends BlockState> BlockTransformer withStateSettings(Class<S> stateClass, Consumer<S> stateConsumer) 
	{
		this.stateClass = stateClass;
		this.stateConsumer = state -> stateConsumer.accept((S) state);
		return this;
	}
	public <D extends BlockData> BlockTransformer withDataSettings(Class<D> dataClass, Consumer<D> dataConsumer) 
	{
		this.dataClass = dataClass;
		this.dataConsumer = data -> dataConsumer.accept((D) data);
		return this;
	}
	public Restorer transform(Block block)
	{
		BlockState oldState = block.getState();
		
		applyNewMaterial(block);
		applyNewData(block);
		applyNewState(block);
		
		return new BlockStateRestorer(oldState);
	}
	
	private void applyNewMaterial(Block block) 
	{
		if(this.newMaterial == null || block.getType() == this.newMaterial)
			return;
		
		block.setType(this.newMaterial, this.applyPhysics);
	}
	private void applyNewState(Block block) 
	{
		if(this.stateClass == null)
			return;
		
		BlockState newState = this.stateClass.cast(block.getState());
		this.stateConsumer.accept(newState);
	}
	private void applyNewData(Block block) 
	{
		if(this.dataClass == null)
			return;
		
		BlockData newData = this.dataClass.cast(block.getState().getBlockData());
		this.dataConsumer.accept(newData);
		block.setBlockData(newData);
	}

	public static BlockTransformer toSign(Material signMaterial, String... newLines) 
	{
		Validate.isTrue(SignUtils.isSign(signMaterial), String.format("The provided material(%s) doesn't represent a Sign!", signMaterial.name()));
		
		String[] fixedLines = SignUtils.fixLines(newLines);
		
		return new BlockTransformer()
				.toMaterial(signMaterial)
				.withStateSettings(Sign.class, sign -> SignUtils.setLines(sign, true, fixedLines));
	}
}