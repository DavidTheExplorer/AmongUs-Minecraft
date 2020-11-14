package mazgani.amongus.corpses.types;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.blocks.BlockChangeComponent;
import mazgani.amongus.corpses.components.blocks.BlockComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.utilities.BlockUtilities;

public class GraveCorpse extends BasicGameCorpse
{
	//private Location[] graveBlocksLocations;

	public GraveCorpse(GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
	}

	@Override
	public void initComponents(Location location) 
	{
		Block block = location.getBlock();
		
		//register the middle block to change to the body's color
		addComponent(new BlockChangeComponent(block, getBodyMaterial()));
		
		//register the grave blocks
		getGraveAround(block).forEach(this::addComponent);
	}
	
	@Override
	public Location computeBestLocation(Location deathLocation) 
	{
		Location bestLocation = deathLocation;
		
		//spawn the grave under the player
		bestLocation = bestLocation.subtract(0, 1, 0);
		
		//move the location if it's next to a wall
		
		return bestLocation;
	}
	private Material getBodyMaterial() 
	{
		String deadColorName = getWhoDied().getColor().name();
		
		return Material.valueOf(deadColorName + "_WOOL");
	}
	private List<BlockComponent> getGraveAround(Block bodyBlock)
	{
		Block[] graveBlocks = BlockUtilities.getSurroundingBlocks(bodyBlock);
		
		return IntStream.range(0, graveBlocks.length)
				.mapToObj(i -> new BlockChangeComponent(graveBlocks[i], getGraveBlockMaterial(i)))
				.collect(toList());
	}
	private Material getGraveBlockMaterial(int blockIndex) 
	{
		return blockIndex % 2 == 0 ? Material.BLACK_WOOL : Material.BEDROCK;
	}
}