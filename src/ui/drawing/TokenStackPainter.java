/**
 *  File name: TokenStackPainter.java
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
 *
 * @author littlewoo
 */
public class TokenStackPainter extends TokenPainter {
	
	private TokenPainter painter;
	
	/** the number of tokens in the stack */
	private int count;
	
	public TokenStackPainter(TokenPainter painter) {
		this.painter = painter;
		count = 0;
	}
	
	/**
	 * Update the location of this token
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	@Override
	public void updateLocation(int x, int y) {
		super.updateLocation(x, y);
		painter.updateLocation(x, y);
	}
	
	public void setCount(int val) {
		this.count = val;
	}

	public void setPainter(TokenPainter tp) {
		painter = tp;
	}
	
	public TokenPainter getPainter() {
		return painter;
	}

	/* (non-Javadoc)
	 * @see ui.drawing.TokenPainter#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		if (painter != null && count > 0) {
			painter.draw(g);
		}
		g.setColor(Color.RED);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
		g.drawString("" + count, x + CELL_SIZE / 4, y + CELL_SIZE / 4);
	}

	/* (non-Javadoc)
	 * @see ui.drawing.TokenPainter#getDiameter()
	 */
	@Override
	protected int getDiameter() {
		return painter.getDiameter();
	}

}
