package mazgani.amongus.corpses.components.premade;

import org.bukkit.Material;
import org.bukkit.block.Block;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.corpses.components.blocks.SignComponent;

public class DeathSignComponent extends SignComponent
{
	public DeathSignComponent(BasicGameCorpse corpse, Block block, Material signMaterial, String deadPlayerName)
	{
		super(corpse, block, signMaterial, deadPlayerName, "died", "here.");
	}
}