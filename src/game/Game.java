package game;

import java.util.List;

import ui.playersDialog.PlayerInputData;

/**
 * The internal workings of the game.
 * 
 * @author littlewoo
 */
public class Game {
	
	private Board board;
	
	/**
	 * Construct a new game
	 * @param data 
	 */
	public Game(List<PlayerInputData> data) {
		System.out.println("Starting a new game with " + data.size() + " players:");
		for (PlayerInputData p : data) {
			System.out.println("\t" + p);
		}
		board = new Board();
	}

	public Board getBoard() {
		return board;
	}
}
