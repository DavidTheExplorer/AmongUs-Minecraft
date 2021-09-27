package dte.amongus.lobby.service;

import static dte.amongus.utils.java.RandomUtils.randomElement;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import dte.amongus.lobby.AULobby;

import dte.amongus.player.AUPlayer;

public class AULobbyService
{
	private final Map<UUID, AULobby> lobbyByID = new HashMap<>();
	
	public void registerLobby(AULobby lobby)
	{
		this.lobbyByID.put(lobby.getID(), lobby);
	}
	
	public Optional<AULobby> getRandomLobby() 
	{
		return Optional.ofNullable(randomElement(getLobbies()));
	}
	
	public Optional<AULobby> getRandomLobbyThat(Predicate<AULobby> lobbyTester) //TODO: Use this for ignore lists - so people can join games without people they hate which could ruin their game
	{
		 Set<AULobby> matchingLobbies = getLobbies().stream()
				 .filter(lobbyTester)
				 .collect(toSet());
		 
		 return Optional.ofNullable(randomElement(matchingLobbies));
	}
	
	public Optional<AULobby> getPlayerLobby(AUPlayer auPlayer)
	{
		return getLobbies().stream()
				.filter(lobby -> lobby.contains(auPlayer))
				.findAny();
	}
	
	public Collection<AULobby> getLobbies()
	{
		return this.lobbyByID.values();
	}
}