/**
 *  File name: WallToken.java
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

import java.util.Set;

import ui.drawing.TokenPainter;
import ui.drawing.WallTokenPainter;

/**
 * Token for drawing walls. Note that this token only represents the portion of
 * the wall within a single square. The other square needs its own WallToken to
 * draw it's part of the wall.
 *
 * @author littlewoo
 */
public class WallToken implements Token {
	
	private final WallTokenPainter tp;
	
	/**
	 * Construct a new WallToken
	 * 
	 * @param dirs the directions in which there are walls, from this square
	 */
	public WallToken(Set<Direction> dirs) {
		tp = new WallTokenPainter(dirs);
	}

	/* (non-Javadoc)
	 * @see game.token.Token#getPainter()
	 */
	@Override
	public TokenPainter getPainter() {
		return tp;
	}

}
