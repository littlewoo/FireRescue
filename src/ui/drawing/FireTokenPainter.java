package ui.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

public class FireTokenPainter extends GenericTokenPainter {

	public FireTokenPainter() {
		super("F", Color.ORANGE);
	}

	@Override
	public void draw(Graphics2D g) {
		drawCircleToken(g, 80, Color.BLACK, Color.RED);
		drawCharacter(g);
	}

}
