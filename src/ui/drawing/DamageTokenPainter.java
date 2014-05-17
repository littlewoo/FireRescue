/**
 *  File name: DamageTokenPainter.java
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
import game.token.DamageToken;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Painter for painting damage tokens.
 * 
 * @author littlewoo
 */
public class DamageTokenPainter extends TokenPainter {
	
	/** the size of the square representing the token, expressed in pixels, 
	 * calculated as a fraction of the cell size */
	private static final int TOKEN_SIZE = CELL_SIZE / 5;
	
	/** the colour of the damage token */
	private static final Color colour = Color.black;
	
	/** the direction of the wall from the cell this token is placed in */
	private final Direction direction;
	
	/** the location on the axis through the center of the cell of the wall on 
	 * which tokens are placed */
	private int wallLoc;
	
	/** the top left corners of the tokens when a given number of tokens is 
	 *  present */
	private Point topLeftSingle;
	private Point topLeftDoubleA;
	private Point topLeftDoubleB;

	/** the damageToken being painted by this painter */
	private final DamageToken token;
	
	/**
	 * Make a new DamageTokenPainter.
	 * 
	 * @param direction the direction this painter faces in, either EAST, or 
	 * 					SOUTH
	 */
	public DamageTokenPainter(Direction dir, DamageToken t) {
		direction = dir;
		token = t;
		updateTokenLocs();
	}

	/* (non-Javadoc)
	 * @see ui.drawing.TokenPainter#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(colour);
		switch (token.getQty()) {
			case 1:
				g.fillRect(topLeftSingle.x, topLeftSingle.y,
						   TOKEN_SIZE, TOKEN_SIZE);
				break;
			case 2:
				g.fillRect(topLeftDoubleA.x, topLeftDoubleA.y,
						   TOKEN_SIZE, TOKEN_SIZE);
				g.fillRect(topLeftDoubleB.x, topLeftDoubleB.y, 
						   TOKEN_SIZE, TOKEN_SIZE);
				break;
			default:
				throw new IllegalArgumentException(
							"Invalid quantity passed to DamageTokenPainter: " + 
							token.getQty());			
		}
	}

	/* (non-Javadoc)
	 * @see ui.drawing.TokenPainter#getDiameter()
	 */
	@Override
	protected int getDiameter() {
		return 99;
	}
	
	/**
	 * Update the locations of the damage tokens
	 * 
	 */
	private void updateTokenLocs() {
		int xLoc, yLoc;
		int xLocA, yLocA, xLocB, yLocB;
		switch (direction) {
			case EAST:			
				xLoc = wallLoc - TOKEN_SIZE / 2;
				yLoc = super.y - TOKEN_SIZE / 2;
				xLocA = xLocB = xLoc;
				yLocA = super.y - CELL_SIZE / 6 - TOKEN_SIZE / 2;
				yLocB = super.y + CELL_SIZE / 6 - TOKEN_SIZE / 2;
				break;
			case SOUTH:
				xLoc = super.x - TOKEN_SIZE / 2;
				yLoc = wallLoc - TOKEN_SIZE / 2;
				xLocA = super.x - CELL_SIZE / 6 - TOKEN_SIZE / 2;
				xLocB = super.x + CELL_SIZE / 6 - TOKEN_SIZE / 2;
				yLocA = yLocB = yLoc;
				break;
			default:
				throw new IllegalArgumentException(
					"DamageTokenPainter given invalid direction: " + direction);
		}
		topLeftSingle = new Point(xLoc, yLoc);
		topLeftDoubleA = new Point(xLocA, yLocA);
		topLeftDoubleB = new Point(xLocB, yLocB);
	}

	@Override
	public void updateLocation(int x, int y) {
		super.updateLocation(x, y);
		switch (direction) {
			case EAST:
				wallLoc = x + CELL_SIZE / 2;
				break;
			case SOUTH:
				wallLoc = y + CELL_SIZE / 2;
				break;
			default:
				throw new IllegalArgumentException(
					"DamageTokenPainter given invalid direction: " + direction);
		}
		updateTokenLocs();
	}

}
