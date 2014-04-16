package game;

import game.token.FireToken;
import game.token.PlayerToken;
import game.token.SmokeToken;
import game.token.ThreatToken;
import game.token.Token;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {	
	private Map<Token, Point> tokenLocs;
	private Map<Point, ThreatToken> fireLayer;
	private Map<Point, PlayerToken> playersLayer;
	
	private List<TokenChangeListener> tokenChangeListeners;
	
	public Board() {
		playersLayer = new HashMap<Point, PlayerToken>();
		tokenLocs = new HashMap<Token, Point>();
		fireLayer = new HashMap<Point, ThreatToken>();
			
		tokenChangeListeners = new ArrayList<TokenChangeListener>();
	}
	
	public void addToken(int x, int y, Token t) {
		tokenLocs.put(t, new Point(x, y));
		TokenChangeEvent e = new TokenChangeEvent(x, y, t, TokenChangeType.ADD);
		alertTokenChangeListeners(e);
	}
	
	public boolean addPlayerToken(int x, int y, PlayerToken t) {
		Point p = new Point(x,y);
		if (playersLayer.get(p) != null) {
			return false;
		}
		playersLayer.put(p, t);
		addToken(x, y, t);
		return true;
	}
	
	public void addThreatToken(int x, int y, ThreatToken t) {
		fireLayer.put(new Point(x,y), t);
		addToken(x, y, t);
	}
	
	public void removeToken(Token t) {
		Point p = tokenLocs.remove(t);
		boolean success = p != null;
		if (success) {
			TokenChangeEvent e = 
					new TokenChangeEvent(p.x, p.y, t, TokenChangeType.REMOVE);
			alertTokenChangeListeners(e);
		}
	}
	
	public void removePlayerToken(Token t) {
		Point p = tokenLocs.get(t);
		if (p != null) {
			playersLayer.remove(p);
			removeToken(t);
		}
	}

	private void removeThreatToken(ThreatToken t) {
		fireLayer.remove(t);
		removeToken(t);		
	}
	
	private void removeThreatToken(int x, int y) {
		Point p = new Point(x, y);
		removeThreatToken(fireLayer.get(p));
	}
	
	public void movePlayerToken(int x, int y, PlayerToken t) {
		removePlayerToken(t);
		addPlayerToken(x, y, t);
	}
	
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
		smokeIntoFire();
		removeFireFromEdges();
	}
	
	/** 
	 * 
	 */
	private void removeFireFromEdges() {
		for (int x=0; x<Game.WIDTH; x++) {
			removeThreatToken(x, 0);
			removeThreatToken(x, Game.HEIGHT);
		}
		for (int y=0; y<Game.HEIGHT; y++) {
			removeThreatToken(0, y);
			removeThreatToken(Game.WIDTH, y);
		}
	}

	/** 
	 * 
	 */
	private void smokeIntoFire() { 
		for (int x=0; x<Game.WIDTH; x++) {
			for (int y=0; y<Game.HEIGHT; y++) {
				Point p = new Point(x, y);
				ThreatToken t = fireLayer.get(p);
				if (t instanceof FireToken) {
					smokeIntoFire((FireToken) t);
				}
			}
		}
	}
	
	private void smokeIntoFire(FireToken t) {
		Point p = tokenLocs.get(t);
		Point[] testPoints = new Point[] {
				new Point(p.x-1, p.y),
				new Point(p.x+1, p.y),
				new Point(p.x, p.y-1),
				new Point(p.x, p.y+1)
		};
		for (Point np : testPoints) {
			ThreatToken tt = fireLayer.get(np);
			if (tt instanceof SmokeToken) {
				removeThreatToken(tt);
				FireToken ft = new FireToken();
				addThreatToken(np.x, np.y, ft);
				smokeIntoFire(ft);
			}
		}
	}

	private void fireExplosion(int x, int y) {
		Point[] directions = new Point[]{
				new Point(-1, 0),  // west
				new Point( 1, 0),  // east
				new Point( 0,-1),  // north
				new Point( 0, 1)}; // south
		
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
	
	private void alertTokenChangeListeners(TokenChangeEvent e) {
		for (TokenChangeListener l : tokenChangeListeners) {
			l.onTokenChange(e);
		}
	}

	public void addTokenChangeListener(TokenChangeListener listener) {
		tokenChangeListeners.add(listener);
	}
	
	public interface TokenChangeListener {
		public void onTokenChange(TokenChangeEvent e);
	}
	
	public class TokenChangeEvent {
		private final int x;
		private final int y;
		private final Token token;
		private final TokenChangeType change;
		
		private TokenChangeEvent(int x, int y, Token t, 
								 TokenChangeType change) {
			this.x = x;
			this.y = y;
			this.token = t;
			this.change = change;
		}
		
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public Token getToken() {
			return token;
		}
		public TokenChangeType getChange() {
			return change;
		}
	}
	
	public enum TokenChangeType {
		ADD, REMOVE, MOVE;
	}
}
