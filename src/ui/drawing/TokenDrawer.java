package ui.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ui.BoardPanel;

public abstract class TokenDrawer {
	protected static final int CELL_SIZE = BoardPanel.CELL_SIZE;
	
	protected static final Font tokenLabelFont = 
			new Font(Font.MONOSPACED, Font.BOLD, 50);
	
	// the location of the token to be drawn
	protected int x;
	protected int y;
	
	public TokenDrawer() {
	}
	
	public void updateLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
		
	/**
	 * Draw a circular background of a token
	 * 
	 * @param g the graphics to draw on
	 * @param x the x location of the centre of the token
	 * @param y the y location of the centre of the token
	 * @param diameterPct the diameter of the circle, as a percentage of the 
	 * 						size of the cell
	 * @param bgColour the background colour of the circle
	 * @param borderColour the colour of the border of the circle
	 */
	protected void drawCircleToken(Graphics2D g, int diameterPct,
								   Color bgColour, Color borderColour) {
		int diameter = CELL_SIZE * diameterPct / 100;
		int cLeft = x - diameter/2;
		int cTop = y - diameter/2;
		
		g.setColor(bgColour);
		g.fillOval(cLeft, cTop, diameter, diameter);
		g.setStroke(new BasicStroke(10));
		g.setColor(borderColour);
		g.drawOval(cLeft, cTop, diameter, diameter);
	}

	public abstract void draw(Graphics2D g);
}
