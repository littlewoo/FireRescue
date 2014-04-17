/**
 *  File name: ColourElement.java
 *
 *  Copyright 2014: John Littlewood
 *
 *  This file is part of FireRescue.
 *
 *  FireRescue is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FireRescue is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FireRescue.  If not, see <http://www.gnu.org/licenses/>.
 */
package ui.playersDialog;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A panel for displaying a colour and its name.
 *
 * @author littlewoo
 */
public class ColourElement extends JPanel {
	private static final long serialVersionUID = -458233182340693648L;
	
	/** The default colours used */
	static final List<ColourElement> COLOURS;
	private static final ColourElement BLACK_ELEM = new ColourElement(Color.BLACK, "Black");
	private static final ColourElement BLUE_ELEM = new ColourElement(Color.BLUE, "Blue");
	private static final ColourElement GREEN_ELEM = new ColourElement(Color.GREEN, "Green");
	private static final ColourElement YELLOW_ELEM = new ColourElement(Color.YELLOW, "Yellow");
	private static final ColourElement ORANGE_ELEM = new ColourElement(new Color(255,127,0), "Orange");	
	private static final ColourElement RED_ELEM = new ColourElement(Color.RED, "Red");
		
	static {
		COLOURS = new ArrayList<ColourElement>();
		COLOURS.add(BLACK_ELEM);
		COLOURS.add(BLUE_ELEM);
		COLOURS.add(GREEN_ELEM);
		COLOURS.add(ORANGE_ELEM);
		COLOURS.add(YELLOW_ELEM);
		COLOURS.add(RED_ELEM);
	}
	
	/** The colour stored in this colour element */
	private Color colour;
	
	/**
	 * Create a new colour element.
	 * 
	 * @param colour the colour stored in this element
	 * @param name the name of the colour
	 */
	public ColourElement(Color colour,String name) {
		this.colour = colour;
		JLabel colourLabel = new JLabel(name);
		colourLabel.setForeground(getTextColour());
		add(colourLabel);
		this.setBackground(colour);
	}
	
	/**
	 * Paint the component
	 * 
	 * @param g the graphics to paint on
	 */
	@Override
	public void paint(Graphics g) {
		setBackground(colour);
		super.paint(g);
	}
	
	/**
	 * @return the colour of this element
	 */
	public Color getColour() {
		return colour;
	}
	
	/** 
	 * @return the foreground colour for writing on this colour panel, for 
	 * 			maximum contrast
	 */
	private Color getTextColour() {
		double brightness = 
				colour.getRed() * 0.299 +
				colour.getGreen() * 0.587 +
				colour.getBlue() * 0.114;
		return (brightness > 127 ? Color.BLACK : Color.WHITE);
	}

	/**
	 * @return the default collection of colours
	 */
	public static Collection<ColourElement> getDefaultColours() {
		List<ColourElement> vals = new ArrayList<ColourElement>();
		for (ColourElement ce : COLOURS) {
			vals.add(ce);
		}
		return vals;
	}
}