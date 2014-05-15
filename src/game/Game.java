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
import game.action.Action;
import game.action.ActionCollection;
import game.action.MoveAction;
import game.action.MoveIntoFireAction;
import game.action.MoveWithVictimAction;
import game.token.BlankPOIToken;
import game.token.POIFaceToken;
import game.token.POIQuestionMarkToken;
import game.token.POIToken;
import game.token.PlayerToken;
import game.token.VictimPOIToken;
import interfaces.APListener;
import interfaces.ActionPerformer;
import interfaces.ActionView;
import interfaces.DiceRollListener;
import interfaces.POIEventListener;
import interfaces.TurnPhaseListener;
import interfaces.TurnPhaseListener.TurnPhase;
import interfaces.TurnTaker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ui.playersDialog.PlayerInputData;

/**
 * The internal workings of the game.
 * 
 * @author littlewoo
 */
public class Game implements TurnTaker, ActionPerformer {
	/** the width of the game board */
	public static final int WIDTH = 10;
	/** the height of the game board */
	public static final int HEIGHT = 8;
	
	/** if this is true, newly placed POIs replace fire. If not, they cannot
	 *  be placed on fire. */
	private static final boolean POI_REPLACES_FIRE = true;
	/** the total number of POI tokens in the game */
	private static final int POI_VICTIM_COUNT = 10;
	/** the number of blank tokens for each victim token */
	private static final int POI_BLANK_COUNT = 5;
	/** the number of POI tokens on the board at the start of the game */
	private static final int INITIAL_POI_COUNT = 3;
	/** the manager for POI tokens */
	private final POITokenManager poiTokenManager;

	/** the index of the current player */
	private int currentPlayerIndex;
	/** all the players in the game */
	private List<Player> players;
	
	/** the board on which the action is happening */
	private Board board;
	/** the source of dice rolls */
	private DiceRoller diceRoller;
	
	/** listeners for turn phases */
	private List<TurnPhaseListener> turnPhaseListeners;
	
	/** action views, for displaying possible actions */
	private List<ActionView> actionViews;
	
	/**
	 * Construct a new game
	 * @param data the players to be introduced into the game
	 */
	public Game(List<PlayerInputData> data) {
		createPlayers(data);		
		board = new Board(WIDTH, HEIGHT);
		diceRoller = new DiceRoller();
		poiTokenManager = 
				new POITokenManager(POI_REPLACES_FIRE, POI_VICTIM_COUNT,
									POI_BLANK_COUNT, board, diceRoller);
		board.addPOIEventListener(poiTokenManager);
		addTurnPhaseListener(poiTokenManager);
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
		players = new ArrayList<Player>();
		for (PlayerInputData p : data) {
			addPlayer(p);
		}
		currentPlayerIndex = 0;
	}
	
	/**
	 * Add a new player to the game.
	 * 
	 * @param data the player input data for the player
	 * @return the new player object
	 */
	private Player addPlayer(PlayerInputData data) {
		PlayerToken token = new PlayerToken(data.name, data.colour);
		Player player = new Player(token);
		players.add(player);
		return player;
	}
	
	/**
	 * Place the player tokens on the board. 
	 */
	public void placePlayers() {
		for (Player p : players) {
			PlayerToken t = p.getToken();
			placePlayerTokenRandomly(t);
		}
		players.get(0).newTurn();
	}

	/**
	 * Place a player token on the board, randomly selecting its location
	 * 
	 * @param t the token to be placed
	 */
	private void placePlayerTokenRandomly(PlayerToken t) {
		boolean success = false;
		while (!success) {
			int x = diceRoller.rollDie(8, false).roll;
			int y = diceRoller.rollDie(6, false).roll;
			success = board.addPlayerToken(x, y, t);
		}
	}
	
	/**
	 * @return the player token of the current player
	 */
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIndex);
	}
	
	/**
	 * Place the POI tokens on the board
	 */
	public void placeInitialPOITokens() {
		poiTokenManager.placeInitialTokens(INITIAL_POI_COUNT);
	}
	/**
	 * Respond to a signal to end the current player's turn
	 */
	@Override
	public void onEndTurn() {
		board.checkRescuedVictims();
		advanceFire();
		currentPlayerIndex ++;
		currentPlayerIndex = currentPlayerIndex % players.size();
		getCurrentPlayer().newTurn();
		alertActionViews();
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
		alertTurnPhaseListeners(TurnPhase.PLACE_POI);
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
	 * Add a player APListener
	 * 
	 * @param listener
	 */
	public void addAPListener(APListener listener) {
		for (Player p : players) {
			p.addAPListener(listener);
		}
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
	
	/**
	 * add an action view to the game
	 * 
	 * @param view the actionView to be added
	 */
	public void addActionView(ActionView view) {
		if (actionViews == null) {
			actionViews = new ArrayList<ActionView>();
		}
		actionViews.add(view);
		alertActionViews();
	}
	
	/**
	 * Alert the action views with an updated set of actions
	 */
	public void alertActionViews() {
		ActionCollection actions = getActions();
		for (ActionView v : actionViews) {
			v.displayActions(actions);
		}
	}
	
	/**
	 * Add a POIEventListener
	 * 
	 * @param listener
	 */
	public void addPOIEventListener(POIEventListener listener) {
		board.addPOIEventListener(listener);
	}

	/** 
	 * Place the walls on the board
	 */
	public void placeWalls() {
		Walls w = new WallCreator().getWalls();
		board.addWalls(w);
	}

	/** 
	 * @return a list of actions 
	 */
	private ActionCollection getActions() {
		List<Action> result = new ArrayList<Action>();
		Player player = getCurrentPlayer();
		PlayerToken t = player.getToken();
		Set<Point> possMoves = board.getPossibleMoves(t);
		VictimPOIToken victim = board.getVictimAtPlayer(t);
		for (Point p : possMoves) {
			if (board.isFireAt(p)) {
				addAction(result, new MoveIntoFireAction(player, p));
			} else {
				addAction(result, new MoveAction(player, p));
				if (victim != null) {
					addAction(result, new MoveWithVictimAction(player, p, victim));
				}
			}
		}
		return new ActionCollection(result);
	}
	
	/**
	 * Check whether an action is valid, and if it is, add it to a list.
	 * 
	 * @param actionList the list of actions
	 * @param action the action to be added
	 */
	private void addAction(List<Action> actionList, Action action) {
		if (getCurrentPlayer().canPerformAction(action)) {
			actionList.add(action);
		}
	}
	
	/**
	 * Move a player to a new location
	 * 
	 * @param loc the new location
	 * @param p the player to move
	 */
	@Override
	public boolean movePlayer(Player p, Point loc) {
		board.movePlayerToken(loc.x, loc.y, p);
		POIToken t = board.getPOITokenAt(loc);
		if (t instanceof POIQuestionMarkToken) {
			POIFaceToken ft = board.flipPOIToken((POIQuestionMarkToken) t);
			if (ft instanceof BlankPOIToken) {
				board.rescueVictim(ft);
			}
		}
		return true;
	}
	
	/**
	 * Move a victim to a new location
	 * 
	 * @param victim the VictimPOIToken to move
	 * @param loc the location to move to
	 * @return true if the move was successful (i.e. fire and other victims 
	 * 				allowed it
	 */
	@Override
	public boolean moveVictimToken(VictimPOIToken victim, Point loc) {
		return board.movePOIToken(victim, loc);
	}

	/* (non-Javadoc)
	 * @see interfaces.ActionPerformer#performAction(game.Action)
	 */
	@Override
	public boolean performAction(Action action) {
		boolean val = action.performAction(this);
		alertActionViews();
		return val;
	}
}
