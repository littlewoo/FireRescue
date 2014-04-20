/**
 *  File name: APHandler.java
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

import game.token.PlayerToken;
import interfaces.APListener;

/**
 *
 * @author littlewoo
 */
public class APHandler implements APListener {
	
	/** The view updated by this handler */
	private final APView view;
	
	/** 
	 * Create a new APHandler
	 * 
	 * @param view the handler's view
	 */
	public APHandler(APView v) {
		view = v;
	}

	/* (non-Javadoc)
	 * @see interfaces.APListener#onAPChange(game.token.PlayerToken, int)
	 */
	@Override
	public void onAPChange(PlayerToken player, int ap) {
		view.updateAP(ap);
	}

}
