/**
 *  File name: ActionPerformer.java
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
package interfaces;

import game.Player;
import game.action.Action;
import game.token.VictimPOIToken;

import java.awt.Point;

/**
 * Provides actions for square selections, and performs a selected action.
 *
 * @author littlewoo
 */
public interface ActionPerformer {
	
	/**
	 * Perform an action
	 *
	 * @param x the x coordinate targeted by the action
	 * @param y the y coordinate targeted by the action
	 * @param the action to perform
	 * @return true if the action was successfully performed
	 */
	public boolean performAction(Action action);
	
	/**
	 * Move a player
	 * 
	 * @param p the player to move
	 * @param loc the location to move to
	 * @return true if the action was successfully performed
	 */
	public boolean movePlayer(Player p, Point loc);
	
	/**
	 * Move a victim token
	 * 
	 * @param victim the token to move
	 * @param loc the location to move to
	 * @return true if the action was successfully performed
	 */
	public boolean moveVictimToken(VictimPOIToken victim, Point loc);
}
