package mazgani.amongus.corpses.types;

import org.bukkit.Location;
import org.bukkit.block.Sign;

import mazgani.amongus.corpses.components.blocks.SignComponent;
import mazgani.amongus.games.AUGame;
import mazgani.amongus.games.GamePlayer;

public class GraveSignCorpse extends GraveCorpse
{
	public GraveSignCorpse(GamePlayer whoDied, AUGame game)
	{
		super(whoDied, game);
	}
	
	@Override
	public void initComponents(Location location) 
	{
		super.initComponents(location);
		
		Sign sign = (Sign) getWhoDied().getPlayer().getTargetBlock(null, 10).getState();
		
		addComponent(new SignComponent(sign, "Anna", ".", "...", "Banana!"));
	}
}