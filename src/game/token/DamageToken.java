/**
 *  File name: DamageToken.java
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

import game.Walls.Direction;
import ui.drawing.DamageTokenPainter;
import ui.drawing.TokenPainter;

/**
 * Token for representing damage to walls. This represents all damage tokens on
 * a particular wall.
 * 
 * @author littlewoo
 */
public class DamageToken implements Token {
	
	/** the maximum number of damage tokens that can be on one wall */
	private static final int MAX_QTY = 2;
	
	/** the number of damage tokens in this stack */
	private int qty;
	
	/** the painter for painting this token */
	private DamageTokenPainter painter;
	
	/**
	 * Make a new Damage token
	 */
	public DamageToken(Direction dir) {
		qty = 0;
		painter = new DamageTokenPainter(dir, this);
	}
	
	/**
	 * Add a damage token
	 */
	public void addDamageToken() {
		if (qty + 1 > MAX_QTY) {
			throw new RuntimeException(
					"Cannot add more than 2 damage tokens...");
		}
		qty ++;
	}

	/** 
	 * @return the number of damage tokens currently placed here
	 */
	public int getQty() {
		return qty;
	}

	/* (non-Javadoc)
	 * @see game.token.Token#getPainter()
	 */
	@Override
	public TokenPainter getPainter() {
		return painter;
	}

}
