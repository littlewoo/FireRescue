package ui.playersDialog;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ColourComboBox extends JComboBox<ColourElement> {
	private static final long serialVersionUID = 5768939185265281650L;	
	
	
	public ColourComboBox(ColourElement[] colours) {
		super(colours);
		setEditable(false);
		
		setRenderer(new ListCellRenderer<ColourElement>() {
			@Override
			public Component getListCellRendererComponent(
					JList<? extends ColourElement> list,
					ColourElement value, int index, 
					boolean isSelected, boolean cellHasFocus) {
				return value;
			}
		});
	}


	public Color getColour() {
		ColourElement ce = (ColourElement) this.getSelectedItem();
		return ce.getColour();
	}
}
