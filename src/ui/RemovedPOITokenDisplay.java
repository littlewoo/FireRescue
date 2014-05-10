/**
 *  File name: RemovedPOITokenDisplay.java
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
package ui;

import game.token.BlankPOIToken;
import game.token.POIToken;
import game.token.TokenStackToken;

import java.awt.Point;

/**
 * Class for managing the display of POI Tokens which have been removed for 
 * rescue or being killed 
 * 
 * @author littlewoo
 */
public class RemovedPOITokenDisplay {
	/** the height and width of the display */
	private final int width;
	private final int height;
	
	/** record of where the next token is to be placed */
	private Point nextTokenPosition;
	
	/** the panel for displaying the tokens on */
	private final TokenGridPanel display;
	/** the panel for displaying the blank tokens */
	private final TokenGridPanel blankDisplay;
	/** the stack of blank tokens */
	private final TokenStackToken blankStack;
	
	/**
	 * Make a new display.
	 * 
	 * @param width the width of the display
	 * @param height the height of the display
	 * @param panel the panel managing the display
	 * @param blanks the panel for displaying blanks
	 */
	public RemovedPOITokenDisplay(int width, int height, TokenGridPanel panel,
								  TokenGridPanel blanks) {
		this.width = width;
		this.height = height;
		display = panel;
		blankDisplay = blanks;
		nextTokenPosition = new Point(0,0);
		blankStack = new TokenStackToken(new BlankPOIToken(null));
		blankDisplay.addToken(blankStack, new Point(0,0));
	}
	
	/**
	 * Add a token to the main display.
	 * 
	 * @param t the token to add
	 * @return true if successful, false if the grid is already full
	 */
	public boolean addToken(POIToken t) {
		if (nextTokenPosition.y == height) {
			return false;
		}
		display.addToken(t, nextTokenPosition);
		nextTokenPosition.x ++;
		if (nextTokenPosition.x == width) {
			nextTokenPosition.x = 0;
			nextTokenPosition.y ++;
		}
		return true;
	}
	
	/**
	 * Add a blank token
	 */
	public void addBlankToken() {
		blankStack.increment();
	}
}
