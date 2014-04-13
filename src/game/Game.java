package game;

import game.Board.TokenChangeListener;
import game.token.PlayerToken;

import java.awt.Color;
import java.util.List;

import ui.BoardPanel;
import ui.BoardPanel.SelectSquareListener;
import ui.playersDialog.PlayerInputData;

/**
 * The internal workings of the game.
 * 
 * @author littlewoo
 */
public class Game implements SelectSquareListener {
	private PlayerToken currentPlayer;
	private Board board;
	
	/**
	 * Construct a new game
	 * @param data 
	 */
	public Game(List<PlayerInputData> data) {
		System.out.println("Starting a new game with " + data.size() + " players:");
		currentPlayer = new PlayerToken("John", Color.ORANGE);
		
		for (PlayerInputData p : data) {
			System.out.println("\t" + p);
		}
		board = new Board();
	}

	public void placePlayerToken() {
		board.addPlayerToken(4,4,currentPlayer);
	}
	
	@Override
	public void onSelectSquare(int x, int y, int button) {
		if (button == BoardPanel.LEFT_MOUSE_BUTTON) {
			board.movePlayerToken(x, y, currentPlayer);
		} else {
			board.advanceFire(x, y);
		}
	}

	public void addTokenChangeListener(TokenChangeListener listener) {
		board.addTokenChangeListener(listener);
	} 
}
