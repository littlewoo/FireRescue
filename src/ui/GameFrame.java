/**
 *  File name: GameFrame.java
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
import interfaces.POIEventListener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The main frame used in the user interface of the game.
 *
 * @author littlewoo
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = -4898631063836444714L;
	
	/** The control panel portion of the ui */
	private ControlPanel controlPanel;
	/** the board panel */
	private BoardPanel boardPanel;

	/**
	 * Create a new game frame, for a given game
	 * 
	 * @param game the game represented by this frame
	 */
	public GameFrame(Game game) {
		Container con = getContentPane();
		controlPanel = new ControlPanel(game);
		con.add(controlPanel, BorderLayout.SOUTH);
		
		TurnPhaseView tpv = controlPanel.getTurnPhaseView();
		TurnPhaseHandler tph = new TurnPhaseHandler(tpv);
		game.addTurnPhaseListener(tph);
		
		game.addDiceRollListener(controlPanel.getDiceRollListener());
		
		APView apv = controlPanel.getAPView();
		APHandler aph = new APHandler(apv);
		game.addAPListener(aph);
		
		game.addPOIEventListener(new POIEventListener() {
			private int rescued = 0;
			private int killed = 0;
			
			@Override
			public void onPOIEvent(POIEvent e) {
				switch (e.type) {
					case RESCUED:
						rescued ++;
						System.out.println("Rescued: " + rescued);
						break;
					case KILLED:
						killed ++;
						System.out.println("Killed: " + killed);
						break;
					default:
						break;
				}
			}
		});
		
		InfoPanel info = new InfoPanel();
		con.add(info, BorderLayout.EAST);

		boardPanel = new BoardPanel(game);
		boardPanel.setPreferredSize(new Dimension(1000, 850));
		con.add(boardPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		game.addActionView(boardPanel);
		game.placeInitialPOITokens();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		mnGame.setMnemonic(KeyEvent.VK_G);
		menuBar.add(mnGame);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.setMnemonic(KeyEvent.VK_N);
		mnGame.add(mntmNew);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setMnemonic(KeyEvent.VK_Q);
		mnGame.add(mntmQuit);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.setMnemonic(KeyEvent.VK_A);
		mnHelp.add(mntmAbout);
		setVisible(true);
	}
}
