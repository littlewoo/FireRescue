/**
 *  File name: PlayerInfoDialog.java
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * A dialog box for gathering input on the players playing the game. Gathers the
 * number of players, the names of each player, and the colour of each player
 *
 * @author littlewoo
 */
public class PlayerInfoDialog extends JDialog {
	// TODO: don't release it while this is here
	private static final boolean RELEASABLE = false;
	
	/** the panel for the player details */
	private JPanel playersPanel;
	/** the individual panels for each player */
	private List<PlayerPanel> playerPanels;
	/** listeners for when the ok button is clicked */
	private List<OkListener> okListeners;
	
	/**
	 * Construct a new dialog. 
	 * 
	 * @param maxPlayers the maximum number of players
	 */
	public PlayerInfoDialog(int maxPlayers) {
		setLocation(new Point(300, 100));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setTitle("Add Players...");
		setVisible(true);
		setModal(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout());
		
		final JLabel lblMessage = new JLabel();
		buttonPanel.add(lblMessage, BorderLayout.CENTER);
		
		JButton okButton = new JButton("OK");
		okButton.setPreferredSize(new Dimension(45, 30));
		okButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<PlayerInputData> playerData = gatherPlayerData();
				if (checkValidInput(playerData)) {
					alertOkListeners(playerData);
					setVisible(false);
				} else {
					lblMessage.setText(
						"All the players need names and colours which have " +
						"to be unique.");
					pack();
				}
			}
		});
		buttonPanel.add(okButton, BorderLayout.EAST);
		
		playersPanel = new JPanel();
		playersPanel.setBorder(new TitledBorder(null, "Players", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(playersPanel, BorderLayout.CENTER);
		
		JPanel infoPanel = new JPanel();
		getContentPane().add(infoPanel, BorderLayout.NORTH);
		
		JLabel infoLabel = new JLabel("Number of players:");
		infoPanel.add(infoLabel);
		
		final JComboBox<Integer> playerCount = new JComboBox<>();
		playerCount.setPreferredSize(new Dimension(40, 20));
		playerCount.setEditable(true);
		for (int i=1; i<=maxPlayers; i++) {
			playerCount.addItem(i);
		}
		if (!RELEASABLE) {
			playerCount.addItem(-1);
		}
		playerCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int value = (Integer) playerCount.getSelectedItem();
				// TODO: remove this when we no longer need test data
				if (!RELEASABLE && value == -1) {
					setPlayerCount(5);
					fillTestData();
				} else {
					setPlayerCount(value);
				}
			}
		});
		playerCount.setSelectedItem(-1);
		infoPanel.add(playerCount);
		
		pack();
	}
	
	/** 
	 * @return the data on all the players, input by the form
	 */
	protected List<PlayerInputData> gatherPlayerData() {
		List<PlayerInputData> playerData = new ArrayList<PlayerInputData>();
		for (PlayerPanel pp : playerPanels) {
			playerData.add(pp.getPlayer());
		}
		return playerData;
	}

	/**
	 * Set the number of players
	 * 
	 * @param count the number of players
	 */
	private void setPlayerCount(int count) {
		playersPanel.removeAll();
		playersPanel.setLayout(new GridLayout(count,1));
		Collection<ColourElement> colours = ColourElement.getDefaultColours();
		playerPanels = new ArrayList<>();
		for (int i=0; i<count; i++) {
			PlayerPanel pp = new PlayerPanel(colours);
			playersPanel.add(pp);
			playerPanels.add(pp);
		}
		
		pack();
	}
	
	/**
	 * Fill the dialog with test data
	 * 
	 * TODO: remove this
	 */
	private void fillTestData() {
		List<ColourElement> colours = ColourElement.COLOURS;
		playerPanels.get(0).setTestData("John", colours.get(3));
		playerPanels.get(1).setTestData("Esther", colours.get(4));
		playerPanels.get(2).setTestData("Elanor", colours.get(5));
		playerPanels.get(3).setTestData("James", colours.get(1));
		playerPanels.get(4).setTestData("William", colours.get(2));
		
	}

	/**
	 * Check that the player data is valid
	 * 
	 * @param playerData the data to check
	 * @return true if the data is valid
	 */
	private boolean checkValidInput(List<PlayerInputData> playerData) {
		Set<String> names = new HashSet<String>();
		Set<Color> colours = new HashSet<Color>();
		try {
			for (PlayerInputData p : playerData) {
				if (p.name.equals("")) {
					throw new IllegalArgumentException("No name provided");
				}
				if (names.contains(p.name)) {
					throw new IllegalArgumentException("Non-unique name");
				}
				if (colours.contains(p.colour)){
					throw new IllegalArgumentException("Non-unique name");
				}
				names.add(p.name);
				colours.add(p.colour);
			}
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;		
	}
	
	/**
	 * Alert listeners that the ok has been clicked
	 * 
	 * @param data
	 */
	public void alertOkListeners(List<PlayerInputData> data) {
		if (okListeners != null) {
			for (OkListener l : okListeners) {
				l.onOk(data);
			}
		}
	}

	/**
	 * Add a listener for listening to when the ok button is clicked
	 * 
	 * @param listener
	 */
	public void addOkListener(OkListener listener) {
		if (okListeners == null) {
			okListeners = new ArrayList<OkListener>();	
		}
		okListeners.add(listener);
	}
	
	/**
	 * A listener which is alerted when the ok button is clicked
	 *
	 * @author littlewoo
	 */
	public interface OkListener {
		/**
		 * Called when the ok button on the PlayerInfoDialog is clicked
		 * 
		 * @param data
		 */
		public void onOk(List<PlayerInputData> data);
	}
	
	private static final long serialVersionUID = 5264316607699504785L;
}
