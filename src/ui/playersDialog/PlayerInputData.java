package ui.playersDialog;

import java.awt.Color;

/**
 * Data structure for holding data on a player. 
 * 
 * @author littlewoo
 */
public class PlayerInputData {
	public final String name;
	public final Color colour;
	
	/**
	 * 
	 * @param name the player's name
	 * @param colour the player's colour
	 */
	public PlayerInputData(String name, Color colour) {
		this.name = name;
		this.colour = colour;
	}
}
