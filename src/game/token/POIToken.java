/**
 *  File name: POIToken.java
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
package game.token;

import java.util.ArrayList;
import java.util.List;

import ui.drawing.POITokenPainter;
import ui.drawing.TokenPainter;

/**
 *
 * @author littlewoo
 */
public class POIToken implements FlippableToken {
	/** The token on the other side of this one: either a victim or blank */
	private POIReverseToken reverse;
	
	/**
	 * Create a new POIToken
	 * 
	 * @param reverse the token on the reverse of this one
	 */
	private POIToken(ReverseType r) {
		switch (r) {
			case BLANK:
				reverse = new BlankPOIToken(this);
				break;
			case VICTIM:
				reverse = new VictimPOIToken(this);
				break;			
		}
	}

	/* (non-Javadoc)
	 * @see game.token.Token#getPainter()
	 */
	@Override
	public TokenPainter getPainter() {
		return new POITokenPainter("?");
	}

	/* (non-Javadoc)
	 * @see game.token.FlippableToken#flip()
	 */
	@Override
	public Token flip() {
		return reverse;
	}

	/**
	 * Create all the POI tokens for use in the game. 
	 * 
	 * @param count the number of POI tokens to generate
	 * @param ratio the number of victim POI tokens to generate for each blank
	 * @return the list of POI tokens, in shuffled order.
	 */
	public static List<POIToken> getPOITokenStack(int count, double ratio) {
		List<POIToken> result = new ArrayList<POIToken>();
		int blanks = (int) (((double) count) / (ratio + 1));
		for (int i=0; i<blanks; i++) {
			result.add(new POIToken(ReverseType.BLANK));
		}
		int victims = count - blanks;
		for (int i=0; i<victims; i++) {
			result.add(new POIToken(ReverseType.VICTIM));
		}
		return result;
	}
	
	private enum ReverseType {
		BLANK, VICTIM;
	}
}
