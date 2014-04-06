package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * The panel showing the board.
 * 
 * @author littlewoo
 */
public class BoardPanel extends JPanel {
	
	private static final long serialVersionUID = 6945410881583290262L;
	
	private final static int WIDTH = 10;
	private final static int HEIGHT = 8;
	
	private final static int CELL_SIZE = 100;
	private final static int MARGIN_SIZE = 25;
	private final static int LEFT_MARGIN = MARGIN_SIZE;
	private final static int RIGHT_MARGIN = MARGIN_SIZE + CELL_SIZE * WIDTH;
	private final static int TOP_MARGIN = MARGIN_SIZE;
	private final static int BOTTOM_MARGIN = MARGIN_SIZE + CELL_SIZE * HEIGHT;
	
	private List<SelectSquareListener> selectSquareListeners;
	
	/**
	 * Make a new BoardPanel
	 */
	public BoardPanel() {
		
		setPreferredSize(
				new Dimension(MARGIN_SIZE + WIDTH * CELL_SIZE + MARGIN_SIZE, 
							  MARGIN_SIZE + HEIGHT * CELL_SIZE + MARGIN_SIZE));
		setBackground(new Color(160, 82, 45));
		
		selectSquareListeners = new ArrayList<SelectSquareListener>();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					respondToMouseClick(e.getX(), e.getY());
				}
			}			
		});
	}
	
	/**
	 * Paint this component. Draw the grid as currently stored.
	 * 
	 */
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawLines(g2);
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
	 */
	private void respondToMouseClick(int x, int y) {
		if (x < LEFT_MARGIN || x > RIGHT_MARGIN || 
			y < TOP_MARGIN || y > BOTTOM_MARGIN) {
			System.out.println("Click outside boundaries: " + x + ", " + y);
		} else {
			x = x - LEFT_MARGIN;
			y = y - TOP_MARGIN;
			int xSquare = x / CELL_SIZE;
			int ySquare = y / CELL_SIZE;
			alertSelectSquareListeners(xSquare, ySquare);
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
	 */
	private void alertSelectSquareListeners(int x, int y) {
		for (SelectSquareListener l : selectSquareListeners) {
			l.onSelectSquare(x, y);
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
		 */
		public void onSelectSquare(int x, int y);
	}
}
