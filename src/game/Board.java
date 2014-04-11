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
	private Map<Point, List<Token>> tokenLayer;
	private Map<Token, Point> tokenLocs;
	private Map<Point, ThreatToken> fireLayer;
	
	private Token playerToken;
	
	private List<TokenChangeListener> tokenChangeListeners;
	
	public Board() {
		tokenLayer = new HashMap<Point, List<Token>>();
		tokenLocs = new HashMap<Token, Point>();
		fireLayer = new HashMap<Point, ThreatToken>();
		
		for (int x=0; x<10; x++) {
			for (int y=0; y<8; y++) {
				Point p = new Point(x, y);
				tokenLayer.put(p, new ArrayList<Token>());
			}
		}
		
		tokenChangeListeners = new ArrayList<TokenChangeListener>();
		
		playerToken = new PlayerToken("Esther", Color.GREEN);
	}
	
	public void placePlayerToken() {
		addToken(4,4,playerToken);
	}
	
	public void addToken(int x, int y, Token t) {
		Point p = new Point(x, y);
		tokenLayer.get(p).add(t);
		tokenLocs.put(t, p);
		alertTokenChangeListeners(p);
	}
	
	public void removeToken(Token t) {
		Point p = tokenLocs.get(t);
		boolean success = tokenLayer.get(p).remove(t);
		if (success) {
			alertTokenChangeListeners(p);
		}
	}
	
	public void moveToken(int x, int y, Token t) {
		removeToken(t);
		addToken(x,y,t);
	}
	
	public List<Token> getTokensAt(int x, int y) {
		return tokenLayer.get(new Point(x, y));
	}
	
	private void alertTokenChangeListeners(Point p) {
		List<Token> tokens = tokenLayer.get(p);
		for (TokenChangeListener l : tokenChangeListeners) {
			l.onTokenChange(p.x, p.y, tokens);
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
		System.out.println("Explosion triggered at (" + x + "," + y + ")");
	}

	private void removeThreatToken(ThreatToken t) {
		fireLayer.remove(t);
		removeToken(t);		
	}

	private void addThreatToken(int x, int y, ThreatToken t) {
		Point p = new Point(x, y);
		fireLayer.put(p, t);
		addToken(x, y, t);
	}

	public void addTokenChangeListener(TokenChangeListener listener) {
		tokenChangeListeners.add(listener);
	}
	
	public interface TokenChangeListener {
		public void onTokenChange(int x, int y, List<Token> tokens);
	}

	@Override
	public void onSelectSquare(int x, int y, int button) {
		if (button == BoardPanel.LEFT_MOUSE_BUTTON) {
			moveToken(x, y, playerToken);
		} else {
			advanceFire(x, y);
		}
	}
}
