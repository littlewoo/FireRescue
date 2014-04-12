package ui;

import game.Board;
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

import ui.drawing.TokenDrawingManager;

/**
 * The panel showing the board.
 * 
 * @author littlewoo
 */
public class BoardPanel extends JPanel implements TokenChangeListener {
	
	private static final long serialVersionUID = 6945410881583290262L;
	
	private final static int WIDTH = 10;
	private final static int HEIGHT = 8;
	
	public final static int CELL_SIZE = 100;
	private final static int MARGIN_SIZE = 25;
	private final static int LEFT_MARGIN = MARGIN_SIZE;
	private final static int RIGHT_MARGIN = MARGIN_SIZE + CELL_SIZE * WIDTH;
	private final static int TOP_MARGIN = MARGIN_SIZE;
	private final static int BOTTOM_MARGIN = MARGIN_SIZE + CELL_SIZE * HEIGHT;
	
	public final static int LEFT_MOUSE_BUTTON = MouseEvent.BUTTON1;
	public final static int MIDDLE_MOUSE_BUTTON = MouseEvent.BUTTON2;
	public final static int RIGHT_MOUSE_BUTTON = MouseEvent.BUTTON3;
	
	private List<SelectSquareListener> selectSquareListeners;
	
	private final TokenDrawingManager tokenDrawingManager;
	
	/**
	 * Make a new BoardPanel
	 */
	public BoardPanel(Game game) {
		
		setPreferredSize(
				new Dimension(MARGIN_SIZE + WIDTH * CELL_SIZE + MARGIN_SIZE, 
							  MARGIN_SIZE + HEIGHT * CELL_SIZE + MARGIN_SIZE));
		setBackground(new Color(160, 82, 45));
		
		selectSquareListeners = new ArrayList<SelectSquareListener>();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				respondToMouseClick(e.getX(), e.getY(), e.getButton());
			}			
		});
		
		Board b = game.getBoard();
		b.addTokenChangeListener(this);
		addSelectSquareListener(b);
		
		tokenDrawingManager = new TokenDrawingManager();
		b.placePlayerToken();
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
        tokenDrawingManager.drawAll(g2);
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
	private void alertSelectSquareListeners(int x, int y, int button) {
		for (SelectSquareListener l : selectSquareListeners) {
			l.onSelectSquare(x, y, button);
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

	@Override
	public void onTokenChange(TokenChangeEvent e) {
		int xLoc = LEFT_MARGIN + CELL_SIZE * e.getX() + CELL_SIZE / 2;
		int yLoc = TOP_MARGIN + CELL_SIZE * e.getY() + CELL_SIZE / 2;
		
		switch (e.getChange()) {
			case ADD:
				tokenDrawingManager.addToken(e.getToken(), xLoc, yLoc);
				break;
			case REMOVE:
				tokenDrawingManager.removeToken(e.getToken());
				break;
			case MOVE:
				tokenDrawingManager.updateTokenLocation(
						e.getToken(), e.getX(), e.getY());
				break;
		}
		
		repaint();
	}
}
