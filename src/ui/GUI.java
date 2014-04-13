package ui;

import game.Game;
import game.token.PlayerToken;

import java.util.List;

import ui.playersDialog.PlayerInfoDialog;
import ui.playersDialog.PlayerInfoDialog.OkListener;
import ui.playersDialog.PlayerInputData;

/**
 * The graphical user interface for the game.
 * 
 * @author littlewoo
 */
public class GUI {
	
	/**
	 * Construct a new GUI.
	 */
	public GUI() {
		PlayerInfoDialog pid = new PlayerInfoDialog(6);
		pid.addOkListener(new OkListener() {
			@Override
			public void onOk(List<PlayerInputData> data) {
				new GameFrame(new Game(data));
			}
		});
	}

	public static void main(String[] args) {
		new GUI();
	}
	
	public interface TurnTaker {
		public void onEndTurn();
		
		public PlayerToken getCurrentPlayer();
	}
}
