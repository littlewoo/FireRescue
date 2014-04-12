package game;

import game.token.FireToken;
import game.token.PlayerToken;
import game.token.SmokeToken;
import game.token.ThreatToken;
import game.token.Token;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.BoardPanel;
import ui.BoardPanel.SelectSquareListener;

public class Board implements SelectSquareListener {	
	private Map<Token, Point> tokenLocs;
	private Map<Point, ThreatToken> fireLayer;
	private Map<Point, PlayerToken> playersLayer;
	
	private PlayerToken playerToken;
	
	private List<TokenChangeListener> tokenChangeListeners;
	
	public Board() {
		playersLayer = new HashMap<Point, PlayerToken>();
		tokenLocs = new HashMap<Token, Point>();
		fireLayer = new HashMap<Point, ThreatToken>();
			
		tokenChangeListeners = new ArrayList<TokenChangeListener>();
		
		playerToken = new PlayerToken("Esther", Color.GREEN);
	}
	
	public void placePlayerToken() {
		addPlayerToken(4,4,playerToken);
	}
	
	public void addToken(int x, int y, Token t) {
		tokenLocs.put(t, new Point(x, y));
		TokenChangeEvent e = new TokenChangeEvent(x, y, t, TokenChangeType.ADD);
		alertTokenChangeListeners(e);
	}
	
	public void addPlayerToken(int x, int y, PlayerToken t) {
		playersLayer.put(new Point(x, y), t);
		addToken(x, y, t);
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
	
	public void movePlayerToken(int x, int y, PlayerToken t) {
		removePlayerToken(t);
		addPlayerToken(x, y, t);
	}
	
	private void alertTokenChangeListeners(TokenChangeEvent e) {
		for (TokenChangeListener l : tokenChangeListeners) {
			l.onTokenChange(e);
		}
	}
	
	private void advanceFire(int x, int y) {
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
	
	private void fireExplosion(int x, int y) {
		Point[] directions = new Point[]{
				new Point(-1, 0),  // west
				new Point( 1, 0),  // east
				new Point( 0,-1),  // north
				new Point( 0, 1)}; // south
		
		for (Point p : directions) {
			int xOffset = x + p.x;
			int yOffset = y + p.y;
			while (fireLayer.get(new Point(xOffset, yOffset)) != null) {
				xOffset += p.x;
				yOffset += p.y;
			}
			addThreatToken(xOffset, yOffset, new FireToken());
		}
		
	}
	
	@Override
	public void onSelectSquare(int x, int y, int button) {
		if (button == BoardPanel.LEFT_MOUSE_BUTTON) {
			movePlayerToken(x, y, playerToken);
		} else {
			advanceFire(x, y);
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
