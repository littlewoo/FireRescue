/**
 *  File name: MoveWithVictimAction.java
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

import game.Player;
import game.token.VictimPOIToken;
import interfaces.ActionPerformer;

import java.awt.Point;

/**
 * Action for moving a player with a victim
 * @author littlewoo
 */
public class MoveWithVictimAction extends BaseMoveAction {
	
	/** the victim to be moved with */
	private VictimPOIToken victim;

	/** 
	 * 
	 * @param p
	 * @param loc
	 * @param type
	 */
	public MoveWithVictimAction(Player p, Point loc, VictimPOIToken victim) {
		super(p, loc, ActionType.MOVE_WITH_VICTIM);
		this.victim = victim;
	}

	/**
	 * Perform the action. This moves the player token and the victim token to 
	 * the target square.
	 * 
	 * @param performer the object carrying out the action
	 */
	@Override
	public boolean performAction(ActionPerformer performer) {
		boolean val = getPlayer().canPerformAction(this);
		if (val) {
			if (performer.moveVictimToken(victim, getLoc())) {
				super.performAction(performer);
			} else {
				return false;
			}
		}
		return val;
	}
}
