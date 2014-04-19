/**
 *  File name: Walls.java
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

import java.awt.Point;
import java.util.Map;
import java.util.Set;

/**
 * Class for storing the walls, and determining whether a given move is passable
 * or blocked by walls.
 *
 * @author littlewoo
 */
public class Walls {
	/** The walls */
	private Map<Point, Set<Direction>> walls;
	
	/**
	 * <p>Constructor for walls. The walls are represented as follows: </p>
	 * 
	 *         1       2       3
	 *  
	 *      +--h00--+--h10--+--h20--+   <br />
	 *      |       |       |       |   <br />
	 *  1  v00     v10     v20     v30  <br />
	 *      |       |       |       |   <br />
	 *      +--h01--+--h11--+--h21--+   <br />
	 *      |       |       |       |   <br />
	 *  2  v01     v11     v21     v31  <br />
	 *      |       |       |       |   <br />
	 *      +--h02--+--h12--+--h22--+   <br />
	 *      |       |       |       |   <br />
	 *  3  v02     v12     v22     v32  <br />
	 *      |       |       |       |   <br />
	 *      +--h03--+--h13--+--h23--+   <br />
	 *   
	 * 
	 * @param horizontal the horizontal set of walls
	 * @param vertical the vertical set of walls
	 */
	public Walls(Map<Point, Set<Direction>> walls) {
		System.out.println(walls);
		this.walls = walls;
		
	}
	
	/**
	 * Get whether a wall space is passable. Takes a pair of coordinates, 
	 * representing a square, and a direction, to check whether a move in that
	 * direction is possible or not.
	 * 
	 * @param x the x coordinate of the square to check (between 1 and the width 
	 * 			of the board, inclusive)
	 * @param y the y coordinate of the square to check (between 1 and the 
	 * 			height of the board, inclusive)
	 * @param dir the direction to check
	 * @return true if the space is passable
	 * 
	 * @throws ArrayIndexOutOfBoundsException if x or y are outside the bounds 
	 * 			of the board
	 */
	public boolean isPassable(int x, int y, Direction dir) {
		Point p = new Point(x, y);
		return !walls.get(p).contains(dir);
	}
	
	/**
	 * Get the directions which are blocked by walls from a square
	 * 
	 * @param x the x coordinate of the square to check
	 * @param y the y coordinate of the square to check
	 * @return the directions which are blocked by walls from a square
	 */
	public Set<Direction> getWalls(int x, int y) {
		return walls.get(new Point(x, y));
	}
	
	
	public enum Direction {
		NORTH('N'), SOUTH('S'), EAST('E'), WEST('W');
		private char character;
		
		private Direction(char c) {
			character = c;
		}
		
		public static Direction getDir(char val) {
			for (Direction d : values()) {
				if (d.character == val) {
					return d;
				}
			}
			return null;
		}
	}
}
