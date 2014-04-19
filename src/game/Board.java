/**
 *  File name: Board.java
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
package game;

import game.token.FireToken;
import game.token.PlayerToken;
import game.token.SmokeToken;
import game.token.ThreatToken;
import game.token.Token;
import game.token.WallToken;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The board on which all the action happens. 
 *
 * @author littlewoo
 */
public class Board {
	/** The locations of all the tokens in the game */
	private Map<Token, Point> tokenLocs;
	/** The fire tokens. */
	private Map<Point, ThreatToken> fireLayer;
	/** The player tokens */
	private Map<Point, PlayerToken> playersLayer;

	/** Listeners for changes to the tokens in the game. */
	private List<TokenChangeListener> tokenChangeListeners;

	/**
	 * Construct a new Board.
	 */
	public Board() {
		playersLayer = new HashMap<Point, PlayerToken>();
		tokenLocs = new HashMap<Token, Point>();
		fireLayer = new HashMap<Point, ThreatToken>();

		tokenChangeListeners = new ArrayList<TokenChangeListener>();
	}

	/**
	 * Add a token to the board, in the location given. Note that this method
	 * makes no attempt to check whether a token is already on the location
	 * provided.
	 * 
	 * @param x the x coordinate to place the token in
	 * @param y the y coordinate
	 * @param t the token to be added
	 */
	public void addToken(int x, int y, Token t) {
		checkCoordinates(x, y, true);
		tokenLocs.put(t, new Point(x, y));
		TokenChangeEvent e = new TokenChangeEvent(x, y, t, TokenChangeType.ADD);
		alertTokenChangeListeners(e);
	}

	/**
	 * Add a player token to the board, in the location given. This method will
	 * not allow a player token to be placed on top of another.
	 * 
	 * @param x the x coordinate to place the token in
	 * @param y the y coordinate to place the token in
	 * @param t the token
	 * @return true if the token was successfully placed
	 */
	public boolean addPlayerToken(int x, int y, PlayerToken t) {
		Point p = new Point(x, y);
		if (playersLayer.get(p) != null) {
			return false;
		}
		addToken(x, y, t);
		playersLayer.put(p, t);
		return true;
	}

	/**
	 * Add a threat token to the board, in the location given. This method will
	 * not allow a threat token to be placed on top of another.
	 * 
	 * @param x the x coordinate to place the token in
	 * @param y the y coordinate to place the token in
	 * @param t the token
	 */
	public void addThreatToken(int x, int y, ThreatToken t) {
		addToken(x, y, t);
		fireLayer.put(new Point(x, y), t);
	}

	/**
	 * Remove a token from the board.
	 * 
	 * @param t the token to be removed
	 */
	public void removeToken(Token t) {
		System.out.println("Removing token: " + t);
		Point p = tokenLocs.remove(t);
		boolean success = (p != null);
		if (success) {
			TokenChangeEvent e = new TokenChangeEvent(p.x, p.y, t,
					TokenChangeType.REMOVE);
			alertTokenChangeListeners(e);
		}
	}

	/**
	 * Remove a player token from the board.
	 * 
	 * @param t the token to be removed
	 */
	public void removePlayerToken(PlayerToken t) {
		Point p = tokenLocs.get(t);
		if (p != null) {
			playersLayer.remove(p);
			removeToken(t);
		}
	}

	/**
	 * Remove a threat token from the board
	 * 
	 * @param t the token to be removed
	 */
	private void removeThreatToken(ThreatToken t) {
		System.out.println("Removing threat token: " + t);
		Point p = tokenLocs.get(t);
		removeThreatToken(p.x, p.y);
	}

	/**
	 * Remove a threat token from the board, given its location.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	private void removeThreatToken(int x, int y) {
		Point p = new Point(x, y);
		System.out.println("Removing threat token by location: " + p);
		ThreatToken t = fireLayer.remove(p);
		removeToken(t);
	}

	/**
	 * Move a player token to a new location.
	 * 
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 * @param t
	 */
	public void movePlayerToken(int x, int y, PlayerToken t) {
		checkCoordinates(x, y, true);
		removePlayerToken(t);
		addPlayerToken(x, y, t);
	}
	
	/**
	 * Check that a set of coordinates is in bounds. The method provides the 
	 * option of throwing an exception if the coordinates are out of bounds.
	 * 
	 * @param x the x coordinate to check
	 * @param y the y coordinate to check
	 * @param throwException if true, throws an illegal argument exception if the 
	 * 				coordinates are out of bounds
	 * @return true if the coordinates are in bounds 
	 */
	private boolean checkCoordinates(int x, int y, boolean throwException) {
		boolean inBounds = !(x < 0 || x > Game.WIDTH || y < 0 || y > Game.HEIGHT);
		if (throwException && !inBounds) {
			throw new IllegalArgumentException(
				"Board.addToken: location out of bounds. (" + x + "," + y + ")");
		} 
		return inBounds;
	}

	/**
	 * Carry out the advance fire phase of the game, centred on a given square.
	 * Advancing fire consists of placing smoke or fire on a location, or
	 * triggering an explosion there, followed by changing all smoke adjacent to
	 * a fire into a fire, followed by removing any threat tokens around the 
	 * outside edge of the board.
	 * 
	 * @param x the x coordinate of the target
	 * @param y the y coordinate of the target
	 */
	void advanceFire(int x, int y) {
		Point p = new Point(x, y);
		ThreatToken t = fireLayer.get(p);
		if (t == null) {
			addThreatToken(x, y, new SmokeToken());
		} else if (t instanceof SmokeToken) {
			removeThreatToken(t);
			addThreatToken(x, y, new FireToken());
		} else {
			fireExplosion(x, y);
		}
	}

	/** 
	 * Remove any fire tokens from around the outside edge of the board.
	 */
	public void removeFireFromEdges() {
		System.out.println("Removing fire from edges: ");
		for (int x = 0; x < Game.WIDTH; x++) {
			removeThreatToken(x, 0);
			removeThreatToken(x, Game.HEIGHT-1);
		}
		for (int y = 0; y < Game.HEIGHT; y++) {
			removeThreatToken(0, y);
			removeThreatToken(Game.WIDTH-1, y);
		}
		System.out.println("Done removing fire from edges.");
	}

	/** 
	 * Wherever a smoke token is adjacent to a fire token, change it into a fire
	 * token. If this places another smoke token adjacent to a fire token, 
	 * carry on until there are no smoke tokens adjacent to fire tokens left.
	 */
	public void smokeIntoFire() {
		for (int x = 0; x < Game.WIDTH; x++) {
			for (int y = 0; y < Game.HEIGHT; y++) {
				Point p = new Point(x, y);
				ThreatToken t = fireLayer.get(p);
				if (t != null && tokenLocs.get(t) == null) {
					System.out.println("Item is in the fire layer but not tokenlocs: " + p);
					System.out.println("FireLayer:\n" + fireLayer);
					System.out.println("TokenLocs:\n" + tokenLocs);
				}
				if (t instanceof FireToken) {
					smokeIntoFire((FireToken) t);
				}
			}
		}
	}

	/**
	 * Check a fire token, to see whether it is adjacent to any smoken tokens.
	 * If it is, change to smoke token to a fire token, and recursively call 
	 * this method on that token.
	 * 
	 * @param t the firetoken to be checked
	 */ 
	private void smokeIntoFire(FireToken t) {
		Point p = null;
		try {
		p = tokenLocs.get(t);
		Point[] testPoints = new Point[] { new Point(p.x - 1, p.y),
				new Point(p.x + 1, p.y), new Point(p.x, p.y - 1),
				new Point(p.x, p.y + 1) };
		
		for (Point np : testPoints) {
			ThreatToken tt = fireLayer.get(np);
			if (tt instanceof SmokeToken) {
				removeThreatToken(tt);
				FireToken ft = new FireToken();
				addThreatToken(np.x, np.y, ft);
				smokeIntoFire(ft);
			}
		}} catch (NullPointerException e) {
			System.out.println("Details: ");
			System.out.println("\tFireToken: " + t + ", " + t.getClass());
			if (p == null) {
				System.out.println("\tp==null");
			} else {
				System.out.println("\tPoint: " + p);
			}
			throw e;
		}
	}

	/**
	 * Calculate and apply the results of an explosion on a given location. 
	 * 
	 * @param x the x coordinate of the explosion's centre
	 * @param y the y coordinate of the explosion's centre
	 */
	private void fireExplosion(int x, int y) {
		Point[] directions = new Point[] { 
				new Point(-1, 0), // west
				new Point(1, 0), // east
				new Point(0, -1), // north
				new Point(0, 1) }; // south

		for (Point p : directions) {
			int xOffset = x + p.x;
			int yOffset = y + p.y;
			ThreatToken t = fireLayer.get(new Point(xOffset, yOffset));
			while (t != null && t instanceof FireToken) {
				xOffset += p.x;
				yOffset += p.y;
				t = fireLayer.get(new Point(xOffset, yOffset));
			}
			if (t != null) {
				removeThreatToken(t);
			}
			addThreatToken(xOffset, yOffset, new FireToken());

		}

	}

	/**
	 * Alert the token change listeners that a token change event has occurred.
	 * Used when a change happens to any of the tokens
	 * 
	 * @param e a tokenChangeEvent to be passed to the listeners.
	 */
	private void alertTokenChangeListeners(TokenChangeEvent e) {
		for (TokenChangeListener l : tokenChangeListeners) {
			l.onTokenChange(e);
		}
	}

	/** 
	 * @param listener
	 */
	public void addTokenChangeListener(TokenChangeListener listener) {
		tokenChangeListeners.add(listener);
	}

	/**
	 * Interface for objects to be informed when the state of the tokens in a
	 * Board changes.
	 *
	 * @author littlewoo
	 */
	public interface TokenChangeListener {
		/**
		 * Method called when the state of the tokens in the Board changes.
		 * 
		 * @param e the TokenChangeEvent
		 */
		public void onTokenChange(TokenChangeEvent e);
	}

	/**
	 * An event containing information about the change to the tokens on the
	 * Board. 
	 *
	 * @author littlewoo
	 */
	public class TokenChangeEvent {
		/** the coordinates affected by the change */
		private final int x;
		private final int y;
		/** the token affected by the change */
		private final Token token;
		/** the type of change */
		private final TokenChangeType change;

		/**
		 * Make a new event.
		 * 
		 * @param x the x coordinate
		 * @param y the y coordinate
		 * @param t the affected token
		 * @param change the kind of chane which has occurred
		 */
		private TokenChangeEvent(int x, int y, Token t, TokenChangeType change) {
			this.x = x;
			this.y = y;
			this.token = t;
			this.change = change;
		}

		/** 
		 * @return the x coordinate of the change
		 */
		public int getX() {
			return x;
		}

		/**
		 * @return the y coordinate of the change
		 */
		public int getY() {
			return y;
		}

		/**
		 * @return the token affected by the change
		 */
		public Token getToken() {
			return token;
		}

		/**
		 * @return the type of change which has occurred
		 */
		public TokenChangeType getChange() {
			return change;
		}
	}

	/**
	 * Enumeration of the different types of changes which can occur to a token.
	 * 
	 * @author littlewoo
	 */
	public enum TokenChangeType {
		ADD, REMOVE, MOVE;
	}

	/** 
	 * Create the walls on this board
	 * @param w 
	 */
	public void addWalls(Walls w) {
		for (int x=1; x<Game.WIDTH-1; x++) {
			for (int y=1; y<Game.HEIGHT-1; y++) {
				try {
					addToken(x, y, new WallToken(w.getWalls(x, y)));
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("x=" + x + ", y=" + y);
					e.printStackTrace(System.out);
				}
			}
		}
	}
}
