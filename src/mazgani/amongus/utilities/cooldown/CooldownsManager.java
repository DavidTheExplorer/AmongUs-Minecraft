package mazgani.amongus.utilities.cooldown;

import java.util.HashMap;
import java.util.Map;

public class CooldownsManager
{
	private Map<String, Cooldown> cooldownByName = new HashMap<>();
	
	public Cooldown getCooldown(String cooldownName)
	{
		Cooldown cooldown = this.cooldownByName.get(cooldownName);
		
		if(cooldown != null) 
		{
			return cooldown;
		}
		throw new RuntimeException(String.format("Cooldown named '%s' wasn't found!", cooldownName));
	}
	public void registerCooldown(Cooldown cooldown) 
	{
		this.cooldownByName.put(cooldown.getName(), cooldown);
	}
}