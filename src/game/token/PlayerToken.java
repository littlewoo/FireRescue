package game.token;


import java.awt.Color;

import ui.drawing.PlayerTokenPainter;
import ui.drawing.TokenPainter;

public class PlayerToken implements MovableToken {
	private final String name;
	private final Color colour;
	
	public PlayerToken(String name, Color colour) {
		this.name = name;
		this.colour = colour;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public TokenPainter getPainter() {
		return new PlayerTokenPainter(name, colour);
	}

}
