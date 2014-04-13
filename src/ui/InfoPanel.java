package ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class InfoPanel extends JPanel {
	public InfoPanel() {
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(200, 10));
		setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EmptyBorder(5, 5, 5, 5)));
		setLayout(new BorderLayout(0, 0));
	}
	private static final long serialVersionUID = 1895792502596588154L;

}
