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

public class ControlPanel extends JPanel {
	private TurnTaker turnTaker; 
	
	private JLabel currentPlayerLabel;
	
	private DiceRollListener diceRollerListener;
	
	public ControlPanel(TurnTaker turnTaker) {
		this.turnTaker = turnTaker;
		makePanel();
	}
	
	private void makePanel() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(500, 100));
		setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(5, 5, 5, 5)));
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
		
		DicePanel dicePanel = new DicePanel();
		diceRollerListener = dicePanel;
		
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
		return diceRollerListener;
	}
	
	private void updatePlayer() {
		PlayerToken t = turnTaker.getCurrentPlayer();
		currentPlayerLabel.setBackground(t.getColour());
		currentPlayerLabel.setText(t.getName());
	}

	private void endTurn() {
		turnTaker.onEndTurn();
		updatePlayer();
	}
	
	private static final long serialVersionUID = 1895792502596588154L;

}
