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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ui.drawing.TokenPaintingManager;

/**
 * The panel showing the board.
 * 
 * @author littlewoo
 */
public class BoardPanel extends JPanel implements TokenChangeListener {
	
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
	
	/** integer constant for the left mouse button */
	public final static int LEFT_MOUSE_BUTTON = MouseEvent.BUTTON1;
	/** integer constant for the middle mouse button */
	public final static int MIDDLE_MOUSE_BUTTON = MouseEvent.BUTTON2;
	/** integer constant for the right mouse button */
	public final static int RIGHT_MOUSE_BUTTON = MouseEvent.BUTTON3;
	
	/** the listeners which are listening for square selections */
	private List<SelectSquareListener> selectSquareListeners;
	
	/** the manager responsible for drawing tokens */
	private final TokenPaintingManager tokenPaintingManager;
	
	/**
	 * Make a new BoardPanel
	 * 
	 * @param game the game represented by this board
	 */
	public BoardPanel(Game game) {
		setBorder(null);
		tokenPaintingManager = new TokenPaintingManager();
		
		setPreferredSize(
				new Dimension(MARGIN_SIZE + WIDTH * CELL_SIZE + MARGIN_SIZE, 
							  MARGIN_SIZE + HEIGHT * CELL_SIZE + MARGIN_SIZE));
		setBackground(new Color(160, 82, 45));
		
		selectSquareListeners = new ArrayList<SelectSquareListener>();
		addSelectSquareListener(game);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				respondToMouseClick(e.getX(), e.getY(), e.getButton());
			}			
		});
		
		game.addTokenChangeListener(this);
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
		if (x < LEFT_MARGIN || x > RIGHT_MARGIN || 
			y < TOP_MARGIN || y > BOTTOM_MARGIN) {
			System.out.println("Click outside boundaries: " + x + ", " + y);
		} else {
			x = x - LEFT_MARGIN;
			y = y - TOP_MARGIN;
			int xSquare = x / CELL_SIZE;
			int ySquare = y / CELL_SIZE;
			alertSelectSquareListeners(xSquare, ySquare, button);
		}
	}
	
	/**
	 * Add a listener to listen to when a square has been selected, by e.g. 
	 * a mouse click or keyboard action.
	 * 
	 * @param listener the listener
	 */
	public void addSelectSquareListener(SelectSquareListener listener) {
		selectSquareListeners.add(listener);
	}
	
	/**
	 * Alert all listeners that a square has been selected
	 * 
	 * @param x the x index of the square
	 * @param y the y index of the square
	 * @param button the button used in the selection
	 */
	private void alertSelectSquareListeners(final int x, final int y, 
											final int button) {
		for (final SelectSquareListener l : selectSquareListeners) {
			Thread t = new Thread() {
				public void run() {
					l.onSelectSquare(x, y, button);
				}
			};
			t.start();
		}
	}
	
	/**
	 * A listener for events where a square is selected, e.g. by a mouse click
	 * or keyboard action.
	 * 
	 * @author littlewoo
	 */
	public interface SelectSquareListener {
		/**
		 * This method is called whenever a square is selected.
		 * 
		 * @param x the x index of the selected square
		 * @param y the y index of the selected square
		 * @param button the button used in selecting the square
		 */
		public void onSelectSquare(int x, int y, int button);
	}

	/**
	 * Respond to a token change event. The action taken depends on the type of 
	 * change: adding a token to the drawing manager or removing it. 
	 * 
	 * @param e the token change event
	 */
	@Override
	public void onTokenChange(TokenChangeEvent e) {
		int xLoc = LEFT_MARGIN + CELL_SIZE * e.getX() + CELL_SIZE / 2;
		int yLoc = TOP_MARGIN + CELL_SIZE * e.getY() + CELL_SIZE / 2;
		
		switch (e.getChange()) {
			case ADD:
				tokenPaintingManager.addToken(e.getToken(), xLoc, yLoc);
				break;
			case REMOVE:
				tokenPaintingManager.removeToken(e.getToken());
				break;
			case MOVE:
				tokenPaintingManager.updateTokenLocation(
						e.getToken(), e.getX(), e.getY());
				break;
		}
		
		repaint();
	}
}
