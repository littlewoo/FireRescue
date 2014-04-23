/**
 *  File name: Player.java
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

import game.token.PlayerToken;
import interfaces.APListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player.
 *
 * @author littlewoo
 */
public class Player {
	/** the player's token */
	private final PlayerToken token;
	
	/** the current number of AP the player has */
	private int ap;
	
	/** the number of AP the player gets at the beginning of each turn */
	private static final int AP_EACH_TURN = 4;
	
	/** the listeners for changes in AP */
	private List<APListener> apListeners;
	
	/**
	 * Construct a new player.
	 * 
	 * @param t the player's token.
	 */
	public Player(PlayerToken t) {
		token = t;
		ap = 0;
	}
	
	/**
	 * Have the player perform an action, and record the associated AP loss.
	 * @param action TODO
	 * 
	 * @return true if the action was successful
	 */
	public boolean performAction(Action action) {
		if (! canPerformAction(action)) {
			return false;
		}
		ap -= action.getApCost();
		alertAPListeners();
		return true;
	}
	
	/**
	 * Register the beginning of a new turn with the player.
	 */
	public void newTurn() {
		ap += AP_EACH_TURN;
		alertAPListeners();
	}
	
	
	/**
	 * @return the token
	 */
	public PlayerToken getToken() {
		return token;
	}

	/**
	 * @return the ap
	 */
	public int getAP() {
		return ap;
	}
	
	/**
	 * Add a listener for AP changes
	 * 
	 * @param listener
	 */
	public void addAPListener(APListener listener) {
		if (apListeners == null) {
			apListeners = new ArrayList<APListener>();
		}
		apListeners.add(listener);
	}
	
	/**
	 * Alert the listeners that the number of AP has changed
	 */
	private void alertAPListeners() {
		for (APListener al : apListeners) {
			al.onAPChange(token, ap);
		}
	}

	/** 
	 * Check whether the player has sufficient AP remaining to perform an 
	 * action.
	 * 
	 * @param a the action to be checked
	 * @return true if the action is performable
	 */
	public boolean canPerformAction(Action a) {
		return a.getApCost() <= getAP();
	}
}
