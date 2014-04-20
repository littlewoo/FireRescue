/**
 *  File name: ThreatTokenPainter.java
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
import java.awt.Graphics2D;

/**
 * Painter for painting threat tokens
 *
 * @author littlewoo
 */
public abstract class ThreatTokenPainter extends GenericTokenPainter {

	/** 
	 * the diameter of the token, expressed as a percentage of the size of
	 * the square it is in
	 */
	private static final int DIAMETER_PERCENTAGE = 70;

	/**
	 * create a new threat token painter
	 * 
	 * @param symbol the symbol of the threat
	 * @param symbolColour the colour of the symbol
	 */
	public ThreatTokenPainter(String symbol, Color symbolColour) {
		super(symbol, symbolColour);
	}

	/**
	 * draw the token
	 * 
	 * @param g the graphics to draw on
	 */
	@Override
	public void draw(Graphics2D g) {
		drawCircleToken(g, DIAMETER_PERCENTAGE, Color.BLACK, Color.RED);
		drawCharacter(g);
	}

	/**
	 * @return the diameter of the token, in pixels
	 */
	@Override
	public int getDiameter() {
		return DIAMETER_PERCENTAGE * CELL_SIZE / 100;
	}

}