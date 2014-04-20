/**
 *  File name: ActionProvider.java
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
package interfaces;

import java.util.List;

/**
 * Provides actions for square selections, and performs a selected action.
 *
 * @author littlewoo
 */
public interface ActionProvider {
	
	/**
	 * Get a list of actions available for a given square.
	 * 
	 * @param x the x coordinate of the square
	 * @param y the y coordinate of the square
	 * @return a list of available actions
	 */
	public List<Action> getActions(int x, int y);
	
	/**
	 * Perform an action
	 *
	 * @param x the x coordinate targetted by the action
	 * @param y the y coordinate targetted by the action
	 * @param the action to perform
	 */
	public void performAction(int x, int y, Action action);
	
	
	public enum Action {
		MOVE;
	}
}
