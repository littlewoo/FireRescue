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

import game.Action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author littlewoo
 */
public class ActionPainter {
	
	/** The actions currently stored in the painter */
	Map<Action, Point> actionLocs;
	
	
	public ActionPainter() {
		actionLocs = new HashMap<Action, Point>();
	}

	/** 
	 * 
	 * @param g
	 */
	public void paintAll(Graphics2D g) {
		g.setColor(new Color(0.0f, 0.5f, 0.0f, 0.4f));
		for (Point p : actionLocs.values()) {
			g.fillOval(p.x-20, p.y-20, 40, 40);
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
