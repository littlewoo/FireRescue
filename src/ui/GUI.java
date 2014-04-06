package ui;

import javax.swing.JFrame;

/**
 * The graphical user interface for the game.
 * 
 * @author littlewoo
 */
public class GUI {
	private JFrame frame;
	
	/**
	 * Construct a new GUI.
	 */
	public GUI() {
		frame = new JFrame();
		BoardPanel panel = new BoardPanel();
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
