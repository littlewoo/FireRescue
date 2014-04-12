package ui.playersDialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayersPanel extends JPanel {
	private static final long serialVersionUID = -3487717973647209369L;
	private static final List<ColourElement> COLOURS;
	private static final ColourElement BLACK_ELEM = new ColourElement(Color.BLACK, Color.WHITE, "Black");
	private static final ColourElement BLUE_ELEM = new ColourElement(Color.BLUE, Color.WHITE, "Blue");
	private static final ColourElement GREEN_ELEM = new ColourElement(Color.GREEN, Color.BLACK, "Green");
	private static final ColourElement YELLOW_ELEM = new ColourElement(Color.YELLOW, Color.BLACK, "Yellow");
	private static final ColourElement ORANGE_ELEM = new ColourElement(new Color(255,127,0), Color.BLACK, "Orange");	
	private static final ColourElement RED_ELEM = new ColourElement(Color.RED, Color.WHITE, "Red");
	
	private static int maxPlayers;
	private static List<PlayerCountChangeListener> playerCountChangeListeners;
	
	static {
		COLOURS = new ArrayList<ColourElement>();
		COLOURS.add(BLACK_ELEM);
		COLOURS.add(BLUE_ELEM);
		COLOURS.add(GREEN_ELEM);
		COLOURS.add(ORANGE_ELEM);
		COLOURS.add(YELLOW_ELEM);
		COLOURS.add(RED_ELEM);
	}
	
	private PlayersPanel nextPanel;
	private JTextField txtPlayerName;
	private ColourComboBox cboColour;
	
	
	private PlayersPanel(final Container con, final List<ColourElement> colours, final int num) {
		
		JLabel lblName = new JLabel("Name:");
		this.add(lblName);
		
		txtPlayerName = new JTextField();
		txtPlayerName.setPreferredSize(new Dimension(150, 20));
		this.add(txtPlayerName);
		
		JLabel lblColour = new JLabel("Colour:");
		this.add(lblColour);
		
		cboColour = new ColourComboBox(coloursToArray(colours)); 
		this.add(cboColour);
		
		JButton nextButton = new JButton("Confirm");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNextPanel(con, colours, num);
			}
		});
		this.add(nextButton);
	}	
	
	protected void createNextPanel(Container con, 
								   List<ColourElement> colours, 
								   int num) {
		System.out.println("Creating next panel...");
		if (nextPanel == null) {
			ColourElement selectedColour = (ColourElement) cboColour.getSelectedItem();
			List<ColourElement> newColours = new ArrayList<ColourElement>();
			for (ColourElement ce : colours) {
				if (ce != selectedColour) {
					newColours.add(ce);
				}
			}
			nextPanel = new PlayersPanel(con, newColours, num-1);
			System.out.println("Done. Adding to container...");
			con.add(nextPanel);
			nextPanel.setVisible(true);
			//con.revalidate();
			System.out.println("Done. Alerting listeners.");
			alertPlayerCountChangeListeners(num);
		}
	}

	public static PlayersPanel getFirst(Container con, int maxPlayerCount) {
		maxPlayers = maxPlayerCount;
		return new PlayersPanel(con, COLOURS, maxPlayers);
	}	
	
	public List<PlayerInputData> getPlayers() {
		List<PlayerInputData> result;
		if (nextPanel == null) {
			result = new ArrayList<PlayerInputData>();
		} else {
			result = nextPanel.getPlayers();
		}
		String name = txtPlayerName.getText();
		Color colour = cboColour.getColour();
		result.add(new PlayerInputData(name, colour));
		return result;
	}
	
	private ColourElement[] coloursToArray(List<ColourElement> colours) {
		ColourElement[] vals = new ColourElement[COLOURS.size()];
		return colours.toArray(vals);
	}
	
	public void addPlayerCountChangeListener(
			PlayerCountChangeListener listener) {
		if (playerCountChangeListeners == null) {
			playerCountChangeListeners = 
					new ArrayList<PlayerCountChangeListener>();
		}
		playerCountChangeListeners.add(listener);
	}
	
	private void alertPlayerCountChangeListeners(int num) {
		for (PlayerCountChangeListener listener : playerCountChangeListeners) {
			listener.onPlayerCountChange(maxPlayers - num);
		}
	}
	
	public interface PlayerCountChangeListener {
		public void onPlayerCountChange(int playerCount);
	}
}
