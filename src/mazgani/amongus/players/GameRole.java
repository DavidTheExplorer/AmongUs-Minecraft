package mazgani.amongus.players;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.WordUtils;

public enum GameRole 
{
	CREWMATE,
	IMPOSTOR;
	
	private final String name;

	GameRole()
	{
		this.name = WordUtils.capitalizeFully(name().toLowerCase());
	}
	public String getName() 
	{
		return this.name;
	}
	public static GameRole getRandomRole() 
	{
		return ThreadLocalRandom.current().nextBoolean() ? CREWMATE : IMPOSTOR;
	}
}