/**
 *  File name: APListener.java
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
 * A listener for taking events where a player's AP changes.
 * 
 * @author littlewoo
 */
public interface APListener {
	
	/**
	 * Called when a player's number of AP changes.
	 * 
	 * @param player the player affected
	 * @param ap the number of AP remaining
	 */
	public void onAPChange(PlayerToken player, int ap);

}
