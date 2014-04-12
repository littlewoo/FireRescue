package ui;

import game.Game;

import javax.swing.JFrame;

import ui.playersDialog.PlayerInfoDialog;

/**
 * The graphical user interface for the game.
 * 
 * @author littlewoo
 */
public class GUI {
	private JFrame frame;
	public final static String FONT_NAME = "Trebuchet MS";
	
	/**
	 * Construct a new GUI.
	 */
	public GUI() {
		frame = new JFrame();
		BoardPanel panel = new BoardPanel(new Game());
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		new PlayerInfoDialog(6);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
