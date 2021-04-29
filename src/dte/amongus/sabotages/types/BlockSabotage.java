package dte.amongus.sabotages.types;

import static dte.amongus.utils.java.Predicates.negate;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.text.WordUtils;

import com.google.common.collect.Sets;

import dte.amongus.sabotages.Sabotage;
import dte.amongus.utils.blocks.transformers.BlockTransformer;
import dte.amongus.utils.blocks.transformers.restorers.Restorer;

public class BlockSabotage implements Sabotage
{
	private final Map<Location, BlockTransformer> blocksTransformers;
	
	private List<Restorer> blocksRestorers;
	
	public BlockSabotage(Map<Location, BlockTransformer> blocksTransformers)
	{
		this.blocksTransformers = new HashMap<>(blocksTransformers);
	}
	public BlockSabotage(Block[] blocks, BlockTransformer globalTransformer) 
	{
		this(Arrays.stream(blocks).collect(toMap(Block::getLocation, block -> globalTransformer)));
	}
	
	@Override
	public void activate()
	{
		this.blocksRestorers = this.blocksTransformers.entrySet().stream()
				.map(entry -> entry.getValue().transform(entry.getKey().getBlock()))
				.collect(toList());
	}

	@Override
	public void disable()
	{
		this.blocksRestorers.forEach(Restorer::restore);
	}

	protected static void verifyType(Block[] blocks, String sabotageName, Material... possibleTypes) 
	{
		Set<Material> possibles = Sets.newHashSet(possibleTypes);
		
		verifyType(blocks, sabotageName, negate(possibles::contains));
	}
	protected static void verifyType(Block[] blocks, String sabotageName, Predicate<Material> materialTester) 
	{
		String badMaterialsNames = Arrays.stream(blocks)
				.map(Block::getType)
				.filter(negate(materialTester))
				.map(Material::name)
				.collect(joining(", "));
		
		if(!badMaterialsNames.isEmpty())
			throw new IllegalArgumentException(String.format("The following materials cannot be registered for a %s: %s", sabotageName, badMaterialsNames));
	}
	protected static void verifyData(Block[] blocks, Class<? extends BlockData> dataClass) 
	{
		String badMaterialsNames = Arrays.stream(blocks)
				.filter(block -> !dataClass.isInstance(block.getState().getBlockData()))
				.map(Block::getType)
				.map(Material::name)
				.collect(joining(", "));
		
		if(!badMaterialsNames.isEmpty()) 
		{
			String dataName = WordUtils.capitalizeFully(dataClass.getSimpleName());
			
			throw new IllegalArgumentException(String.format("Illegal blocks were registered for an %s Sabotage: %s", dataName, badMaterialsNames));
		}
	}
}