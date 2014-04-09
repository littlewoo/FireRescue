package game;

/**
 * The internal workings of the game.
 * 
 * @author littlewoo
 */
public class Game {
	
	private Board board;
	
	/**
	 * Construct a new game
	 */
	public Game() {
		board = new Board();
	}

	public Board getBoard() {
		return board;
	}
}
