/**
 *  File name: GenericPainter.java
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
 * A token painter for painting any kind of tokens. The resulting visual is a 
 * coloured circle filled with white, with a specified single letter symbol 
 * drawn in the centre, in a specified colour.
 *
 * @author littlewoo
 */
public class GenericTokenPainter extends TokenPainter {
	
	/** the diameter of the token, as a percentage of the cell size */
	private static int TOKEN_DIAMETER_PERCENTAGE = 66;
	
	/**
	 * the offset from the centre of the token for where the token's character
	 * label should be drawn
	 */
	private static int TOKEN_CHAR_X_OFFSET = -18;
	private static int TOKEN_CHAR_Y_OFFSET = 17;
	
	/** the font size of the token character label */
	private static int TOKEN_CHAR_FONT_SIZE = 60;
	/** the font of the token character label */
	private static Font TOKEN_CHAR_FONT = 
			new Font(Font.MONOSPACED, Font.BOLD, TOKEN_CHAR_FONT_SIZE);

	/** the symbol used as the token's character label, and its colour */
	private String symbol;
	private Color symbolColour;
	
	/**
	 * Make a new Generic Token Drawer.
	 * @param symbol
	 * @param symbolColour
	 */
	public GenericTokenPainter(String symbol, Color symbolColour) {
		this.symbol = symbol;
		this.symbolColour = symbolColour;
	}
	
	/** 
	 * Draw the token
	 * 
	 * @param g the graphics to draw the token on
	 */
	@Override
	public void draw(Graphics2D g) {
		super.drawCircleToken(g, TOKEN_DIAMETER_PERCENTAGE, Color.WHITE, Color.RED);
		drawCharacter(g);
	}
	
	/**
	 * Draw the character symbol on the token
	 * 
	 * @param g the graphics to draw on
	 */
	protected void drawCharacter(Graphics2D g) {
		if (symbol.length() != 1) {
			throw new IllegalArgumentException(
								"Token character length must be exactly 1.");
		}
		g.setColor(symbolColour);
		g.setFont(TOKEN_CHAR_FONT);
		g.drawString(symbol, x+TOKEN_CHAR_X_OFFSET, y+TOKEN_CHAR_Y_OFFSET);
	}

	/**
	 * @return the diameter of this token, in pixels
	 */
	@Override
	protected int getDiameter() {
		return TOKEN_DIAMETER_PERCENTAGE * CELL_SIZE / 100;
	}
}
