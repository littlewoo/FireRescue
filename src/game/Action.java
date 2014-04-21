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
package game;

/**
 * Represents a possible action to be taken by a player.
 *
 * @author littlewoo
 */
public class Action {
	
	/** Costs of actions */
	private final static int MOVE_COST = 1;
	private final static int MOVE_INTO_FIRE_COST = 2;
	private final static int MOVE_INTO_SMOKE_COST = 1;
	
	/** The player the action applies to */
	private final Player player;
	
	/** the location the action applies to */
	private final int x;
	private final int y;
	
	/** the type of the action */
	private final ActionType type;
	
	/** the ap cost of the action */
	private final int apCost;
	
	/**
	 * Initialize an action
	 * 
	 * @param p the player taking the action
	 * @param x the x location of the action
	 * @param y the y location of the action
	 * @param type the type of the action
	 * @param cost the cost of the action
	 */
	public Action(Player p, int x, int y, ActionType type) {
		player = p;
		this.x = x;
		this.y = y;
		this.type = type;
		apCost = type.getCost();
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
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
		return "[Action: Player=" + player + ", x=" + x + ", y=" + y +
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
		MOVE_INTO_FIRE("Move into fire", MOVE_INTO_FIRE_COST);
		
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
