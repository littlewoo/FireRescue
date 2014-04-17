/**
 *  File name: PlayerInputData.java
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
package ui.playersDialog;

import java.awt.Color;

/**
 * Data structure for holding data on a player. 
 * 
 * @author littlewoo
 */
public class PlayerInputData {
	public final String name;
	public final Color colour;
	
	/**
	 * 
	 * @param name the player's name
	 * @param colour the player's colour
	 */
	public PlayerInputData(String name, Color colour) {
		this.name = name;
		this.colour = colour;
	}
	
	/**
	 * Express this player as a string.
	 */
	public String toString() {
		return name + ": " + colour;
	}
}
