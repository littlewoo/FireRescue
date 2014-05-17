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
	 * <p>Constructor for walls. The walls are represented as each cell, and the
	 * directions from that cell that are walled. Be aware: this means that 
	 * walls can be 1-way!
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
	 * @param p the location of the cell
	 * @param dir the direction to check
	 * @return true if the space is passable
	 * 
	 * @throws ArrayIndexOutOfBoundsException if x or y are outside the bounds 
	 * 			of the board
	 */
	public boolean isPassable(Point p, Direction dir) {
		return !walls.get(p).contains(dir);
	}
	
	/**
	 * Get the directions which are blocked by walls from a square
	 * 
	 * @param p the location of the cell to get the walls for
	 * @return the directions which are blocked by walls from a square
	 */
	public Set<Direction> getWalls(Point p) {
		return walls.get(p);
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
