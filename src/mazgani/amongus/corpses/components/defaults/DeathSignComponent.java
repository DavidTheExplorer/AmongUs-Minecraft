package mazgani.amongus.corpses.components.defaults;

import org.bukkit.Material;
import org.bukkit.block.Block;

import mazgani.amongus.corpses.AbstractGameCorpse;
import mazgani.amongus.corpses.components.blocks.SignComponent;

public class DeathSignComponent extends SignComponent
{
	public DeathSignComponent(AbstractGameCorpse corpse, Block block, Material signMaterial, String deadPlayerName)
	{
		super(corpse, block, signMaterial, deadPlayerName, "died", "here.");
	}
}
