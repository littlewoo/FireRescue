/**
 *  File name: TokenPainter.java
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ui.BoardPanel;

/**
 * Class for painting tokens onto the board
 *
 * @author littlewoo
 */
public abstract class TokenPainter {
	/** the size of one square on the board, in pixels */
	protected static final int CELL_SIZE = BoardPanel.CELL_SIZE;
	
	/** The font used to label the token */
	protected static final Font tokenLabelFont = 
			new Font(Font.MONOSPACED, Font.BOLD, 50);
	
	/** the location of the token to be drawn */
	protected int x;
	protected int y;
		
	/**
	 * Update the location of this token
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void updateLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
		
	/**
	 * Draw a circular background of a token
	 * 
	 * @param g the graphics to draw on
	 * @param x the x location of the centre of the token
	 * @param y the y location of the centre of the token
	 * @param diameterPct the diameter of the circle, as a percentage of the 
	 * 						size of the cell
	 * @param bgColour the background colour of the circle
	 * @param borderColour the colour of the border of the circle
	 */
	protected void drawCircleToken(Graphics2D g, int diameterPct,
								   Color bgColour, Color borderColour) {
		int diameter = CELL_SIZE * diameterPct / 100;
		int cLeft = x - diameter/2;
		int cTop = y - diameter/2;
		
		g.setColor(bgColour);
		g.fillOval(cLeft, cTop, diameter, diameter);
		g.setStroke(new BasicStroke(10));
		g.setColor(borderColour);
		g.drawOval(cLeft, cTop, diameter, diameter);
	}

	/** 
	 * Paint the token
	 * 
	 * @param g the graphics to paint on
	 */
	public abstract void draw(Graphics2D g);
	
	/**
	 * @return the diameter of the token, expressed as a percentage of the size
	 * 			of the cell.
	 */
	protected abstract int getDiameter();
}
