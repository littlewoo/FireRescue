package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	
	private static final long serialVersionUID = 6945410881583290262L;
	
	private final static int WIDTH = 10;
	private final static int HEIGHT = 8;
	
	private final static int CELL_SIZE = 100;
	private final static int MARGIN_SIZE = 25;
	
	public BoardPanel() {
		
		setPreferredSize(
				new Dimension(MARGIN_SIZE + WIDTH * CELL_SIZE + MARGIN_SIZE, 
							  MARGIN_SIZE + HEIGHT * CELL_SIZE + MARGIN_SIZE));
		setBackground(new Color(160, 82, 45));
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
	
	
	private void drawLines(Graphics2D g) {
		g.setColor(Color.WHITE);
		
		for (int i=0; i<WIDTH; i++) {
			int x = MARGIN_SIZE + i * CELL_SIZE;
			int y1 = MARGIN_SIZE;
			int y2 = MARGIN_SIZE + HEIGHT * CELL_SIZE;
			g.drawLine(x, y1, x, y2);
		}
		for (int j=0; j<HEIGHT; j++) {
			int y = MARGIN_SIZE + j * CELL_SIZE;
			int x1 = MARGIN_SIZE;
			int x2 = MARGIN_SIZE + WIDTH * CELL_SIZE;
			g.drawLine(x1, y, x2, y);
		}
		
		g.drawLine(MARGIN_SIZE+WIDTH*CELL_SIZE, MARGIN_SIZE, 
				MARGIN_SIZE+WIDTH*CELL_SIZE, MARGIN_SIZE+HEIGHT*CELL_SIZE);
		g.drawLine(MARGIN_SIZE, MARGIN_SIZE+HEIGHT*CELL_SIZE, 
				MARGIN_SIZE+WIDTH*CELL_SIZE, MARGIN_SIZE+HEIGHT*CELL_SIZE);
	}
}
