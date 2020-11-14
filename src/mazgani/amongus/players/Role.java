package mazgani.amongus.players;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.WordUtils;

public enum Role 
{
	CREWMATE,
	IMPOSTOR;
	
	private final String name;

	Role()
	{
		this.name = WordUtils.capitalizeFully(name().toLowerCase());
	}
	public String getName() 
	{
		return this.name;
	}
	public static Role getRandomRole() 
	{
		return ThreadLocalRandom.current().nextBoolean() ? CREWMATE : IMPOSTOR;
	}
}