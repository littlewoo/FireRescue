/**
 *  File name: PlayerTokenPainter.java
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
package ui.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * A painter for painting player tokens. Consists of a circle of the player's 
 * colour, filled in white, with a 'P' (for player) symbol, in the same colour, 
 * and the player's name written in small lettering in black.
 *
 * @author littlewoo
 */
public class PlayerTokenPainter extends TokenPainter {
	
	/** the diameter of the token, as a percentage of the cell size */
	private static int TOKEN_DIAMETER_PERCENTAGE = 66;
	
	/**
	 * the offset from the centre of the token for where the token's character
	 * label should be drawn
	 */
	private static int TOKEN_CHAR_X_OFFSET = -14;
	private static int TOKEN_CHAR_Y_OFFSET = 14;
	
	/** the font size of the token character label */
	private static int TOKEN_CHAR_FONT_SIZE = 48;
	/** the font of the token character label */
	private static Font TOKEN_CHAR_FONT = 
			new Font(Font.MONOSPACED, Font.BOLD, TOKEN_CHAR_FONT_SIZE);
	
	/** the font size and font of the token player name */
	private static int PLAYER_FONT_SIZE = 10;
	private static Font PLAYER_FONT = 
			new Font(Font.SANS_SERIF, Font.BOLD, PLAYER_FONT_SIZE);
	
	/** 
	 * the offset from the centre of the token for where the token's player name
	 * should be drawn
	 */
	private static final int PLAYER_X_OFFSET = -14;
	private static final int PLAYER_Y_OFFSET = 22;

	/** the symbol used as the token's character label, and its colour */
	private final static String symbol = "P";
	
	/** The name and colour of the player attached to this token */
	private String name;
	private Color colour;

	/**
	 * Construct a new player token painter
	 * 
	 * @param playerName
	 * @param colour
	 */
	public PlayerTokenPainter(String playerName, Color colour) {
		this.name = playerName;
		this.colour = colour;
	}
	
	/**
	 * Draw the token
	 * 
	 * @param g the graphics to be drawn on
	 */
	@Override
	public void draw(Graphics2D g) {
		drawCircleToken(g, TOKEN_DIAMETER_PERCENTAGE, Color.WHITE, colour);
		g.setColor(colour);
		g.setFont(TOKEN_CHAR_FONT);
		g.drawString(symbol, x + TOKEN_CHAR_X_OFFSET, y + TOKEN_CHAR_Y_OFFSET);
		
		g.setColor(Color.BLACK);
		g.setFont(PLAYER_FONT);
		g.drawString(name, x+PLAYER_X_OFFSET, y+PLAYER_Y_OFFSET);
		
	}

	/**
	 * @return the diameter of the token, in pixels
	 */
	@Override
	protected int getDiameter() {
		return TOKEN_DIAMETER_PERCENTAGE;
	}

}
