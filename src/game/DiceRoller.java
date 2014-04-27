/**
 *  File name: DiceRoller.java
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

import interfaces.DiceRollListener;
import interfaces.ListShuffler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class for performing tasks adding randomness to the game. Principally rolling
 * dice, but also shuffling lists.
 *
 * @author littlewoo
 *
 */
public class DiceRoller implements ListShuffler {
	
	/** random number generator used in rolling dice */
	private Random rand;
	private List<DiceRollListener> listeners;
	
	/**
	 * Create a new DiceRoller using an arbitrary seed. Using this constructor
	 * to initialize DiceRoller should result in a different sequence of rolls
	 * each time.
	 *  
	 */
	public DiceRoller() {
		this(System.currentTimeMillis());
	}
	
	/**
	 * Create a new DiceRoller, using a given seed. If the same seed is used,
	 * the sequence of rolls produced by this DiceRoller should be the same.
	 * 
	 * @param seed the seed to use to initialize this DiceRoller. 
	 */ 
	public DiceRoller(long seed) {
		rand = new Random(seed);
		listeners = new ArrayList<DiceRollListener>();
	}
	
	/**
	 * Roll a single die, of a given number of sides.
	 * 
	 * @param sides the number of sides on the die to be rolled
	 * @param alert true if the dicerolllisteners should be alerted to the roll
	 * @return a DieResult object containing the result
	 */
	public DieResult rollDie(int sides, boolean alert) {
		DieResult result = new DieResult(sides, rand.nextInt(sides) + 1);
		if (alert) {
			alertListeners(result);
		}
		return result;
	}
	
	/**
	 * Roll a collection of dice. The dice to be rolled are given in a mapping
	 * of integers to integers. The keys in the <code>Map</code> are the number
	 * of sides, and the values are the number of dice to be rolled of that
	 * many sides. 
	 * 
	 * For example:
	 * To roll 4d8 and 7d6, use the following code, assuming 
	 * <code>diceReqs</code> is an object of type 
	 * <code>Map<Integer, Integer></code>.
	 * <code>
	 * 		diceReqs.put(8, 4);
	 * 		diceReqs.put(6, 7);
	 * 		diceRoller.rollDice(diceReqs);
	 * </code>
	 * 
	 * @param dice the dice to be rolled.
	 * @param alert true if the dicerolllisteners should be alerted to the roll
	 * @return
	 */
	public List<DieResult> rollDice(Map<Integer, Integer> dice, boolean alert) {
		List<DieResult> result = new ArrayList<DieResult>();
		for (int sides : dice.keySet()) {
			int n = dice.get(sides);
			for (int i=0; i<n; i++) {
				result.add(rollDie(sides, alert));
			}
		}
		return result; 		
	}
	
	/**
	 * Add a die listener to this roller.
	 * 
	 * @param listener the listener to be added.
	 */
	public void addDiceRollListener(DiceRollListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Alert the listeners of a roll.
	 * 
	 * @param die the DieResult that has been rolled
	 */
	private void alertListeners(DieResult d) {
		for (DiceRollListener l : listeners) {
			l.diceRoll(d);
		}
	}
	
	/**
	 * Shuffle a list
	 * 
	 * @param list the list to be shuffled
	 * @return a copy of the list, in a shuffled order
	 */
	public <T> List<T> shuffle(final List<T> list) {
		List<T> result = new ArrayList<T>();
		for (T t : list) {
			int position = 0;
			if (result.size() > 0) {
				position = rand.nextInt(result.size());
			}
			result.add(position, t);
		}		
		return result;		
	}
	
	/**
	 * A die, consisting of a number of sides, and the result of rolling it.
	 *
	 * @author littlewoo
	 */
	public class DieResult {
		public final int sides;
		public final int roll;
		
		/**
		 * Make a new DieResult.
		 * 
		 * @param sides the number of sides on the die.
		 * @param roll the result of rolling it.
		 */
		public DieResult(int sides, int roll) {
			this.sides = sides;
			this.roll = roll;
		}
	}
}
