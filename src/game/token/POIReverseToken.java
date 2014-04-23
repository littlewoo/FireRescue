/**
 *  File name: IPOIToken.java
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

/**
 *
 * @author littlewoo
 */
public abstract class POIReverseToken implements FlippableToken {
	/** the POIToken representing the reverse of this token */
	private POIToken reverse;

	/**
	 * Create a new BlankPOIToken.
	 * 
	 * @param reverse the POI token on the reverse of the blank.
	 */
	public POIReverseToken(POIToken reverse) {
		this.reverse = reverse;
	}

	/* (non-Javadoc)
	 * @see game.token.FlippableToken#flip()
	 */
	@Override
	public Token flip() {
		return reverse;
	}

}
