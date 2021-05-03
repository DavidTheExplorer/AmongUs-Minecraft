package dte.amongus.cooldown;

import java.util.HashMap;
import java.util.Map;

public class CooldownService
{
	private final Map<String, Cooldown> cooldownByName = new HashMap<>();
	
	public Cooldown getCooldown(String cooldownName)
	{
		Cooldown cooldown = this.cooldownByName.get(cooldownName);
		
		if(cooldown == null) 
			throw new RuntimeException(String.format("Cooldown named '%s' wasn't found!", cooldownName));
		
		return cooldown;
	}
	public void register(Cooldown cooldown) 
	{
		this.cooldownByName.put(cooldown.getName(), cooldown);
	}
}