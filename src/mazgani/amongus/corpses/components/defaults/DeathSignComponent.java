package mazgani.amongus.corpses.components.defaults;

import org.bukkit.block.Sign;

import mazgani.amongus.corpses.components.blocks.SignComponent;

public class DeathSignComponent extends SignComponent
{
	public DeathSignComponent(Sign sign)
	{
		super(sign, new String[]{"Mizrahi", "died", "here."});
	}
}
