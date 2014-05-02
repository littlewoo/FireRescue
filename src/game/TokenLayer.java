/**
 *  File name: TokenLayer.java
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

import game.token.Token;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A storage class for storing tokens.
 *
 * @author littlewoo
 */
public class TokenLayer<T extends Token> {
	/** The token store */
	private Map<Point, T> tokens;
	
	/** the dimensions of the layer */
	private int width;
	private int height;
	
	/**
	 * Make a new TokenLayer, of a given width and height
	 * 
	 * @param width
	 * @param height
	 */
	public TokenLayer(int width, int height) {
		tokens = new HashMap<Point, T>();
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Check whether a location is within the bounds of the layer, and if not,
	 * throw an ArrayIndexOutOfBoundsException.
	 * 
	 * @param p the point to check
	 */
	private void throwExceptionIfOutOfBounds(Point p) {
		if (p.x > width || p.y > height) {
			throw new ArrayIndexOutOfBoundsException("Point out of bounds.");
		}		
	}
	
	/**
	 * Get the token at a given location in the layer. Returns null if no token
	 * is there.
	 * 
	 * @param p the location to check
	 * @return the token at the location, or null
	 */
	public T getTokenAt(Point p) {
		throwExceptionIfOutOfBounds(p);
		return tokens.get(p);
	}
	
	/**
	 * Get a set containing all the tokens in the layer
	 * 
	 * @return a set containing all of the tokens in the layer
	 */
	public Set<T> getAllTokens() {
		Set<T> result = new HashSet<T>();
		for (T t : tokens.values()) {
			result.add(t);
		}
		return result;
	}
	
	/**
	 * Add a new token to a given location in the layer. If the position is 
	 * already occupied, this method does not change the state of the layer at 
	 * all.
	 * 
	 * @param t the token to be added
	 * @param p the location to add it in
	 * @return true if the operation was successful, false if not (i.e. there 
	 * 				was already a token there)
	 */
	public boolean addToken(T t, Point p) {
		throwExceptionIfOutOfBounds(p);		
		if (tokens.get(p) != null) {
			return false;
		}
		tokens.put(p, t);
		return true;
	}
	
	/**
	 * Remove a token from the layer.
	 * 
	 * @param p the location to remove the token from.
	 * @return the token removed from the layer, or null if there wasn't one
	 */
	public T removeToken(Point p) {
		return tokens.remove(p);
	}
}
