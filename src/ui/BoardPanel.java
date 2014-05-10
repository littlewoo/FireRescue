/**
 *  File name: BoardPanel.java
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

import game.Board.TokenChangeEvent;
import game.Board.TokenChangeListener;
import game.Game;
import game.action.Action;
import game.action.Action.ActionType;
import game.action.ActionCollection;
import game.token.MovableToken;
import interfaces.ActionPerformer;
import interfaces.ActionView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import ui.drawing.ActionPainter;

/**
 * The panel showing the board.
 * 
 * @author littlewoo
 */
public class BoardPanel extends TokenGridPanel 
						implements TokenChangeListener, ActionView {
	
	private static final long serialVersionUID = 6945410881583290262L;
	
	/** The width and height of the board (in squares) */
	private final static int WIDTH = Game.WIDTH;
	private final static int HEIGHT = Game.HEIGHT;
	
	/** the size of one of the squares on the board, in pixels */
	public final static int CELL_SIZE = 95;
	/** the size of the margins around the board */
	private final static int MARGIN_SIZE = 25;
	
	/** the painter which paints possible actions onto the panel */
	private final ActionPainter actionPainter;
	/** The list of currently available actions */
	private ActionCollection actions;
	/** the performer which performs actions for this panel */
	private final ActionPerformer actionPerformer;
	/** default action on left click */
	private final static ActionType DEFAULT_ACTION = ActionType.MOVE; 
	
	/**
	 * Make a new BoardPanel
	 * 
	 * @param game the game represented by this board
	 */
	public BoardPanel(Game game) {
		super(CELL_SIZE, MARGIN_SIZE, WIDTH, HEIGHT);
		setOpaque(true);
		setBorder(null);
		actionPainter = new ActionPainter();
		actionPerformer = game;
		
		setBackground(new Color(160, 82, 45));
		setForeground(Color.WHITE);
		setOpaque(true);
				
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				respondToMouseClick(e.getX(), e.getY(), e.getButton());
			}			
		});
		
		game.addTokenChangeListener(this);
		game.placeWalls();
		game.placePlayers();
	}
	
	/**
	 * Paint this component. Draw the grid as currently stored.
	 * 
	 * @param g the graphics instance to draw onto
	 */
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Painting board panel");
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        actionPainter.paintAll(g2);
    }
	
	/**
	 * Respond to an ordinary mouse click, by calculating the square in which 
	 * the click came, and then signalling listeners of the selected square.
	 * 
	 * @param x the x coordinate within the panel of the click
	 * @param y the y coordinate within the panel of the click
	 * @param button the button used to click
	 */
	private void respondToMouseClick(int x, int y, int button) {
		actionPainter.clearActions();
		if (x < leftMargin || x > rightMargin || 
			y < topMargin || y > bottomMargin) {
			System.out.println("Click outside boundaries: " + x + ", " + y);
		} else {
			x = x - leftMargin;
			y = y - topMargin;
			int xSquare = x / CELL_SIZE;
			int ySquare = y / CELL_SIZE;
			Point p = new Point(xSquare, ySquare);
			List<Action> acts = actions.getActions(p);
			if (acts != null && acts.size() > 0) {
				if (button == MouseEvent.BUTTON1) {
					for (Action a : acts) {
						if (a.getType() == DEFAULT_ACTION) {
							performAction(a);
							return;
						}
					}
				}			
				showActionMenu(acts, x, y);
			}
		}
	}
	
	/** 
	 * Perform a given action
	 * 
	 * @param a the action to perform
	 */
	private void performAction(Action a) {
		actionPainter.clearActions();
		actionPerformer.performAction(a);
	}

	/**
	 * Show a menu of possible actions for a square
	 * 
	 * @param actions the possible actions
	 * @param x the x coordinate to display the menu
	 * @param y the y coordinate to display the menu
	 */
	private void showActionMenu(List<Action> actions, int x, int y) {
		if (actions != null && actions.size() > 0) {
			JPopupMenu menu = new JPopupMenu();
			for (final Action a : actions) {
				String text = a.getType().getName() + " - " + 
			                  a.getApCost() + "AP";
				JMenuItem item = menu.add(text);
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						performAction(a);
					}
				});
				
			}
			menu.show(this, x, y);
		}
	}

	/**
	 * Respond to a token change event. The action taken depends on the type of 
	 * change: adding a token to the drawing manager or removing it. 
	 * 
	 * @param e the token change event
	 */
	@Override
	public void onTokenChange(TokenChangeEvent e) {		
		Point p = new Point(e.getX(), e.getY());
		switch (e.getChange()) {
			case ADD:
				super.addToken(e.getToken(), p);
				break;
			case REMOVE:
				super.removeToken(e.getToken());
				break;
			case MOVE:
				super.moveToken((MovableToken) e.getToken(), p);
				break;
		}
		
		repaint();
	}

	/* (non-Javadoc)
	 * @see interfaces.ActionView#displayActions(java.util.List)
	 */
	@Override
	public void displayActions(ActionCollection actions) {
		actionPainter.clearActions();
		this.actions = actions;
		for (Action a : actions.getActions()) {
			Point p = getCellLoc(a.getLoc());
			actionPainter.addAction(a, p);
		}
		repaint();
	}
}
