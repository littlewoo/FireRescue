package ui.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class ThreatTokenPainter extends GenericTokenPainter {

	private static final int DIAMETER_PERCENTAGE = 80;

	public ThreatTokenPainter(String symbol, Color symbolColour) {
		super(symbol, symbolColour);
	}

	@Override
	public void draw(Graphics2D g) {
		drawCircleToken(g, DIAMETER_PERCENTAGE, Color.BLACK, Color.RED);
		drawCharacter(g);
	}

	@Override
	public int getDiameter() {
		return DIAMETER_PERCENTAGE;
	}

}