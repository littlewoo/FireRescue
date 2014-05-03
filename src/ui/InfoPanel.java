/**
 *  File name: InfoPanel.java
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
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Dimension;

/**
 * Panel for displaying rescued victims, victims lost, and remaining POI Tokens
 * not yet placed on the board. 
 * 
 * @author littlewoo
 */
public class InfoPanel extends JPanel {
	
	/** The size of the cells containing the POItokens */
	private static final int CELL_SIZE = 60;
	/** the margin surrounding the grid */
	private static final int GRID_MARGIN = 10;
	/** The dimensions of the table for displaying rescued victims */
	private static final int RESCUED_WIDTH = 2;
	private static final int RESCUED_HEIGHT = 5;
	/** The dimensions of the table for displaying dead victims */
	private static final int KILLED_WIDTH = 2;
	private static final int KILLED_HEIGHT = 3;
	
	/** the font for the labels */
	private static final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 14);
	
	public InfoPanel() {
		setLayout(new GridLayout(0, 1, 0, 0));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBackground(Color.BLACK);
		setOpaque(true);
		
		JPanel rescuedPanel = 
				makeTokenDisplay(RESCUED_WIDTH, RESCUED_HEIGHT, "Rescued");
		add(rescuedPanel);
		
		JPanel killedPanel = 
				makeTokenDisplay(KILLED_WIDTH, KILLED_HEIGHT, "Killed");
		add(killedPanel);
	}
	
	/**
	 * Make a panel for displaying the rescued or killed POI tokens.
	 * 
	 * @param width the width of the table
	 * @param height the height of the table
	 */
	private JPanel makeTokenDisplay(int width, int height, String message) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(160, 10));
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setOpaque(false);
		panel.add(labelPanel);
		labelPanel.setLayout(new GridLayout(0,2,0,0));
		
		JLabel label = new JLabel(message + ":");
		label.setPreferredSize(new Dimension(10, 10));
		labelPanel.add(label);
		label.setFont(LABEL_FONT);
		label.setForeground(Color.WHITE);
		
		TokenGridPanel blanks = new TokenGridPanel(CELL_SIZE, GRID_MARGIN, 1, 1);
		blanks.setOpaque(false);
		labelPanel.add(blanks);
		
		TokenGridPanel tokenGrid = new TokenGridPanel(CELL_SIZE, GRID_MARGIN,
													  width, height);
		panel.add(tokenGrid);
		tokenGrid.setOpaque(false);
		return panel;
	}
	
	
	private static final long serialVersionUID = 1895792502596588154L;

	
}
