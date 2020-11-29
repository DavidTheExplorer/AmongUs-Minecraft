package mazgani.amongus.corpses.components.blocks;

import static mazgani.amongus.utilities.SignUtilities.LINES_AMOUNT;

import java.util.function.Function;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.utilities.SignUtilities;

public class SignComponent extends BlockChangeComponent
{
	private final String[] newLines;
	
	private Sign spawnedSign;

	public SignComponent(BasicGameCorpse corpse, Block block, Material signMaterial, String... newLines)
	{
		super(corpse, block, signMaterial);
		
		this.newLines = SignUtilities.fixLinesIfNeeded(newLines);
	}
	public String[] getNewLines() 
	{
		return this.newLines;
	}

	@Override
	public void spawn()
	{
		super.spawn();
		
		this.spawnedSign = (Sign) getBlock().getState();
		
		replaceLineByIndex(i -> this.newLines[i]);
	}

	protected void replaceLineByIndex(Function<Integer, String> lineMapper) 
	{
		for(int i = 0; i < LINES_AMOUNT; i++) 
		{
			this.spawnedSign.setLine(i, lineMapper.apply(i));
		}
		this.spawnedSign.update(true);
	}
}