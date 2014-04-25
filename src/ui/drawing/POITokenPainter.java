/**
 *  File name: POITokenPainter.java
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

/**
 * Painter for painting a point of interest token.
 *
 * @author littlewoo
 */
public class POITokenPainter extends GenericTokenPainter {
	
	/** the diameter of the token as a percentage of the cell size */
	private static final int DIAMETER_PERCENTAGE = 45;
	
	/** the colours for painting a POI token */
	private static final Color SYMBOL_COLOUR = Color.WHITE;
	private static final Color BG_COLOUR = new Color(100, 170, 255);
	private static final Color BORDER_COLOUR = new Color(100, 170, 255);

	/** 
	 * Make a new POI token painter
	 * 
	 * @param symbol the symbol of the token
	 */
	public POITokenPainter(String symbol) {
		super(symbol, DIAMETER_PERCENTAGE, 
				SYMBOL_COLOUR, BG_COLOUR, BORDER_COLOUR);
	}

}
