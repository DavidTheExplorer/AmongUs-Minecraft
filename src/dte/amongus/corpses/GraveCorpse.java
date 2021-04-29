package dte.amongus.corpses;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import dte.amongus.corpses.basic.BasicCorpse;
import dte.amongus.corpses.basic.components.blocks.BlockChangeComponent;
import dte.amongus.games.players.AUGamePlayer;
import dte.amongus.internal.GamePlayerUtils;
import dte.amongus.player.visual.PlayerColor;
import dte.amongus.utils.blocks.BlockUtils;

public class GraveCorpse extends BasicCorpse
{
	public GraveCorpse(AUGamePlayer whoDied, Location deathLocation) 
	{
		super(whoDied);
		
		registerComponents(deathLocation);
	}
	
	private void registerComponents(Location deathLocation) 
	{
		Block spawnBlock = deathLocation.getBlock();
		
		//register the middle block to change to the body's color
		addComponent(new BlockChangeComponent(this, spawnBlock, getBodyMaterial()));
		
		//register the grave
		getGraveAround(spawnBlock).forEach(this::addComponent);
	}
	private Material getBodyMaterial()
	{
		PlayerColor deadColor = GamePlayerUtils.toAUPlayer(whoDied()).getVisibilityManager().getCurrentColor();
		
		return Material.valueOf(deadColor.name() + "_WOOL");
	}
	private List<BlockChangeComponent> getGraveAround(Block bodyBlock)
	{
		Block[] graveBlocks = BlockUtils.getFacedBlocks(bodyBlock, BlockUtils.CIRCLE_FACES).stream().toArray(Block[]::new);

		return IntStream.range(0, graveBlocks.length)
				.mapToObj(i -> new BlockChangeComponent(this, graveBlocks[i], i % 2 == 0 ? Material.BLACK_WOOL : Material.BEDROCK))
				.collect(toList());
	}
}