package ui.drawing;

import game.PlayerToken;
import game.Token;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ui.BoardPanel;

public class TokenDrawer {
	private static final int CELL_SIZE = BoardPanel.CELL_SIZE;
	
	private static final Font tokenLabelFont = 
			new Font(Font.MONOSPACED, Font.BOLD, 50);
		
	public TokenDrawer() {
	}
	
	public void drawToken(Graphics2D g, int x, int y, Token t) {		
		if (t instanceof PlayerToken) {
			drawPlayerToken(g, x, y, (PlayerToken) t);
		} else {
			drawCircleToken(g, x, y, CELL_SIZE*2/3, Color.WHITE, Color.RED);
			g.setColor(Color.RED);
			g.setFont(tokenLabelFont);
			g.drawString("T", x - 16, y + 16);
		}
	}
	
	private void drawPlayerToken(Graphics2D g, int x, int y, PlayerToken t) {
		drawCircleToken(g, x, y, CELL_SIZE*2 / 3, Color.WHITE, t.getColour());
		g.setColor(t.getColour());
		g.setFont(tokenLabelFont);
		g.drawString("P", x-16, y+13);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
		g.setColor(Color.BLACK);
		g.drawString(t.getName(), x-16, y+20);
	}

	protected void drawCircleToken(Graphics2D g, int x, int y, int diameter,
								   Color bgColour, Color borderColour) {
		int cLeft = x - diameter/2;
		int cTop = y - diameter/2;
		
		g.setColor(bgColour);
		g.fillOval(cLeft, cTop, diameter, diameter);
		g.setStroke(new BasicStroke(10));
		g.setColor(borderColour);
		g.drawOval(cLeft, cTop, diameter, diameter);
	}
}
