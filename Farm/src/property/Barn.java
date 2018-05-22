package property;

import java.awt.Image;

import javax.swing.ImageIcon;

import commodity.Cow;

/**
 * Class that handles the Barn building.
 * 
 * @author Mikael Lindfors.
 *
 */
public class Barn extends Building {
	private ImageIcon barn;
	private static final int capacity = 8;

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
		barn = new ImageIcon("images/property/barn.png");
	}

	/**
	 * Method that returns the Image of the building.
	 * 
	 * @return Image of the building.
	 */
	public ImageIcon getImage() {
		return barn;
	}
	/**
	 * Method that return the capacity of the building
	 * @return
	 */
	public static int getCapacity() {
		return capacity;
	}
	/**
	 * Method that tells the animal-class if it has gained a new building to populate
	 */
	public static void gained() {
		Cow.alterStorage(true);
	}
	/**
	 * Method that tells the animal-class if it has lost a building to populate.
	 */
	public static void lost() {
		Cow.alterStorage(false);
	}
}