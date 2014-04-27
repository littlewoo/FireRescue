/**
 *  File name: Action.java
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
import interfaces.ActionPerformer;

import java.awt.Point;

/**
 * Represents a possible action to be taken by a player.
 *
 * @author littlewoo
 */
public abstract class Action {
	
	/** Costs of actions */
	private final static int MOVE_COST = 1;
	private final static int MOVE_INTO_FIRE_COST = 2;
	private final static int MOVE_INTO_SMOKE_COST = 1;
	private final static int MOVE_WITH_VICTIM_COST = 2;
	private final static int MOVE_WITH_VICTIM_INTO_FIRE_COST = 100;
	
	/** The player the action applies to */
	private final Player player;
	
	/** the location the action applies to */
	private final Point loc;
	
	/** the type of the action */
	private final ActionType type;
	
	/** the ap cost of the action */
	private final int apCost;
	
	/**
	 * Initialize an action
	 * 
	 * @param p the player taking the action
	 * @param loc the location targeted by the action
	 * @param type the type of the action
	 * @param cost the cost of the action
	 */
	public Action(Player p, Point loc, ActionType type) {
		player = p;
		this.loc = loc;
		this.type = type;
		apCost = type.getCost();
	}
	
	/**
	 * Perform the action
	 * 
	 * @param performer the ActionPerformer which will carry out the action
	 * @return true if the action was performed successfully
	 */
	public abstract boolean performAction(ActionPerformer performer);
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the location of the action
	 */
	public Point getLoc() {
		return loc;
	}

	/**
	 * @return the type
	 */
	public ActionType getType() {
		return type;
	}
	
	/**
	 * @return the apCost
	 */
	public int getApCost() {
		return apCost;
	}

	@Override
	public String toString() {
		return "[Action: Player=" + player + ", x=" + loc.x + ", y=" + loc.y +
				", type=" + type;
	}

	/**
	 * Possible types of action.
	 *
	 * @author littlewoo
	 */
	public enum ActionType {		
		MOVE("Move", MOVE_COST),
		MOVE_INTO_SMOKE("Move into smoke", MOVE_INTO_SMOKE_COST),
		MOVE_INTO_FIRE("Move into fire", MOVE_INTO_FIRE_COST),
		MOVE_WITH_VICTIM("Carry victim", MOVE_WITH_VICTIM_COST),
		MOVE_WITH_VICTIM_INTO_FIRE(
				"Carry victim into fire", MOVE_WITH_VICTIM_INTO_FIRE_COST);
		
		private int cost;
		private String name;
		ActionType(String name, int cost) {
			this.name = name;
			this.cost = cost;
		}
		
		public String getName() {
			return name;
		}
		
		public int getCost() {
			return cost;
		}
	}

}
