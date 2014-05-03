/**
 *  File name: TokenGridPanel.java
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
package ui;

import game.token.MovableToken;
import game.token.Token;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import ui.drawing.TokenPaintingManager;

/**
 * A panel for drawing tokens in a grid shape
 *
 * @author littlewoo
 */
public class TokenGridPanel extends JPanel {
	private static final long serialVersionUID = 7077907873052891633L;
	
	/** the height and width of the grid, in cells */
	private final int height;
	private final int width;
	
	/** the size of one of the squares on the grid, in pixels */
	private final int cellSize;
	/** the x value for the left margin */
	protected final int leftMargin;
	/** the x value for the right margin */
	protected final int rightMargin;
	/** the y value for the top margin */
	protected final int topMargin;
	/** the y value for the bottom margin */
	protected final int bottomMargin;
	
	/** the manager responsible for drawing tokens */
	private final TokenPaintingManager tokenPaintingManager;
	
	/**
	 * Create a new TokenGridPanel.
	 */
	public TokenGridPanel(int cellSize, int marginSize, int width, int height) {
		tokenPaintingManager = new TokenPaintingManager();
		this.cellSize = cellSize;
		this.width = width;
		this.height = height;
		leftMargin = marginSize;
		rightMargin = marginSize + cellSize * width;
		topMargin = marginSize;
		bottomMargin = marginSize + cellSize * height;
		setPreferredSize(new Dimension(rightMargin + marginSize, 
						      		   bottomMargin + marginSize));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		drawGridLines(g2);
		tokenPaintingManager.drawAll(g2);
	}
	
	/**
	 * Add a token to the grid
	 * 
	 * @param t the token
	 * @param p the grid reference of the token
	 */
	public void addToken(Token t, Point p) {
		Point loc = getCellLoc(p);
		tokenPaintingManager.addToken(t, loc.x, loc.y);
	}
	
	/**
	 * Remove a token from the grid
	 * 
	 * @param t the token to remove
	 */
	public void removeToken(Token t) {
		tokenPaintingManager.removeToken(t);
	}
	
	/**
	 * Update the grid reference of a movable token
	 * 
	 * @param token the movable token which is moving
	 * @param p the location it is moving to
	 */
	public void moveToken(MovableToken t, Point p) {
		Point loc = getCellLoc(p);
		tokenPaintingManager.updateTokenLocation(t, loc.x, loc.y);
	}

	/**
	 * Convert a cell reference (x,y) into a pixel coordinate for the center of
	 * the cell
	 * 
	 * @param p the location to check
	 * @return the centre location of a cell
	 */
	protected Point getCellLoc(Point p) {
		int xLoc = leftMargin + cellSize * p.x + cellSize / 2;
		int yLoc = topMargin + cellSize * p.y + cellSize / 2;
		return new Point(xLoc, yLoc);
	}
	
	/**
	 * Draw the grid lines on the panel
	 */
	private void drawGridLines(Graphics2D g) {
		g.setColor(this.getForeground());
		g.setStroke(new BasicStroke(1));
		
		for (int i=0; i<width; i++) {
			int x = leftMargin + i * cellSize;
			int y1 = topMargin;
			int y2 = bottomMargin;
			g.drawLine(x, y1, x, y2);
		}
		for (int j=0; j<height; j++) {
			int y = topMargin + j * cellSize;
			int x1 = leftMargin;
			int x2 = rightMargin;
			g.drawLine(x1, y, x2, y);
		}
		
		g.drawLine(rightMargin, topMargin, rightMargin, bottomMargin);
		g.drawLine(leftMargin, bottomMargin, rightMargin, bottomMargin);
	}
}
