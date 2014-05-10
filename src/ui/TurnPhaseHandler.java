/**
 *  File name: TurnPhaseHandler.java
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

import interfaces.TurnPhaseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Label for showing the current turn phase.
 * 
 * @author littlewoo
 */
public class TurnPhaseHandler implements TurnPhaseListener {
	
	/** the texts displayed when advancing the turn phase */
	private static final Map<TurnPhase, String> texts = 
			new HashMap<TurnPhase, String>();
	
	static {
		texts.put(TurnPhase.MOVE, "Movement");
		texts.put(TurnPhase.ADVANCE_FIRE, "Advance fire");
		texts.put(TurnPhase.SMOKE_TO_FIRE, "Smoke to fire");
		texts.put(TurnPhase.CLEAR_EDGE_FIRE, "Clearing fire from edge");
		texts.put(TurnPhase.PLACE_POI, "Replenish POI tokens");
	}

	private TurnPhaseView turnPhaseView;
	
	/**
	 * Create a new TurnPhaseHandler
	 * 
	 * @param f the frame which dialogs should be attached to
	 */
	public TurnPhaseHandler(TurnPhaseView view) {
		turnPhaseView = view;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.TurnPhaseListener#onTurnPhaseChange(interfaces.TurnPhaseListener.TurnPhase)
	 */
	@Override
	public void onTurnPhaseChange(TurnPhase phase) {
		String val = texts.get(phase);
		if (val != null) {
			turnPhaseView.display(val);
		}
	}

}
