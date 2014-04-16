package interfaces;

import game.token.PlayerToken;

public interface TurnTaker {
	public void onEndTurn();
	
	public PlayerToken getCurrentPlayer();
}