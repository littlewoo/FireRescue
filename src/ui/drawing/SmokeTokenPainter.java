package ui.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

public class SmokeTokenPainter extends GenericTokenPainter {

	public SmokeTokenPainter() {
		super("S", Color.GRAY);
	}

	@Override
	public void draw(Graphics2D g) {
		drawCircleToken(g, 80, Color.BLACK, Color.RED);
		drawCharacter(g);
	}
}
