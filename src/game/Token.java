package game;

import java.awt.Color;

import ui.drawing.GenericTokenDrawer;
import ui.drawing.TokenDrawer;


public class Token {

	public TokenDrawer getDrawer() {
		
		return new GenericTokenDrawer("T", Color.GREEN);
	}
}
