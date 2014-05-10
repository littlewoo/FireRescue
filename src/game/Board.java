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

import game.Walls.Direction;
import game.token.FireToken;
import game.token.POIFaceToken;
import game.token.POIQuestionMarkToken;
import game.token.POIToken;
import game.token.PlayerToken;
import game.token.SmokeToken;
import game.token.ThreatToken;
import game.token.Token;
import game.token.VictimPOIToken;
import game.token.WallToken;
import interfaces.POIEventListener;
import interfaces.POIEventListener.POIEvent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The board on which all the action happens. 
 *
 * @author littlewoo
 */
public class Board {
	/** The locations around the edge of the board */
	private Set<Point> edges;
	
	/** The locations of all the tokens in the game */
	private Map<Token, Point> tokenLocs;
	/** The fire tokens. */
	private TokenLayer<ThreatToken> fireLayer;
	/** The player tokens */
	private TokenLayer<PlayerToken> playersLayer;
	/** The POI tokens */
	private TokenLayer<POIToken> poiLayer;
	
	/** the walls on the board */
	private Walls walls;

	/** Listeners for changes to the tokens in the game. */
	private List<TokenChangeListener> tokenChangeListeners;
	/** listeners for POI token events */
	private List<POIEventListener> poiEventListeners; 

	/**
	 * Construct a new Board.
	 */
	public Board(int width, int height) {
		tokenLocs = new HashMap<Token, Point>();
		playersLayer = new TokenLayer<PlayerToken>(width, height);
		fireLayer = new TokenLayer<ThreatToken>(width, height);
		poiLayer = new TokenLayer<POIToken>(width, height);

		tokenChangeListeners = new ArrayList<TokenChangeListener>();
		edges = getEdges();
	}

	/** 
	 * Create the walls on this board
	 * @param w 
	 */
	public void addWalls(Walls w) {
		walls = w;
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				addToken(new Point(x, y), new WallToken(w.getWalls(x, y)));
			}
		}
	}
	
	/**
	 * Get a set of squares adjacent to a token
	 * 
	 * @return the set of squares
	 */
	private Set<Point> getAdjacentSquares(Token t) {
		Point p = tokenLocs.get(t);
		if (p != null) {
			return getAdjacentSquares(p.x, p.y);
		}
		return new HashSet<Point>();
	}

	/** 
	 * Get a set of squares adjacent to a location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return
	 */
	private Set<Point> getAdjacentSquares(int x, int y) {
		Set<Point> result = new HashSet<Point>();
		Point p = new Point(x, y);
		if (p.x > 0) {
			result.add(new Point(p.x-1, p.y));
		}
		if (p.x < getWidth()-1) {
			result.add(new Point(p.x+1, p.y));
		}
		if (p.y > 0) {
			result.add(new Point(p.x, p.y-1));
		}
		if (p.y < getHeight()-1) {
			result.add(new Point(p.x, p.y+1));
		}
		
		return result;
	}

	/** 
	 * @return the width of the board
	 */
	private int getWidth() {
		return Game.WIDTH;
	}
	
	/**
	 * @return the height of the board 
	 */
	private int getHeight() {
		return Game.HEIGHT;
	}
	
	/**
	 * Add a token to the board, in the location given. Note that any number of
	 * tokens of any type can be added to the same location. This method makes
	 * no attempt to check whether a token is already on the location provided.
	 * 
	 * @param p the location to add the token to
	 * @param t the token to be added
	 */
	public void addToken(Point p, Token t) {
		checkCoordinates(p.x, p.y, true);
		tokenLocs.put(t, p);
		TokenChangeEvent e = new TokenChangeEvent(p.x, p.y, t, TokenChangeType.ADD);
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
		if (playersLayer.addToken(t, p)) {
			addToken(p, t);
			return true;
		}
		return false;
	}

	/**
	 * Add a threat token to the board, in the location given. This method will
	 * not allow a threat token to be placed on top of another.
	 * 
	 * @param x the x coordinate to place the token in
	 * @param y the y coordinate to place the token in
	 * @param t the token
	 * @return true if the token could be added (i.e. there was no threat token
	 * 				already present
	 */
	public boolean addThreatToken(int x, int y, ThreatToken t) {
		Point p = new Point(x, y);
		if (fireLayer.getTokenAt(p) != null) {
			return false;
		}
		addToken(p, t);
		fireLayer.addToken(t, new Point(x, y));
		return true;
	}
	
	/**
	 * Add a POI token to the board, in the location given. This method will not
	 * allow a POI token to be placed on top of another.
	 * 
	 * @param p the point to add the token at
	 * @param t the token to add
	 * @param replaceFire if true, then the token will be replace fire, if 
	 * 					  false then the token cannot be placed on fire
	 * @return true if the token could be added (i.e. there was no POI token or
	 * 				fire already present)
	 */
	public boolean addPOIToken(Point p, POIToken t, boolean replaceFire) {
		if (poiLayer.getTokenAt(p) != null && !replaceFire && isFireAt(p)) {
			return false;
		}
		addToken(p, t);
		poiLayer.addToken(t, p);
		return true;
	}

	/**
	 * Remove a token from the board.
	 * 
	 * @param t the token to be removed
	 */
	public void removeToken(Token t) {
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
			playersLayer.removeToken(p);
			removeToken(t);
		}
	}

	/**
	 * Remove a threat token from the board
	 * 
	 * @param t the token to be removed
	 */
	private void removeThreatToken(ThreatToken t) {
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
		ThreatToken t = fireLayer.removeToken(p);
		removeToken(t);
	}
	
	/** 
	 * Remove a POI token from the board.
	 * 
	 * @param t the token to remove
	 */
	private void removePOIToken(POIToken t) {
		Point p = tokenLocs.get(t);
		poiLayer.removeToken(p);
		removeToken(t);
	}

	/**
	 * Move a player token to a new location.
	 * 
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 * @param player
	 */
	public void movePlayerToken(int x, int y, Player player) {
		checkCoordinates(x, y, true);
		PlayerToken t = player.getToken();
		removePlayerToken(t);
		addPlayerToken(x, y, t);
	}
	
	/**
	 * Check whether a move between two adjacent squares is passable by a player
	 * token. Checks for walls and other player tokens.
	 * 
	 * @param p1 the source square
	 * @param p2 the destination square
	 * @return true if the move is possible
	 */
	private boolean isMovable(Point p1, Point p2) {
		if (getAdjacentSquares(p1.x, p1.y).contains(p2)) {
			Direction dir;
			if (p1.x == p2.x) {
				if (p1.y > p2.y) {
					dir = Direction.NORTH;
				} else {
					dir = Direction.SOUTH;
				}
			} else {
				if (p1.x > p2.x){
					dir = Direction.WEST;
				} else {
					dir = Direction.EAST;
				}
			}
			boolean passable = walls.isPassable(p1.x, p1.y, dir);
			boolean empty = playersLayer.getTokenAt(p2) == null;
			return passable && empty;
		} else {
			throw new IllegalArgumentException("Points not adjacent.");
		}
	}
	
	/**
	 * Get a set of possible single square moves for a player token. This makes 
	 * no checks as to other factors limiting a player's movement (notably AP 
	 * cost of the move, and whether the player can afford to make it).
	 * 
	 * @param player the playerToken to get moves for
	 * @return a set of possible moves
	 */
	public Set<Point> getPossibleMoves(PlayerToken player) {
		Set<Point> result = new HashSet<Point>();
		Point loc = tokenLocs.get(player);
		for (Point p : getAdjacentSquares(player)) {
			if (isMovable(loc, p)) {
				result.add(p);
			}
		}
		return result;
	}
	
	/**
	 * @return true if there is a fire at a given location
	 */
	public boolean isFireAt(Point p) {
		return fireLayer.getTokenAt(p) instanceof FireToken;
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
		boolean inBounds = !(x < 0 || x > getWidth() || y < 0 || y > getHeight());
		if (throwException && !inBounds) {
			throw new IllegalArgumentException(
				"Board.addToken: location out of bounds. (" + x + "," + y + ")");
		} 
		return inBounds;
	}
	
	/**
	 * @return a set of coordinates around the edge of the board
	 */
	private Set<Point> getEdges() {
		Set<Point> edges = new HashSet<Point>();
		for (int x=0; x<getWidth(); x++) {
			edges.add(new Point(x, 0));
			edges.add(new Point(x, getHeight()-1));
		}
		for (int y=0; y<getWidth(); y++) {
			edges.add(new Point(0, y));
			edges.add(new Point(getWidth()-1, y));
		}
		return edges;
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
		ThreatToken t = fireLayer.getTokenAt(p);
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
		for (Point p : edges) {
			removeThreatToken(p.x, p.y);
		}
	}

	/** 
	 * Wherever a smoke token is adjacent to a fire token, change it into a fire
	 * token. If this places another smoke token adjacent to a fire token, 
	 * carry on until there are no smoke tokens adjacent to fire tokens left.
	 */
	public void smokeIntoFire() {
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				Point p = new Point(x, y);
				ThreatToken t = fireLayer.getTokenAt(p);
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
		Set<Point> testPoints = getAdjacentSquares(t);
		
		for (Point np : testPoints) {
			ThreatToken tt = fireLayer.getTokenAt(np);
			if (tt instanceof SmokeToken) {
				removeThreatToken(tt);
				FireToken ft = new FireToken();
				addThreatToken(np.x, np.y, ft);
				smokeIntoFire(ft);
			}
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
			ThreatToken t = fireLayer.getTokenAt(new Point(xOffset, yOffset));
			while (t != null && t instanceof FireToken) {
				xOffset += p.x;
				yOffset += p.y;
				t = fireLayer.getTokenAt(new Point(xOffset, yOffset));
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
		public TokenChangeEvent(int x, int y, Token t, TokenChangeType change) {
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
	 * Get the POI token at the given location
	 * 
	 * @param loc the location to check
	 * @return the POI token at the location, if there is one
	 */
	public POIToken getPOITokenAt(Point loc) {
		return poiLayer.getTokenAt(loc);
	}
	
	/**
	 * @param t the player to check if a victim is there
	 * @return true if there is a victim on a square
	 */
	public VictimPOIToken getVictimAtPlayer(PlayerToken t) {
		Point p = tokenLocs.get(t);
		POIToken pt = getPOITokenAt(p);
		if (pt instanceof VictimPOIToken) {
			return (VictimPOIToken) pt;
		} else {
			return null;
		}
	}
	
	/**
	 * Flip a flippable token
	 * 
	 * @param token the token to flip
	 * @return the flipped token
	 */
	public POIFaceToken flipPOIToken(POIQuestionMarkToken t) {
		Point p = tokenLocs.get(t);
		if (p != null) {
			POIFaceToken flipped = t.flip();
			removePOIToken(t);
			addPOIToken(p, flipped, true);
			return flipped;
		}
		return null;
	}

	/**
	 * Move a POI token to a new location
	 * 
	 * @param t the POI token to be moved
	 * @param loc the new location to move to
	 * @return true if the move was successful
	 */
	public boolean movePOIToken(POIToken t, Point loc) {
		POIToken other = poiLayer.getTokenAt(loc);
		if (other != null || isFireAt(loc)) {
			return false;
		} 
		removePOIToken(t);
		addPOIToken(loc, t, false);
		return true;
	}
	
	/**
	 * Check whether any victims have been rescued, and alert listeners if they
	 * have.
	 */
	public void checkRescuedVictims() {
		for (POIToken t : poiLayer.getAllTokens()) {
			if (t instanceof VictimPOIToken) {
				checkRescuedVictim((VictimPOIToken) t);
			}
		}
	}
	
	/**
	 * Check whether a victim has been rescued, and alert listeners if they
	 * have.
	 * 
	 * @param v the victim token to check
	 */
	public void checkRescuedVictim(VictimPOIToken v) {
		Point p = tokenLocs.get(v);
		if (edges.contains(p)) {
			rescueVictim(v);
		}
	}
	
	/**
	 * Register a victim token as rescued
	 * 
	 * @param t the victim token to rescue
	 */
	public void rescueVictim(POIFaceToken t) {
		removePOIToken(t);
		alertPOIEventListeners(new POIEvent(t, POIEvent.POIEventType.RESCUED));
	}
	
	
	/**
	 * Add a POIEventListener
	 * 
	 * @param listener
	 */
	public void addPOIEventListener(POIEventListener listener) {
		if (poiEventListeners == null) {
			poiEventListeners = new ArrayList<POIEventListener>();
		}
		poiEventListeners.add(listener);
	}
	
	/**
	 * Alert POI event listeners of a new POI event
	 * 
	 * @param e the event
	 */
	private void alertPOIEventListeners(POIEvent e) {
		if (poiEventListeners != null) {
			for (POIEventListener l : poiEventListeners) {
				l.onPOIEvent(e);
			}
		}
	}
}
