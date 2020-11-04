package mazgani.amongus.games;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mazgani.amongus.enums.Role;

@RequiredArgsConstructor
public class GamePlayer
{
	@Getter
	private final Player player;
	
	@Getter
	private final AUGame game;
	
	@Getter
	@Setter
	private Role role;
	
	@Getter
	private boolean spectator = false;
	
	public void setSpectator() 
	{
		this.spectator = true;
		this.player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2));
		this.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 2, 2));
	}
}