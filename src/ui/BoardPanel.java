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

import game.Action;
import game.Action.ActionType;
import game.ActionCollection;
import game.Board.TokenChangeEvent;
import game.Board.TokenChangeListener;
import game.Game;
import game.token.MovableToken;
import interfaces.ActionPerformer;
import interfaces.ActionView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import ui.drawing.ActionPainter;
import ui.drawing.TokenPaintingManager;

/**
 * The panel showing the board.
 * 
 * @author littlewoo
 */
public class BoardPanel extends JPanel 
						implements TokenChangeListener, ActionView {
	
	private static final long serialVersionUID = 6945410881583290262L;
	
	/** The width and height of the board (in squares) */
	private final static int WIDTH = Game.WIDTH;
	private final static int HEIGHT = Game.HEIGHT;
	
	/** the size of one of the squares on the board, in pixels */
	public final static int CELL_SIZE = 95;
	/** the size of the margins around the board */
	private final static int MARGIN_SIZE = 25;
	/** the x value for the left margin */
	private final static int LEFT_MARGIN = MARGIN_SIZE;
	/** the x value for the right margin */
	private final static int RIGHT_MARGIN = MARGIN_SIZE + CELL_SIZE * WIDTH;
	/** the y value for the top margin */
	private final static int TOP_MARGIN = MARGIN_SIZE;
	/** the y value for the bottom margin */
	private final static int BOTTOM_MARGIN = MARGIN_SIZE + CELL_SIZE * HEIGHT;
	
	/** the manager responsible for drawing tokens */
	private final TokenPaintingManager tokenPaintingManager;
	
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
		setBorder(null);
		tokenPaintingManager = new TokenPaintingManager();
		actionPainter = new ActionPainter();
		actionPerformer = game;
		
		setPreferredSize(
				new Dimension(MARGIN_SIZE + WIDTH * CELL_SIZE + MARGIN_SIZE, 
							  MARGIN_SIZE + HEIGHT * CELL_SIZE + MARGIN_SIZE));
		setBackground(new Color(160, 82, 45));
		
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
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        drawLines(g2);
        tokenPaintingManager.drawAll(g2);
        actionPainter.paintAll(g2);
    }
	
	/**
	 * Draw the lines of the grid of the gameboard.
	 * @param g
	 */
	private void drawLines(Graphics2D g) {
		g.setColor(Color.WHITE);
		
		for (int i=0; i<WIDTH; i++) {
			int x = LEFT_MARGIN + i * CELL_SIZE;
			int y1 = TOP_MARGIN;
			int y2 = BOTTOM_MARGIN;
			g.drawLine(x, y1, x, y2);
		}
		for (int j=0; j<HEIGHT; j++) {
			int y = TOP_MARGIN + j * CELL_SIZE;
			int x1 = LEFT_MARGIN;
			int x2 = RIGHT_MARGIN;
			g.drawLine(x1, y, x2, y);
		}
		
		g.drawLine(RIGHT_MARGIN, TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN);
		g.drawLine(LEFT_MARGIN, BOTTOM_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN);
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
		if (x < LEFT_MARGIN || x > RIGHT_MARGIN || 
			y < TOP_MARGIN || y > BOTTOM_MARGIN) {
			System.out.println("Click outside boundaries: " + x + ", " + y);
		} else {
			x = x - LEFT_MARGIN;
			y = y - TOP_MARGIN;
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
		Point p = getCellLoc(e.getX(), e.getY());
		
		switch (e.getChange()) {
			case ADD:
				tokenPaintingManager.addToken(e.getToken(), p.x, p.y);
				break;
			case REMOVE:
				tokenPaintingManager.removeToken(e.getToken());
				break;
			case MOVE:
				tokenPaintingManager.updateTokenLocation(
						(MovableToken) e.getToken(), p.x, p.y);
				break;
		}
		
		repaint();
	}
	
	/**
	 * Convert a cell reference (x,y) into a pixel coordinate for the center of
	 * the cell
	 * 
	 * @param x the x coordinate of the cell
	 * @param y the y coordinate of the cell
	 * @return the centre location of a cell
	 */
	private Point getCellLoc(int x, int y) {
		int xLoc = LEFT_MARGIN + CELL_SIZE * x + CELL_SIZE / 2;
		int yLoc = TOP_MARGIN + CELL_SIZE * y + CELL_SIZE / 2;
		return new Point(xLoc, yLoc);
	}
	

	/* (non-Javadoc)
	 * @see interfaces.ActionView#displayActions(java.util.List)
	 */
	@Override
	public void displayActions(ActionCollection actions) {
		actionPainter.clearActions();
		this.actions = actions;
		for (Action a : actions.getActions()) {
			Point p = getCellLoc(a.getX(), a.getY());
			actionPainter.addAction(a, p);
		}
		repaint();
	}
}
