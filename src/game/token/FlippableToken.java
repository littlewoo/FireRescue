/**
 *  File name: FlippableToken.java
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
 * Interface for tokens which can be flipped, to reveal a different token or a 
 * change to this token.
 * 
 * @author littlewoo
 */
public interface FlippableToken extends Token {
	/**
	 * Flip the token. The flipped token returned by this method represents this
	 * token, but changed in some way as if flipped over. Note that the returned
	 * token need not be the same object, and need not even be itself flippable
	 * (i.e. flipping a token may be a one-way procedure).
	 * 
	 * @return the flipped token
	 */
	public Token flip();
}
