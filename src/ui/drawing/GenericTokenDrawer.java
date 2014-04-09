package ui.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GenericTokenDrawer extends TokenDrawer {
	
	// the diameter of the token, as a percentage of the cell size
	private static int TOKEN_DIAMETER_PERCENTAGE = 66;
	
	/*
	 * the offset from the centre of the token for where the token's character
	 * label should be drawn
	 */
	private static int TOKEN_CHAR_X_OFFSET = -18;
	private static int TOKEN_CHAR_Y_OFFSET = 17;
	
	// the font size of the token character label
	private static int TOKEN_CHAR_FONT_SIZE = 60;
	// the font of the token character label
	private static Font TOKEN_CHAR_FONT = 
			new Font(Font.MONOSPACED, Font.BOLD, TOKEN_CHAR_FONT_SIZE);

	// the symbol used as the token's character label, and its colour
	private String symbol;
	private Color symbolColour;
	
	/**
	 * Make a new Generic Token Drawer.
	 * @param symbol
	 * @param symbolColour
	 */
	public GenericTokenDrawer(String symbol, Color symbolColour) {
		this.symbol = symbol;
		this.symbolColour = symbolColour;
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.drawCircleToken(g, TOKEN_DIAMETER_PERCENTAGE, Color.WHITE, Color.RED);
		g.setColor(symbolColour);
		g.setFont(TOKEN_CHAR_FONT);
		g.drawString(symbol, x+TOKEN_CHAR_X_OFFSET, y+TOKEN_CHAR_Y_OFFSET);
	}
}
