/**
 *  File name: TokenStackToken.java
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

import ui.drawing.TokenPainter;
import ui.drawing.TokenStackPainter;

/**
 *
 * @author littlewoo
 */
public class TokenStackToken implements Token {
	
	private int count;
	private final TokenStackPainter painter;
	
	public TokenStackToken(Token t) {
		count = 0;
		painter = new TokenStackPainter(t.getPainter());
	}
	
	public void setCount(int val) {
		this.count = val;
		painter.setCount(val);
	}
	
	public void increment() {
		setCount(count + 1);
	}
	
	public void decrement() {
		setCount(count - 1);
	}
	
	public int getCount() {
		return count;
	}

	/* (non-Javadoc)
	 * @see game.token.Token#getPainter()
	 */
	@Override
	public TokenPainter getPainter() {
		return painter;
	}

}
