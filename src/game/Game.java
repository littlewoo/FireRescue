/**
 *  File name: Game.java
 *
 *  Copyright 2014: John Littlewood
 *
 *  This file is part of FireRescue.
 *
 *  FireRescue is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FireRescue is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FireRescue.  If not, see <http://www.gnu.org/licenses/>.
 */
package game;

import game.Board.TokenChangeListener;
import game.DiceRoller.DieResult;
import game.token.PlayerToken;
import interfaces.DiceRollListener;
import interfaces.TurnPhaseListener;
import interfaces.TurnPhaseListener.TurnPhase;
import interfaces.TurnTaker;

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
public class Game implements SelectSquareListener, TurnTaker {
	/** the width of the game board */
	public static final int WIDTH = 10;
	/** the height of the game board */
	public static final int HEIGHT = 8;

	/** the index of the current player */
	private int currentPlayerIndex;
	/** all the players in the game */
	private List<PlayerToken> players;
	
	/** the board on which the action is happening */
	private Board board;
	/** the source of dice rolls */
	private DiceRoller diceRoller;
	
	/** listeners for turn phases */
	private List<TurnPhaseListener> turnPhaseListeners;
	
	/**
	 * Construct a new game
	 * @param data the players to be introduced into the game
	 */
	public Game(List<PlayerInputData> data) {
		createPlayers(data);		
		board = new Board();
		Walls w = new WallCreator().getWalls();
		board.addWalls(w);
		diceRoller = new DiceRoller();
	}
	
	/**
	 * Create the player tokens from the input data. The first player is
	 * selected, as the first in the input list.
	 * 
	 * @param data the player input data
	 */
	private void createPlayers(List<PlayerInputData> data) {
		if (data.size() <= 0) {
			throw new IllegalArgumentException("Cannot start a game with " + 
												data.size() + " players!");
		}
		players = new ArrayList<PlayerToken>();
		for (PlayerInputData p : data) {
			addPlayer(p);
		}
		currentPlayerIndex = 0;
	}
	
	/**
	 * Add a new player to the game.
	 * 
	 * @param data the player input data for the player
	 * @return a player token representing the player
	 */
	private PlayerToken addPlayer(PlayerInputData data) {
		PlayerToken player = new PlayerToken(data.name, data.colour);
		players.add(player);
		return player;
	}
	
	/**
	 * Place the player tokens on the board. 
	 */
	public void placePlayers() {
		for (PlayerToken t : players) {
			placePlayerTokenRandomly(t);
		}
	}

	/**
	 * Place a player token on the board, randomly selecting its location
	 * 
	 * @param t the token to be placed
	 */
	private void placePlayerTokenRandomly(PlayerToken t) {
		Random rand = new Random(System.currentTimeMillis());
		boolean success = false;
		while (!success) {
			int x = rand.nextInt(WIDTH);
			int y = rand.nextInt(HEIGHT);
			success = board.addPlayerToken(x, y, t);
		}
	}
	
	/**
	 * Respond to a square being selected
	 * 
	 * @param x the x coordinate of the selected square
	 * @param y the y coordinate of the selected square
	 * @param button which mouse button was used in making the selection
	 * 
	 * TODO: Remove the button stuff - either refactor as 'alternate selection'
	 * or something, or remove it entirely.
	 */
	@Override
	public void onSelectSquare(int x, int y, int button) {
		if (button == BoardPanel.LEFT_MOUSE_BUTTON) {
			board.movePlayerToken(x, y, getCurrentPlayer());
		} else {
			board.advanceFire(x, y);
		}
	}
	
	/**
	 * @return the player token of the current player
	 */
	public PlayerToken getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}

	/**
	 * Respond to a signal to end the current player's turn
	 */
	@Override
	public void onEndTurn() {
		advanceFire();
		currentPlayerIndex ++;
		currentPlayerIndex = currentPlayerIndex % players.size();
	} 
	
	/** 
	 * Perform the advance fire stage of a turn. Rolls the dice, and advances
	 * fire on the indicated space.
	 * 
	 * @see Board#advanceFire(int, int)
	 */
	private void advanceFire() {
		alertTurnPhaseListeners(TurnPhase.ADVANCE_FIRE);
		int x = rollDie(8);
		int y = rollDie(6);
		board.advanceFire(x, y);
		alertTurnPhaseListeners(TurnPhase.SMOKE_TO_FIRE);
		board.smokeIntoFire();
		alertTurnPhaseListeners(TurnPhase.CLEAR_EDGE_FIRE);
		board.removeFireFromEdges();
		alertTurnPhaseListeners(TurnPhase.MOVE);
	}

	/**
	 * Roll a die.
	 * 
	 * @param sides the number of sides on the die to be rolled
	 * @return the result of the roll
	 */
	private int rollDie(int sides) {
		DieResult r = diceRoller.rollDie(sides, true);
		return r.roll;
	}

	/** 
	 * Add a listener to listen for changes to tokens
	 * 
	 * @param listener
	 */
	public void addTokenChangeListener(TokenChangeListener listener) {
		board.addTokenChangeListener(listener);
	}
	
	/**
	 * Add a dice roll listener
	 * 
	 * @param listener the listener
	 */
	public void addDiceRollListener(DiceRollListener listener) {
		diceRoller.addDiceRollListener(listener);
	}
	
	/**
	 * Add a turn phase listener
	 * 
	 * @param listener
	 */
	public void addTurnPhaseListener(TurnPhaseListener listener) {
		if (turnPhaseListeners == null) {
			turnPhaseListeners = new ArrayList<TurnPhaseListener>();
		}
		turnPhaseListeners.add(listener);
	}
	
	/**
	 * Alert turn phase listeners of a change in turn phase
	 * 
	 * @param phase the new turn phase
	 */
	private void alertTurnPhaseListeners(TurnPhase phase) {
		if (turnPhaseListeners != null) {
			for (TurnPhaseListener l : turnPhaseListeners) {
				l.onTurnPhaseChange(phase);
			}
		}
	}
	
}
