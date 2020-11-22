package mazgani.amongus.corpses.types;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import mazgani.amongus.corpses.components.GameCorpseComponent;
import mazgani.amongus.corpses.components.blocks.BlockChangeComponent;
import mazgani.amongus.corpses.components.blocks.BlockComponent;
import mazgani.amongus.corpses.types.specials.CompositeCorpse;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;
import mazgani.amongus.utilities.CollectionUtilities;

public class WoolsCompositeCorpse extends CompositeCorpse
{
	public WoolsCompositeCorpse(GamePlayer whoDied, AUGame game)
	{
		super(whoDied, game);
	}
	
	@Override
	public Set<GameCorpseComponent> handleDuplicatedComponents() 
	{
		List<GameCorpseComponent> duplicates = CollectionUtilities.findAllDuplicates(getComponentsView());
		
		return duplicates.stream()
				.filter(component -> component instanceof BlockComponent)
				.map(component -> (BlockComponent) component)
				.map(blockComponent -> new BlockChangeComponent(this, blockComponent.getBlock(), Material.OBSIDIAN))
				.collect(toSet());
	}
}
