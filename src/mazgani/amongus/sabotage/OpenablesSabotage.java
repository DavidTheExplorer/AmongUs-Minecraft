package mazgani.amongus.sabotage;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;

public class OpenablesSabotage implements Sabotage
{
	protected final Location[] blocksLocations;

	public OpenablesSabotage(Block... openableBlocks)
	{
		verifyAllOpenable(openableBlocks);

		this.blocksLocations = Arrays.stream(openableBlocks)
				.map(Block::getLocation)
				.toArray(Location[]::new);
	}
	public Block[] getBlocks() 
	{
		return Arrays.stream(this.blocksLocations)
				.map(Location::getBlock)
				.toArray(Block[]::new);
	}

	@Override
	public void sabotage()
	{
		forOpenables(openable -> 
		{
			openable.setOpen(false);
		});
	}

	@Override
	public void unsabotage()
	{
		forOpenables(openable -> openable.setOpen(true));
	}

	protected void forOpenables(Consumer<Openable> action) 
	{
		Arrays.stream(this.blocksLocations)
		.map(location -> location.getBlock().getState())
		.forEach(state ->
		{
			Openable openable = (Openable) state.getBlockData();
			action.accept(openable);

			state.setBlockData(openable);
			state.update(true);
		});
	}
	private void verifyAllOpenable(Block[] blocks) 
	{
		String unOpenableMaterialsNames = Arrays.stream(blocks)
				.filter(block -> !(block.getState().getBlockData() instanceof Openable))
				.map(Block::getType)
				.map(Material::name)
				.collect(joining(", "));

		if(!unOpenableMaterialsNames.isEmpty()) 
		{
			throw new IllegalArgumentException("Blocks which aren't openable were registered for an Openables Sabotage: " + unOpenableMaterialsNames);
		}

		/*boolean allOpenable = Arrays.stream(blocks)
				.map(block -> block.getState().getBlockData())
				.allMatch(state -> state instanceof Openable);

		if(!allOpenable)
		{
			throw new IllegalArgumentException("At least 1 block that isn't openable was registered for an Openables Sabotage.");
		}*/
	}
}
