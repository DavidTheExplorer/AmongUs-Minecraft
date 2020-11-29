package mazgani.amongus.games;

import mazgani.amongus.corpses.BasicGameCorpse;
import mazgani.amongus.players.AUPlayer;
import mazgani.amongus.players.GameRole;

public class GamePlayer
{
	private final AUPlayer auPlayer;
	private final AUGame game;
	
	private GameRole role;
	private boolean spectator = false;
	private BasicGameCorpse corpse;
	
	public GamePlayer(AUPlayer auPlayer, AUGame game) 
	{
		this.auPlayer = auPlayer;
		this.game = game;
	}
	public AUPlayer getAUPlayer() 
	{
		return this.auPlayer;
	}
	public AUGame getGame() 
	{
		return this.game;
	}
	public GameRole getRole() 
	{
		return this.role;
	}
	public boolean isSpectator() 
	{
		return this.spectator;
	}
	public void setRole(GameRole role)
	{
		verifyInInit("The players' roles can only be determined at the init of the game.");
		
		this.role = role;
	}
	public void setSpectator() 
	{
		this.spectator = true;
	}
	public BasicGameCorpse getCorpse() 
	{
		return this.corpse;
	}
	public void setCorpse(BasicGameCorpse corpse) 
	{
		this.corpse = corpse;
	}
	
	private void verifyInInit(String errorMessage) 
	{
		if(this.game.getState() != GameState.INIT)
		{
			throw new UnsupportedOperationException(errorMessage);
		}
	}
}