/**
 *  File name: PlayerToken.java
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
package game.token;


import java.awt.Color;

import ui.drawing.PlayerTokenPainter;
import ui.drawing.TokenPainter;

/**
 * A token for players in the game.
 *
 * @author littlewoo
 */
public class PlayerToken implements MovableToken {
	/** the name of the player */
	private final String name;
	/** the colour of the token representing the player */
	private final Color colour;
	
	/**
	 * Create a new PlayerToken.
	 * 
	 * @param name the name of the player
	 * @param colour the player's colour
	 */
	public PlayerToken(String name, Color colour) {
		this.name = name;
		this.colour = colour;
	}
	
	/** 
	 * @return the colour of this token
	 */
	public Color getColour() {
		return colour;
	}
	
	/**
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the painter for this token
	 */
	@Override
	public TokenPainter getPainter() {
		return new PlayerTokenPainter(name, colour);
	}

}
