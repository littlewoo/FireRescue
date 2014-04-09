package ui.drawing;

import game.Token;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

public class TokenDrawingManager {

	private Map<Token, TokenDrawer> tokenDrawers;
	
	public TokenDrawingManager() {
		tokenDrawers = new HashMap<Token, TokenDrawer>();
	}
	
	public void addToken(Token t) {
		System.out.println("Adding token: " + t);
		tokenDrawers.put(t, t.getDrawer());
	}
	
	public void removeToken(Token t) {
		tokenDrawers.remove(t);
	}
	
	public void updateTokenLocation(Token t, int x, int y) {
		System.out.println("Painting token: " + t);
		tokenDrawers.get(t).updateLocation(x, y);
	}
	
	public void drawAll(Graphics2D g) {
		for (TokenDrawer drawer : tokenDrawers.values()) {
			drawer.draw(g);			
		}
	}
}
