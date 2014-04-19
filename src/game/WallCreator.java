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

import game.Walls.Direction;
import interfaces.WallProvider;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
	 * @throws IOException 
	 */
	private static Walls createWalls() {
		Map<Point, Set<Direction>> dirs = new HashMap<Point, Set<Direction>>();
		File f = new File("res/simple.board");
		FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find the file " + f.getAbsolutePath());
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(fr);
		} catch (IOException e) {
			System.out.println("Problem reading the file.");
			e.printStackTrace();
		}
		int height = Integer.parseInt(props.getProperty("height"));
		int width = Integer.parseInt(props.getProperty("width"));
		System.out.println("Dimension: (" + width + "x" + height + ")");
		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++) {
				Set<Direction> dir = new HashSet<Direction>();
				String key = "" + x + y;
				String val = props.getProperty(key);
				if (val == null) {
					System.out.println("Couldn't read the property: " + key);
				}
				for (char c : val.toCharArray()) {
					dir.add(Direction.getDir(c));
				}
				dirs.put(new Point(x, y), dir);
				System.out.println(dir);
			}
			System.out.println("---------------");
		}
		return new Walls(dirs);
	}
	
	/* (non-Javadoc)
	 * @see interfaces.WallProvider#getWalls()
	 */
	@Override
	public Walls getWalls() {
		return createWalls();
	}

}
