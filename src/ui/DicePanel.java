/**
 *  File name: DicePanel.java
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

import game.DiceRoller.DieResult;
import interfaces.DiceRollListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * A panel for displaying the results of a dice roll.
 *
 * @author littlewoo
 */
public class DicePanel extends JPanel implements DiceRollListener {
	private static final long serialVersionUID = -40971945906937999L;
	
	private JLabel d6;
	private JLabel d8;

	/**
	 * Create a new DicePanel.
	 */
	public DicePanel() {
		setBorder(new TitledBorder(null, "DieResult", TitledBorder.LEADING, TitledBorder.TOP, null, Color.WHITE));
		setPreferredSize(new Dimension(150, 80));
		setOpaque(false);
		setLayout(new GridLayout(2, 2, 0, 0));
		
		d6 = new JLabel("6");
		d6.setForeground(Color.RED);
		d6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		d6.setHorizontalTextPosition(SwingConstants.CENTER);
		d6.setHorizontalAlignment(SwingConstants.CENTER);
		add(d6);
		
		d8 = new JLabel("8");
		d8.setForeground(Color.WHITE);
		d8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		d8.setHorizontalTextPosition(SwingConstants.CENTER);
		d8.setHorizontalAlignment(SwingConstants.CENTER);
		add(d8);
	}

	/* (non-Javadoc)
	 * @see interfaces.DiceRollListener#diceRoll(game.DiceRoller.DieResult)
	 */
	@Override
	public void diceRoll(DieResult dieResult) {
		int sides = dieResult.sides;
		int val = dieResult.roll;
		if (sides == 6) {
			d6.setText("" + val);
		} else if (sides == 8) {
			d8.setText("" + val);
		} else {
			throw new IllegalArgumentException("DieResult not 6 or 8.");
		}
	}
}
