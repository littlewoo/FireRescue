/**
 *  File name: GUI.java
 *
 *  Copyright 2014: John Littlewood
 *
 *  This file is part of FireRescue.
 *
 *  FireRescue is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FireRescue is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FireRescue.  If not, see <http://www.gnu.org/licenses/>.
 */
package ui;

import game.Game;

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
	 * Construct a new GUI. Displays the player info dialog, and attaches its
	 * input to a new game.
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
}
