package ui;

import game.Game;

import java.util.List;

import javax.swing.JFrame;

import ui.playersDialog.PlayerInfoDialog;
import ui.playersDialog.PlayerInfoDialog.OkListener;
import ui.playersDialog.PlayerInputData;

/**
 * The graphical user interface for the game.
 * 
 * @author littlewoo
 */
public class GUI {
	public final static String FONT_NAME = "Trebuchet MS";
	
	/**
	 * Construct a new GUI.
	 */
	public GUI() {
		PlayerInfoDialog pid = new PlayerInfoDialog(6);
		pid.addOkListener(new OkListener() {
			@Override
			public void onOk(List<PlayerInputData> data) {
				makeGui(new Game(data));
			}
		});
	}

	protected void makeGui(Game game) {
		JFrame frame = new JFrame();
		BoardPanel panel = new BoardPanel(game);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
