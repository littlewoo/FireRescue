/**
 *  File name: PlayerPanel.java
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
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel for gathering details of a player. Gathers the name and the colour.
 *
 * @author littlewoo
 */
public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = -3487717973647209369L;
	/** Text field for the player's name */
	private JTextField txtPlayerName;
	/** ComboBox for the player's colour */
	private ColourComboBox cboColour;	
	/** The possible colours for the player */
	private Collection<ColourElement> colours;
	
	/**
	 * Construct a new playerPanel
	 * 
	 * @param colours the possible colours for the player
	 */
	public PlayerPanel(Collection<ColourElement> colours) {		
		this.colours = colours;
		
		JLabel lblName = new JLabel("Name:");
		this.add(lblName);
		
		txtPlayerName = new JTextField();
		txtPlayerName.setPreferredSize(new Dimension(150, 20));
		this.add(txtPlayerName);
		
		JLabel lblColour = new JLabel("Colour:");
		this.add(lblColour);
		
		cboColour = new ColourComboBox(coloursToArray(colours)); 
		this.add(cboColour);
	}	
	
	/**
	 * @return the player data entered in this panel
	 */
	public PlayerInputData getPlayer() {
		String name = txtPlayerName.getText();
		Color colour = cboColour.getColour();
		return new PlayerInputData(name, colour);
	}
	
	/**
	 * Convert the colours to an array
	 * 
	 * @param colours the colour collection to be converted
	 * @return the array containing the colour elements
	 */
	private ColourElement[] coloursToArray(Collection<ColourElement> colours) {
		ColourElement[] vals = new ColourElement[colours.size()];
		return colours.toArray(vals);
	}
	
	/**
	 * Get the colours which have not been selected
	 * 
	 * @return a list containing the ColourElements which have not been selected 
	 */
	public List<ColourElement> getRemainingColours() {
		List<ColourElement> vals = new ArrayList<ColourElement>();
		Object selected = cboColour.getSelectedItem();
		for (ColourElement ce : colours) {
			if (selected != ce) {
				vals.add(ce);
			}
		}
		return vals;
	}
	
	/**
	 * Set the name and colour of this PlayerPanel.
	 * 
	 * @param name
	 * @param colour
	 */
	public void setTestData(String name, ColourElement colour) {
		txtPlayerName.setText(name);
		cboColour.setSelectedItem(colour);
	}
}
