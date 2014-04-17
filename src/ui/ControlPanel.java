/**
 *  File name: ControlPanel.java
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

import game.token.PlayerToken;
import interfaces.DiceRollListener;
import interfaces.TurnTaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * A panel for showing information about the state of the game, and providing
 * interface elements for the player to input actions to the game.
 *
 * @author littlewoo
 */
public class ControlPanel extends JPanel {
	/** The object listening to this panel's end turn events */
	private TurnTaker turnTaker; 
	
	/** the panel displaying the results of the previous dice roll */
	private DicePanel dicePanel;
	
	/** the label displaying the current player's name */
	private JLabel currentPlayerLabel;
	
	/**
	 * Create a new ControlPanel 
	 * 
	 * @param turnTaker the listener which consumes this panel's 'end turn' 
	 * 					events
	 */
	public ControlPanel(TurnTaker turnTaker) {
		this.turnTaker = turnTaker;
		makePanel();
	}
	
	/**
	 * Construct the panel's GUI.
	 */
	private void makePanel() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(500, 100));
		setBorder(
			new CompoundBorder(
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				new EmptyBorder(5, 5, 5, 5)));
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel currentPlayerPanel = new JPanel();
		currentPlayerPanel.setBackground(Color.BLACK);
		currentPlayerPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Current Player:", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		add(currentPlayerPanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_1.setBackground(Color.BLACK);
		currentPlayerPanel.add(panel_1);
		
		currentPlayerLabel = new JLabel("");
		currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(currentPlayerLabel);
		currentPlayerLabel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new EmptyBorder(0, 10, 4, 10)));
		currentPlayerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		currentPlayerLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		currentPlayerLabel.setOpaque(true);
		currentPlayerLabel.setBackground(Color.WHITE);
		
		updatePlayer();
		
		JPanel actionPanel = new JPanel();
		actionPanel.setBackground(Color.BLACK);
		actionPanel.setBorder(new TitledBorder(null, "Actions", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		add(actionPanel);
		actionPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endTurn();
			}
		});
		actionPanel.add(endTurnButton);
		
		dicePanel = new DicePanel();
		
		GridLayout gridLayout = (GridLayout) dicePanel.getLayout();
		gridLayout.setColumns(2);
		gridLayout.setRows(0);
		add(dicePanel);
	}
	
	/**
	 * Get the dice roll listener from this panel. This panel includes a
	 * listener which listens for dice roll events, and then displays the result.
	 * @return the listener
	 */
	public DiceRollListener getDiceRollListener() {
		return dicePanel;
	}
	
	/**
	 * Update the view of who the current player is. 
	 */
	private void updatePlayer() {
		PlayerToken t = turnTaker.getCurrentPlayer();
		currentPlayerLabel.setBackground(t.getColour());
		currentPlayerLabel.setText(t.getName());
	}

	/**
	 * End the current turn
	 */
	private void endTurn() {
		turnTaker.onEndTurn();
		updatePlayer();
	}
	
	private static final long serialVersionUID = 1895792502596588154L;

}
