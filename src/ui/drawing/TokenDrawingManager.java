package ui.drawing;

import game.token.Token;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenDrawingManager {

	private Map<Token, TokenPainter> tokenPainters;
	private Comparator<TokenPainter> tokenOrderComparator;
	
	public TokenDrawingManager() {
		tokenPainters = new HashMap<Token, TokenPainter>();
		tokenOrderComparator = new Comparator<TokenPainter>() {
			@Override
			public int compare(TokenPainter o1, TokenPainter o2) {
				return o2.getDiameter() - o1.getDiameter();
			}
		};
	}
	
	public void addToken(Token t, int x, int y) {
		tokenPainters.put(t, t.getPainter());
		updateTokenLocation(t, x, y);
	}
	
	public void removeToken(Token t) {
		tokenPainters.remove(t);
	}
	
	public void updateTokenLocation(Token t, int x, int y) {
		tokenPainters.get(t).updateLocation(x, y);
	}
	
	public void drawAll(Graphics2D g) {
		List<TokenPainter> painters = 
				new ArrayList<TokenPainter>(tokenPainters.values());
		Collections.sort(painters, tokenOrderComparator);
		for (TokenPainter drawer : painters) {
			drawer.draw(g);			
		}
	}
}
