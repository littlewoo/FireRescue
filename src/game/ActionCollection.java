/**
 *  File name: ActionCollection.java
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

import interfaces.ActionStore;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author littlewoo
 */
public class ActionCollection implements ActionStore {
	/** mapping of points to actions */
	private final Map<Point, List<Action>> actionLocs;
	/** this collection's actions */
	private final List<Action> actions;
	
	public ActionCollection(List<Action> actions) {
		actionLocs = new HashMap<Point, List<Action>>();
		this.actions = actions;
		for (Action a : actions) {
			Point p = new Point(a.getX(), a.getY());
			if (actionLocs.get(p) == null) {
				actionLocs.put(p, new ArrayList<Action>());
			}
			actionLocs.get(p).add(a);
		}
	}

	/* (non-Javadoc)
	 * @see interfaces.ActionStore#getActions()
	 */
	@Override
	public List<Action> getActions() {
		return actions;
	}

	/* (non-Javadoc)
	 * @see interfaces.ActionStore#getActions(java.awt.Point)
	 */
	@Override
	public List<Action> getActions(Point p) {
		return actionLocs.get(p);
	}

}
