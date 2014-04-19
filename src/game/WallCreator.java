/**
 *  File name: WallCreator.java
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
package game;

import interfaces.WallProvider;

/**
 * Utility class for creating walls for the board.
 *
 * @author littlewoo
 */
public class WallCreator implements WallProvider {

	/**
	 * Create the walls 
	 * 
	 * @return the walls
	 */
	private static Walls createWalls() {
		boolean l = true;
		boolean _ = true;
		boolean x = false;
		boolean[][] horizontalWalls = new boolean[][] {
				{_, _, _, _, _, x, _, _},
				{x, x, x, x, x, x, x, x},
				{x, x, _, _, _, _, _, x},
				{x, x, x, x, x, x, x, x},
				{_, _, _, x, _, _, _, _},
				{x, x, x, x, x, x, x, x},
				{_, _, x, _, _, _, _, _}
		};
		boolean[][] verticalWalls = new boolean[][] {
				{l, x, x, x, x, l, x, x, l},
				{l, x, x, l, x, x, x, x, l},
				{x, x, x, x, x, x, l, x, l},
				{l, x, l, x, x, x, x, x, l},
				{l, x, x, x, x, l, x, l, l},
				{l, x, x, x, x, x, x, x, l}
		};
		
		return new Walls(horizontalWalls, verticalWalls);
	}
	
	/* (non-Javadoc)
	 * @see interfaces.WallProvider#getWalls()
	 */
	@Override
	public Walls getWalls() {
		return createWalls();
	}

}
