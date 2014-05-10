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

import game.token.BlankPOIToken;
import game.token.POIToken;
import interfaces.POIEventListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DebugGraphics;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel for displaying rescued victims, victims lost, and remaining POI Tokens
 * not yet placed on the board. 
 * 
 * @author littlewoo
 */
public class InfoPanel extends JPanel implements POIEventListener {
	
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
	
	/** the panels displaying token grids */
	private List<TokenGridPanel> tokenGrids;
	
	/** the display for rescued POIs */
	private RemovedPOITokenDisplay rescuedDisplay;
	/** the display for killed POIs */
	private RemovedPOITokenDisplay killedDisplay;
	
	public InfoPanel() {
		setLayout(new GridLayout(0, 1, 0, 0));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBackground(Color.BLACK);
		setOpaque(true);
		
		tokenGrids = new ArrayList<TokenGridPanel>();
		
		TokenGridPanel rescuedVictimsDisplay = 
				new TokenGridPanel(CELL_SIZE, GRID_MARGIN, RESCUED_WIDTH, RESCUED_HEIGHT);
		TokenGridPanel killedVictimsDisplay = 
				new TokenGridPanel(CELL_SIZE, GRID_MARGIN, KILLED_WIDTH, KILLED_HEIGHT);
		TokenGridPanel rescuedBlanksDisplay = 
				new TokenGridPanel(CELL_SIZE, GRID_MARGIN, 1, 1);
		TokenGridPanel killedBlanksDisplay = 
				new TokenGridPanel(CELL_SIZE, GRID_MARGIN, 1, 1);
		
		tokenGrids.add(rescuedVictimsDisplay);
		tokenGrids.add(killedVictimsDisplay);
		tokenGrids.add(rescuedBlanksDisplay);
		tokenGrids.add(killedBlanksDisplay);
		
		rescuedDisplay = new RemovedPOITokenDisplay(
											RESCUED_WIDTH, 
											RESCUED_HEIGHT, 
											rescuedVictimsDisplay, 
											rescuedBlanksDisplay);
		killedDisplay = new RemovedPOITokenDisplay(
											KILLED_WIDTH,
											KILLED_HEIGHT,
											killedVictimsDisplay,
											killedBlanksDisplay);
		
		
		JPanel rescuedPanel = 
				makeTokenDisplay("Rescued", rescuedBlanksDisplay, rescuedVictimsDisplay);
		add(rescuedPanel);
		
		JPanel killedPanel = 
				makeTokenDisplay("Killed", killedBlanksDisplay, killedVictimsDisplay);
		add(killedPanel);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("Painting InfoPanel");
		for (TokenGridPanel t : tokenGrids) {
			t.repaint();
		}
	}
	
	/**
	 * Make a panel for displaying the rescued or killed POI tokens.
	 * 
	 * @param message the label for the table
	 * @param blankPanel the panel for displaying blanks
	 * @param tablePanel the panel for displaying the rest of the tokens
	 */
	private JPanel makeTokenDisplay(String message,
									TokenGridPanel blankPanel,
									TokenGridPanel tablePanel) {
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
		
		blankPanel.setOpaque(false);
		labelPanel.add(blankPanel);
		
		panel.add(tablePanel);
		tablePanel.setOpaque(false);
		return panel;
	}
	
	/* (non-Javadoc)
	 * @see interfaces.POIEventListener#onPOIEvent(interfaces.POIEventListener.POIEvent)
	 */
	@Override
	public void onPOIEvent(POIEvent e) {
		System.out.println("POI event received by InfoPanel");
		POIToken t = e.token;
		switch (e.type) {
			case RESCUED:
				System.out.print("\tRescued ");
				if (t instanceof BlankPOIToken) {
					System.out.println("blank.");
					rescuedDisplay.addBlankToken();
				} else {
					System.out.println("victim.");
					rescuedDisplay.addToken(t);
				}
				break;
			case KILLED:
				System.out.print("\tKilled ");
				if (t instanceof BlankPOIToken) {
					System.out.println("blank.");
					killedDisplay.addBlankToken();
				} else {
					System.out.println("victim.");
					rescuedDisplay.addToken(t);
				}
				break;
			default:
				break;
		}
		
	}	
	
	private static final long serialVersionUID = 1895792502596588154L;	
}
