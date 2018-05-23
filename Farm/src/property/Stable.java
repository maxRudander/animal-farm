package property;

import javax.swing.ImageIcon;
import commodity.Sheep;
/**
 * Class that handles the Stable Building
 * 
 * @author Malin Zederfeldt, Max Rudander
 */
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
	 * @return the buildings capacity
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
	/**
	 * @return the name of the occupant
	 */
	public static String getOccupant() {
		return "Sheep";
	}

}
