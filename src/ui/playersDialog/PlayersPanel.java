package ui.playersDialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayersPanel extends JPanel {
	private static final long serialVersionUID = -3487717973647209369L;
	private JTextField txtPlayerName;
	private ColourComboBox cboColour;
	
	
	public PlayersPanel(List<ColourElement> colours) {		
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
	
	public PlayerInputData getPlayer() {
		String name = txtPlayerName.getText();
		Color colour = cboColour.getColour();
		return new PlayerInputData(name, colour);
	}
	
	private ColourElement[] coloursToArray(List<ColourElement> colours) {
		ColourElement[] vals = new ColourElement[colours.size()];
		return colours.toArray(vals);
	}
}
