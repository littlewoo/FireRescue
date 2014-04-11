package game;

import java.awt.Color;

import ui.drawing.GenericTokenPainter;
import ui.drawing.TokenPainter;


public class Token {

	public TokenPainter getPainter() {
		
		return new GenericTokenPainter("T", Color.GREEN);
	}
}
