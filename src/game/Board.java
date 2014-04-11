package game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ui.BoardPanel.SelectSquareListener;

public class Board implements SelectSquareListener {
	private Map<Point, List<Token>> tokenLayer;
	
	private List<TokenChangeListener> tokenChangeListeners;
	
	public Board() {
		tokenLayer = new HashMap<Point, List<Token>>();
		for (int x=0; x<10; x++) {
			for (int y=0; y<8; y++) {
				Point p = new Point(x, y);
				tokenLayer.put(p, new ArrayList<Token>());
			}
		}
		
		tokenChangeListeners = new ArrayList<TokenChangeListener>();
	}
	
	public void addToken(int x, int y, Token t) {
		Point p = new Point(x, y);
		tokenLayer.get(p).add(t);
		alertTokenChangeListeners(p);
	}
	
	public void removeToken(int x, int y, Token t) {
		Point p = new Point(x, y);
		boolean success = tokenLayer.get(p).remove(t);
		if (success) {
			alertTokenChangeListeners(p);
		}
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
	
	public void addTokenChangeListener(TokenChangeListener listener) {
		tokenChangeListeners.add(listener);
	}
	
	public interface TokenChangeListener {
		public void onTokenChange(int x, int y, List<Token> tokens);
	}

	@Override
	public void onSelectSquare(int x, int y) {
		System.out.println("Board selecting square: " + x + ", " + y);
		addToken(x, y, new PlayerToken("Esther", Color.ORANGE));
	}
}
