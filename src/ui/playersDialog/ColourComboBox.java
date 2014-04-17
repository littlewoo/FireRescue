/**
 *  File name: ColourComboBox.java
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
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * A combo box which lists a collection of colours. 
 *
 * @author littlewoo
 */
public class ColourComboBox extends JComboBox<ColourElement> {
	private static final long serialVersionUID = 5768939185265281650L;	
	
	/**
	 * Create a new ColourComboBox.
	 * 
	 * @param colours the ColourElements used in the box
	 */
	public ColourComboBox(ColourElement[] colours) {
		super(colours);
		setEditable(false);
		
		setRenderer(new ListCellRenderer<ColourElement>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends ColourElement> list,
					ColourElement value, int index, 
					boolean isSelected, boolean cellHasFocus) {
				return value;
			}
		});
	}

	/**
	 * Get the colour selected by the combo box
	 * 
	 * @return
	 */
	public Color getColour() {
		ColourElement ce = (ColourElement) this.getSelectedItem();
		return ce.getColour();
	}
}
