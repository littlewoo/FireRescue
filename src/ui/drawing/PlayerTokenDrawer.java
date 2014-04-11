package ui.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class PlayerTokenDrawer extends TokenDrawer {
	
	// the diameter of the token, as a percentage of the cell size
	private static int TOKEN_DIAMETER_PERCENTAGE = 66;
	
	/*
	 * the offset from the centre of the token for where the token's character
	 * label should be drawn
	 */
	private static int TOKEN_CHAR_X_OFFSET = -14;
	private static int TOKEN_CHAR_Y_OFFSET = 14;
	
	// the font size of the token character label
	private static int TOKEN_CHAR_FONT_SIZE = 48;
	// the font of the token character label
	private static Font TOKEN_CHAR_FONT = 
			new Font(Font.MONOSPACED, Font.BOLD, TOKEN_CHAR_FONT_SIZE);
	
	// the font size and font of the token player name
	private static int PLAYER_FONT_SIZE = 10;
	private static Font PLAYER_FONT = 
			new Font(Font.SANS_SERIF, Font.BOLD, PLAYER_FONT_SIZE);
	
	private static final int PLAYER_X_OFFSET = -14;
	private static final int PLAYER_Y_OFFSET = 22;

	// the symbol used as the token's character label, and its colour
	private final static String symbol = "P";
	
	private String name;
	private Color colour;

	public PlayerTokenDrawer(String playerName, Color colour) {
		this.name = playerName;
		this.colour = colour;
	}
	
	@Override
	public void draw(Graphics2D g) {
		drawCircleToken(g, TOKEN_DIAMETER_PERCENTAGE, Color.WHITE, colour);
		g.setColor(colour);
		g.setFont(TOKEN_CHAR_FONT);
		g.drawString(symbol, x + TOKEN_CHAR_X_OFFSET, y + TOKEN_CHAR_Y_OFFSET);
		
		g.setColor(Color.BLACK);
		g.setFont(PLAYER_FONT);
		g.drawString(name, x+PLAYER_X_OFFSET, y+PLAYER_Y_OFFSET);
		
	}

}
