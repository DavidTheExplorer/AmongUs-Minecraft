package mazgani.amongus.corpses.types;

import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.blocks.BlockChangeComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.utilities.BlockUtilities;

public class WoolCorpse extends BasicGameCorpse
{
	private final Material woolColor;
	
	public WoolCorpse(Material woolColor, GamePlayer whoDied, AUGame game) 
	{
		super(whoDied, game);
		
		this.woolColor = woolColor;
	}

	@Override
	public void initComponents(Location location) 
	{
		List<Block> blocks = BlockUtilities.getFacedBlocks(location.getBlock(), BlockUtilities.SURROUNDING_FACES);
		Collections.shuffle(blocks);
		
		List<Block> half = blocks.subList(0, blocks.size()/2);
		
		for(Block block : half)
		{
			addComponent(new BlockChangeComponent(this, block, this.woolColor));
		}
	}
	
	@Override
	public Location computeBestLocation(Location deathLocation) 
	{
		return deathLocation.subtract(0, 1, 0);
	}
}