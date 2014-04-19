/**
 *  File name: TokenPaintingManager.java
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
package ui.drawing;

import game.token.Token;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for handling all drawing of tokens on the board
 *
 * @author littlewoo
 */
public class TokenPaintingManager {

	/** The token painters managed */
	private Map<Token, TokenPainter> tokenPainters;
	/** The comparator for ordering tokens in the z axis */
	private Comparator<TokenPainter> tokenOrderComparator;
	
	/**
	 * Construct a new token painting manager.
	 */
	public TokenPaintingManager() {
		tokenPainters = new HashMap<Token, TokenPainter>();
		tokenOrderComparator = new Comparator<TokenPainter>() {
			@Override
			public int compare(TokenPainter o1, TokenPainter o2) {
				return o2.getDiameter() - o1.getDiameter();
			}
		};
	}
	
	/**
	 * Add a token to be painted
	 * 
	 * @param t the token to be added
	 * @param x the x location of the token
	 * @param y the y location of the token
	 */
	public void addToken(Token t, int x, int y) {
		tokenPainters.put(t, t.getPainter());
		updateTokenLocation(t, x, y);
	}
	
	/**
	 * Remove a token from painting
	 * 
	 * @param t the token to be removed
	 */
	public void removeToken(Token t) {
		tokenPainters.remove(t);
	}
	
	/**
	 * Update the location of a token.
	 * 
	 * @param t the token to be updated
	 * @param x the new x location
	 * @param y the new y location
	 */
	public void updateTokenLocation(Token t, int x, int y) {
		tokenPainters.get(t).updateLocation(x, y);
	}
	
	/**
	 * Draw all the tokens currently managed by this manager
	 * 
	 * @param g the graphics to draw on
	 */
	public void drawAll(Graphics2D g) {
		List<TokenPainter> painters = 
				new ArrayList<TokenPainter>(tokenPainters.values());
		Collections.sort(painters, tokenOrderComparator);
		for (TokenPainter drawer : painters) {
			drawer.draw(g);			
		}
	}
}
