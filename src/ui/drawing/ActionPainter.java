/**
 *  File name: ActionPainter.java
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

import game.action.Action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for painting action markers. When an action is possible, a coloured 
 * circle is placed on the square relating to the action, to signify to the user
 * that an action is available on that square. This class draws the marker.
 *
 * @author littlewoo
 */
public class ActionPainter {
	
	/** the colour of the marker */
	private static final Color markerColour = new Color(0.0f, 0.5f, 0.0f, 0.5f);
	
	/** The actions currently stored in the painter */
	private Map<Action, Point> actionLocs;
	
	
	public ActionPainter() {
		actionLocs = new HashMap<Action, Point>();
	}

	/** 
	 * 
	 * @param g
	 */
	public void paintAll(Graphics2D g) {
		g.setColor(markerColour);
		for (Point p : actionLocs.values()) {
			g.fillOval(p.x-20, p.y-20, 40, 40);
			System.out.println("Drawing action at " + p);
		}
	}

	/** 
	 * 
	 * @param a
	 * @param x
	 * @param y
	 */
	public void addAction(Action a, Point p) {
		actionLocs.put(a, p);
	}

	/** 
	 * Clear all the actions from the painter.
	 */
	public void clearActions() {
		actionLocs.clear();
	}

}
