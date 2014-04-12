package ui.playersDialog;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColourElement extends JPanel {
	private static final long serialVersionUID = -458233182340693648L;
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
}