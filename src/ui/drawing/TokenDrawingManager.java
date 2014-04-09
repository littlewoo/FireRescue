package ui.drawing;

import game.Token;

import java.awt.Graphics2D;

public class TokenDrawingManager {

	public void draw(Graphics2D g, int x, int y, Token t) {
		TokenDrawer drawer = t.getDrawer();
		drawer.draw(g, x, y);
	}
}
