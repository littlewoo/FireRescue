package ui.playersDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import java.awt.ComponentOrientation;
import java.awt.Point;

public class PlayerInfoDialog extends JDialog {
	private JPanel playersPanel;
	private List<PlayerPanel> playerPanels;
	private List<OkListener> okListeners;
	
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
		playerCount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int value = (Integer) playerCount.getSelectedItem();
				setPlayerCount(value);
			}
		});
		playerCount.setSelectedIndex(0);
		infoPanel.add(playerCount);
		
		pack();
	}
	
	protected List<PlayerInputData> gatherPlayerData() {
		List<PlayerInputData> playerData = new ArrayList<PlayerInputData>();
		for (PlayerPanel pp : playerPanels) {
			playerData.add(pp.getPlayer());
		}
		return playerData;
	}

	private void setPlayerCount(int count) {
		playersPanel.removeAll();
		playersPanel.setLayout(new GridLayout(count,1));
		List<ColourElement> colours = ColourElement.getDefaultColours();
		playerPanels = new ArrayList<>();
		for (int i=0; i<count; i++) {
			PlayerPanel pp = new PlayerPanel(colours);
			playersPanel.add(pp);
			playerPanels.add(pp);
		}
		
		pack();
	}
	
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
	
	public void alertOkListeners(List<PlayerInputData> data) {
		if (okListeners != null) {
			for (OkListener l : okListeners) {
				l.onOk(data);
			}
		}
	}

	public void addOkListener(OkListener listener) {
		if (okListeners == null) {
			okListeners = new ArrayList<OkListener>();	
		}
		okListeners.add(listener);
	}
	
	public interface OkListener {
		public void onOk(List<PlayerInputData> data);
	}
	
	private static final long serialVersionUID = 5264316607699504785L;
}
