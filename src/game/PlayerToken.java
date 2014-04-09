package game;

import java.awt.Color;

import ui.drawing.PlayerTokenDrawer;
import ui.drawing.TokenDrawer;

public class PlayerToken extends Token {
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
	public TokenDrawer getDrawer() {
		return new PlayerTokenDrawer(name, colour);
	}

}
