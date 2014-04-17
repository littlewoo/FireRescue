/**
 *  File name: TurnTaker.java
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

import game.token.PlayerToken;

/**
 * Interface for objects involving turns. Has methods for receiving a signal to 
 * end the current turn, and for sending the current player.
 *
 * @author littlewoo
 */
public interface TurnTaker {
	/**
	 * Receive the signal that the current turn should finish
	 */
	public void onEndTurn();
	
	/**
	 * 
	 * @return the token representing the current player
	 */
	public PlayerToken getCurrentPlayer();
}