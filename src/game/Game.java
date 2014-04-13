package game;

import game.Board.TokenChangeListener;
import game.token.PlayerToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ui.BoardPanel;
import ui.BoardPanel.SelectSquareListener;
import ui.playersDialog.PlayerInputData;

/**
 * The internal workings of the game.
 * 
 * @author littlewoo
 */
public class Game implements SelectSquareListener {
	public static final int WIDTH = 10;
	public static final int HEIGHT = 8;

	private PlayerToken currentPlayer;
	
	private List<PlayerToken> players;
	private Board board;
	
	/**
	 * Construct a new game
	 * @param data 
	 */
	public Game(List<PlayerInputData> data) {
		createPlayers(data);		
		board = new Board();
	}
	
	private void createPlayers(List<PlayerInputData> data) {
		if (data.size() <= 0) {
			throw new IllegalArgumentException("Cannot start a game with " + 
												data.size() + " players!");
		}
		players = new ArrayList<PlayerToken>();
		for (PlayerInputData p : data) {
			addPlayer(p);
		}
		currentPlayer = players.get(0);
	}
	
	private PlayerToken addPlayer(PlayerInputData data) {
		PlayerToken player = new PlayerToken(data.name, data.colour);
		players.add(player);
		return player;
	}
	
	public void placePlayers() {
		for (PlayerToken t : players) {
			placePlayerTokenRandomly(t);
		}
	}

	private void placePlayerTokenRandomly(PlayerToken t) {
		Random rand = new Random(System.currentTimeMillis());
		boolean success = false;
		while (!success) {
			int x = rand.nextInt(WIDTH);
			int y = rand.nextInt(HEIGHT);
			success = board.addPlayerToken(x, y, t);
		}
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
