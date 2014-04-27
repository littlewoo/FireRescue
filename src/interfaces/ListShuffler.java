/**
 *  File name: ListShuffler.java
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
 * Interface for an object which can shuffle a list
 * 
 * @author littlewoo
 */
public interface ListShuffler {
	
	/**
	 * Shuffle a list. The given list is not changed at all by the method.
	 * 
	 * @param list the list to be shuffled
	 * @return a shuffled <em>copy</em> of the original list
	 */
	public <T> List<T> shuffle(final List<T> list);
}
