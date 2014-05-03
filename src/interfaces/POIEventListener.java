/**
 *  File name: POIEventListener.java
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

import game.token.POIToken;

/**
 * Interface for listening to POI events, covering new POI tokens placed on the 
 * board, victims being rescued, false alarms revealed, victims dying, and false
 * alarms being 'killed'.
 * 
 * @author littlewoo
 */
public interface POIEventListener {
	
	/**
	 * Respond to a POI event
	 * 
	 * @param e the event to respond to.
	 */
	public void onPOIEvent(POIEvent e);
	
	/**
	 * Event covering changes to points of interest.
	 */
	public class POIEvent {
		/** the token affected */
		public final POIToken token;
		
		/** the type of the event */
		public final POIEventType type;
		
		/**
		 * Make a new POIEvent.
		 * 
		 * @param token the token affected
		 * @param type the type of the event
		 */
		public POIEvent(POIToken token, POIEventType type) {
			this.token = token;
			this.type = type;
		}
		
		/**
		 * Enum for different POIEvent types
		 */
		public enum POIEventType {
			PLACED, KILLED, RESCUED;
		}
	}
}
