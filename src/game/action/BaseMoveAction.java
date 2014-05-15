/**
 *  File name: BaseMoveAction.java
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
package game.action;

import java.awt.Point;

import game.Player;
import interfaces.ActionPerformer;

/**
 * Parent for any actions which involve moving the player
 *
 * @author littlewoo
 */
public abstract class BaseMoveAction extends Action {

	/** 
	 * 
	 * @param p the player moving
	 * @param loc
	 * @param type
	 */
	public BaseMoveAction(Player p, Point loc, ActionType type) {
		super(p, loc, type);
	}
	
	/* (non-Javadoc)
	 * @see game.action.Action#performAction(interfaces.ActionPerformer)
	 */
	@Override
	public boolean performAction(ActionPerformer performer) {
		boolean val = getPlayer().performAction(this);
		if (val) {
			performer.movePlayer(getPlayer(), getLoc());
		}
		return val;
	}
}
