/**
 *  File name: ActionView.java
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

import game.ActionCollection;

/**
 * Interface for an actionView: a view which provides some sort of display of 
 * available actions.
 *
 * @author littlewoo
 */
public interface ActionView {
	
	/**
	 * Display a list of actions
	 * 
	 * @param actions the actions to be displayed
	 */
	public void displayActions(ActionCollection actions);
}
