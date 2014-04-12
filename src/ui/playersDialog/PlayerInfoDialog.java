package ui.playersDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import ui.GUI;
import ui.playersDialog.PlayersPanel.PlayerCountChangeListener;
import java.awt.GridLayout;

public class PlayerInfoDialog extends JDialog {
	public PlayerInfoDialog(int maxPlayers) {
		setTitle("Add Players...");
		setVisible(true);
		setModal(true);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		final JLabel lblProceedWithPlayers = 
				new JLabel("Please enter a player name to proceed");
		buttonPanel.add(lblProceedWithPlayers);
		
		JButton okButton = new JButton("OK");
		buttonPanel.add(okButton);
		
		JPanel playerPanels = new JPanel();
		playerPanels.setBorder(new TitledBorder(null, "Player", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(playerPanels, BorderLayout.CENTER);
		playerPanels.setLayout(new FlowLayout());
		
		PlayersPanel playerPanel = PlayersPanel.getFirst(playerPanels, maxPlayers);
		
		playerPanel.addPlayerCountChangeListener(
				new PlayerCountChangeListener() {
					@Override
					public void onPlayerCountChange(int playerCount) {
						System.out.println("Player count changed...");
						lblProceedWithPlayers.setText(
								"Proceed with " + playerCount + "players:");
						pack();
					}
		});
		
		playerPanels.add(playerPanel);
		
		JPanel infoPanel = new JPanel();
		getContentPane().add(infoPanel, BorderLayout.NORTH);
		
		JLabel infoLabel = new JLabel("Please add a player:");
		infoLabel.setFont(new Font(GUI.FONT_NAME, Font.PLAIN, 14));
		infoPanel.add(infoLabel);
		
		pack();
	}


	private static final long serialVersionUID = 5264316607699504785L;

}
