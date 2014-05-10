/**
 *  File name: POITokenManager.java
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

import game.token.POIQuestionMarkToken;
import game.token.POIToken;
import game.token.POIToken.POIFaceType;
import interfaces.ListShuffler;
import interfaces.POIEventListener;
import interfaces.TurnPhaseListener;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author littlewoo
 */
public class POITokenManager implements POIEventListener, TurnPhaseListener {	
	private final boolean poiReplacesFire;
	
	/** the number of POI tokens removed from the board this time */
	private int poiTokensRemovedThisTurn;
	
	/** the board whose POITokens are being managed */
	private final Board board;
	
	/** the list of POI tokens in this game */
	private List<POIToken> stack;
	
	/** the index of the next POI to be placed */
	private int nextPOI;
	
	/** a dice roller for placing POIs and shuffling the stack */
	private DiceRoller roller;
	
	/**
	 * Make a new POITokenManager. Includes creating the stack of POI tokens for
	 * play.
	 * 
	 * @param poiReplacesFire true if POI tokens rolling a square with fire in 
	 * 						  it replace the fire, false if they are placed 
	 * 						  elsewhere
	 * @param victims the number of victim tokens in the stack
	 * @param blanks the number of blank POI tokens to generate in the stack 
	 * @param initialPOIs the number of initial POI tokens placed on the board
	 * @param board the board the manager is managing POI tokens for
	 * @param roller a dice roller for rolling the dice to place POIs and 
	 * 				 shuffling the stack of POI tokens.
	 */
	public POITokenManager(boolean poiReplacesFire, int victims, int blanks, 
						   Board board, DiceRoller roller) {
		poiTokensRemovedThisTurn = 0;
		this.poiReplacesFire = poiReplacesFire;
		this.board = board;
		this.roller = roller;
		stack = getTokenStack(victims, blanks, roller);
	}

	/* (non-Javadoc)
	 * @see interfaces.POIEventListener#onPOIEvent(interfaces.POIEventListener.POIEvent)
	 */
	@Override
	public void onPOIEvent(POIEvent e) {
		switch (e.type) {
			case KILLED:
			case RESCUED:
				poiTokensRemovedThisTurn ++;
				break;
			case PLACED:
			default:
				break;
		}
	}

	/* (non-Javadoc)
	 * @see interfaces.TurnPhaseListener#onTurnPhaseChange(interfaces.TurnPhaseListener.TurnPhase)
	 */
	@Override
	public void onTurnPhaseChange(TurnPhase phase) {
		if (phase == TurnPhase.PLACE_POI) {
			for (int i=0; i<poiTokensRemovedThisTurn; i++) {
				placeNextTokenRandomly();
			}
			poiTokensRemovedThisTurn = 0;
		}
	}

	/** 
	 * Place the next token on the board, in a random location.
	 */
	private void placeNextTokenRandomly() {
		if (nextPOI < stack.size()) {
			POIToken pt = stack.get(nextPOI);
			int x = roller.rollDie(8, false).roll;
			int y = roller.rollDie(6, false).roll;
			board.addPOIToken(new Point(x, y), pt, poiReplacesFire);
			nextPOI ++;
		}
	}

	/**
	 * Create all the POI tokens for use in the game. 
	 * 
	 * @param count the number of POI tokens to generate
	 * @param ratio the number of blank POI tokens to generate for each victim
	 * @return the list of POI tokens, in shuffled order.
	 */
	public List<POIToken> getTokenStack(int victims, int blanks,
												  ListShuffler shuffler) {
		List<POIToken> result = new ArrayList<POIToken>();
		for (int i=0; i<blanks; i++) {
			result.add(new POIQuestionMarkToken(POIFaceType.BLANK));
		}
		for (int i=0; i<victims; i++) {
			result.add(new POIQuestionMarkToken(POIFaceType.VICTIM));
		}
		result = shuffler.shuffle(result);
		return result;
	}

	/** 
	 * Place the initial POI tokens on the board
	 */
	public void placeInitialTokens(int initialPOIs) {
		for (int i=0; i<initialPOIs; i++) {
			placeNextTokenRandomly();
		}
	}
}
