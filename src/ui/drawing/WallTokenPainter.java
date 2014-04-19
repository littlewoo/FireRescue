/**
 *  File name: WallTokenPainter.java
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

import game.Walls.Direction;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Set;

/**
 * Painter for painting the walls in a square.
 *
 * @author littlewoo
 */
public class WallTokenPainter extends TokenPainter {
	
	private static final int WIDTH_PERCENTAGE = 100;
	
	private static final int THICKNESS = 5;
	
	private int north;
	private int east;
	private int south;
	private int west;
	
	private final Set<Direction> directions;
	
	
	
	/**
	 * Construct a new WallTokenPainter
	 * 
	 * @param directions the directions in which walls should be painted
	 */
	public WallTokenPainter(Set<Direction> directions) {
		this.directions = directions;
		int offset = CELL_SIZE / 2 - 3;
		north = -offset;
		east = offset;
		south = offset;
		west = -offset;
	}

	/* (non-Javadoc)
	 * @see ui.drawing.TokenPainter#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(5));
		for (Direction dir : directions) {
			switch (dir) {
				case NORTH:
					g.drawLine(east, north, west, north);
					break;
				case EAST:
					g.drawLine(east, north, east, south);
					break;
				case SOUTH:
					g.drawLine(east, south, west, south);
					break;
				case WEST:
					g.drawLine(west, north, west, south);
					break;
				default:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see ui.drawing.TokenPainter#getDiameter()
	 */
	@Override
	protected int getDiameter() {
		return WIDTH_PERCENTAGE;
	}
	
	@Override
	public void updateLocation(int x, int y) {
		super.updateLocation(x, y);
		north += y;
		south += y;
		west += x;
		east += x;
		
	}
}
