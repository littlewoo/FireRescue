package ui;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = -4898631063836444714L;
	
	private InfoPanel infoPanel;
	private BoardPanel boardPanel;

	public GameFrame(Game game) {
		
		infoPanel = new InfoPanel();
		getContentPane().add(infoPanel, BorderLayout.EAST);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		boardPanel = new BoardPanel(game);
		boardPanel.setPreferredSize(new Dimension(1000, 850));
		getContentPane().add(boardPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
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
