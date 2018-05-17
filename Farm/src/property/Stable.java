package property;

import java.awt.Image;

/**
 * Class that handles the Hen House Builing
 * 
 * @author Malin Zederfeldt
 */

import javax.swing.ImageIcon;
//

import commodity.Cow;
import commodity.Sheep;

public class Stable extends Building {

	private static int capacity = 8;
	private ImageIcon stable;

	/**
	 * Constructor that sends x, y coordinates to superclass and load images
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */

	public Stable(int x, int y) {
		super(x, y);
		loadImages();
	}

	/**
	 * Constructor only used when removing buildings, so coordinates needed
	 */

	public Stable() {
		super();
		loadImages();
	}

	/**
	 * method loads image for building
	 */

	public void loadImages() {
		stable = new ImageIcon("images/property/stable.png");

	}

	/**
	 * returns image of the building
	 * 
	 * @return image of building
	 */

	public ImageIcon getImage() {
		return stable;
	}

	/**
	 * Method that return the capacity of the building
	 * 
	 * @return
	 */
	public static int getCapacity() {
		return capacity;
	}

	/**
	 * Method that tells the animal-class if it has gained a new building to
	 * populate
	 */
	public static void gained() {
		Sheep.alterStorage(true);
	}

	/**
	 * Method that tells the animal-class if it has lost a building to populate.
	 */
	public static void lost() {
		Sheep.alterStorage(false);
	}
	public static String getOccupant() {
		return "Sheep";
	}

}
