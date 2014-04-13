package ui.playersDialog;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColourElement extends JPanel {
	private static final long serialVersionUID = -458233182340693648L;
	static final List<ColourElement> COLOURS;
	private static final ColourElement BLACK_ELEM = new ColourElement(Color.BLACK, Color.WHITE, "Black");
	private static final ColourElement BLUE_ELEM = new ColourElement(Color.BLUE, Color.WHITE, "Blue");
	private static final ColourElement GREEN_ELEM = new ColourElement(Color.GREEN, Color.BLACK, "Green");
	private static final ColourElement YELLOW_ELEM = new ColourElement(Color.YELLOW, Color.BLACK, "Yellow");
	private static final ColourElement ORANGE_ELEM = new ColourElement(new Color(255,127,0), Color.BLACK, "Orange");	
	private static final ColourElement RED_ELEM = new ColourElement(Color.RED, Color.WHITE, "Red");
		
	static {
		COLOURS = new ArrayList<ColourElement>();
		COLOURS.add(BLACK_ELEM);
		COLOURS.add(BLUE_ELEM);
		COLOURS.add(GREEN_ELEM);
		COLOURS.add(ORANGE_ELEM);
		COLOURS.add(YELLOW_ELEM);
		COLOURS.add(RED_ELEM);
	}
	
	private Color colour;
	
	public ColourElement(Color colour, Color textColour, String name) {
		JLabel colourLabel = new JLabel(name);
		colourLabel.setForeground(textColour);
		add(colourLabel);
		this.setBackground(colour);
		this.colour = colour;
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(colour);
		super.paint(g);
	}
	
	public Color getColour() {
		return colour;
	}

	public static List<ColourElement> getDefaultColours() {
		List<ColourElement> vals = new ArrayList<ColourElement>();
		for (ColourElement ce : COLOURS) {
			vals.add(ce);
		}
		return vals;
	}
}