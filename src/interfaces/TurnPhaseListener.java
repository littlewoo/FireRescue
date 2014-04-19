/**
 *  File name: TurnPhaseListener.java
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

/**
 * A listener for listening for the progression of stages through a turn
 *
 * @author littlewoo
 */
public interface TurnPhaseListener {
	/**
	 * Called when the phase of the turn changes
	 * 
	 * @param phase the newly begun phase
	 */
	public void onTurnPhaseChange(TurnPhase phase);
	
	public enum TurnPhase {
		BEGIN, MOVE, ADVANCE_FIRE, SMOKE_TO_FIRE, CLEAR_EDGE_FIRE, PLACE_POI; 
	}
}
