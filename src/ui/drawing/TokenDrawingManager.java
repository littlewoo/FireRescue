package ui.drawing;

import game.token.Token;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class TokenDrawingManager {

	private Map<Token, TokenPainter> tokenPainters;
	
	public TokenDrawingManager() {
		tokenPainters = new HashMap<Token, TokenPainter>();
	}
	
	public void addToken(Token t) {
		System.out.println("Adding token: " + t);
		tokenPainters.put(t, t.getPainter());
	}
	
	public void removeToken(Token t) {
		tokenPainters.remove(t);
	}
	
	public void updateTokenLocation(Token t, int x, int y) {
		System.out.println("Painting token: " + t);
		tokenPainters.get(t).updateLocation(x, y);
	}
	
	public void drawAll(Graphics2D g) {
		for (TokenPainter drawer : tokenPainters.values()) {
			drawer.draw(g);			
		}
	}
}
