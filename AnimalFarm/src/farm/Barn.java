package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class that handles the Barn building.
 * 
 * @author Mikael Lindfors
 *
 */
public class Barn extends Building {
	private ImageIcon barn;

	/**
	 * Constructor that sends x,y coordinates to superclass and load images.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Barn(int x, int y) {
		super(x, y);
		loadImages();
	}

	/**
	 * Constructor only used when removing buildings, no x,y coordinates for
	 * location.
	 */
	public Barn() {
		super();
		loadImages();
	}

	/**
	 * Method that loads Image for the building
	 */
	public void loadImages() {
		barn = new ImageIcon("images/Barn.png");
	}

	/**
	 * Method that returns the Image of the building.
	 * 
	 * @return Image of the building.
	 */
	public ImageIcon getImage() {
		return barn;
	}
}
