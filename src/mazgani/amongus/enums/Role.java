package mazgani.amongus.enums;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.WordUtils;

import lombok.Getter;

public enum Role 
{
	CREWMATE,
	IMPOSTOR;

	@Getter
	private final String name;

	Role()
	{
		this.name = WordUtils.capitalizeFully(name().toLowerCase());
	}
	public static Role getRandomRole() 
	{
		return ThreadLocalRandom.current().nextBoolean() ? CREWMATE : IMPOSTOR;
	}
}