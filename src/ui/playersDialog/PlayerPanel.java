package ui.playersDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = -3487717973647209369L;
	private JTextField txtPlayerName;
	private ColourComboBox cboColour;	
	private List<ColourElement> colours;
	
	public PlayerPanel(List<ColourElement> colours) {		
		this.colours = colours;
		
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
	
	public List<ColourElement> getRemainingColours() {
		List<ColourElement> vals = new ArrayList<ColourElement>();
		Object selected = cboColour.getSelectedItem();
		for (ColourElement ce : colours) {
			if (selected != ce) {
				vals.add(ce);
			}
		}
		return vals;
	}
	
	public void setTestData(String name, ColourElement colour) {
		txtPlayerName.setText(name);
		cboColour.setSelectedItem(colour);
	}
}
