/**
 *  File name: FlippableTokenPainter.java
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

import game.token.FlippableToken;

import java.awt.Graphics2D;

/**
 * Painter for painting a flippable token. Acts as a wrapper for painters of the
 * tokens representing the two sides of the flippable token. 
 * 
 * @author littlewoo
 */
public abstract class FlippableTokenPainter extends TokenPainter {
	
	/** the token painter for the currently active token */
	private TokenPainter painter;
	
	/**
	 * Constructor.
	 * 
	 * @param token the token object representing the initial side of the 
	 * 			flippable token
	 */
	public FlippableTokenPainter(FlippableToken token) {
		painter = token.getPainter();
	}
	
	/**
	 * Respond to the flipping of the token
	 * 
	 * @param token the new token (i.e. the token after flipping)
	 */
	public void flip(FlippableToken token) {
		painter = token.getPainter();
	}
	
	/**
	 * Paint the token in its current state.
	 */
	@Override
	public void draw(Graphics2D g) {
		painter.draw(g);
	}
}
