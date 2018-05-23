package property;

import javax.swing.ImageIcon;
import commodity.Pig;
	/**
	 * Class that handles the Pigsty Building.
	 * 
	 * @author Malin Zederfeldt, Max Rudander
	 */
public class Pigsty extends Building {
	private static int capacity = 8;
	private ImageIcon pigsty;
	
	/**
	 * Constructor that sends x, y coordinates to superclass and load images
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Pigsty(int x, int y) {
		super(x,y);
		loadImages();
	}
	
	/**
	 * Constructor only used when removing buildings, so coordinates needed
	 */
	public Pigsty() {
		super();
		loadImages();
	}
	
	/**
	 * method loads image for building
	 */
	public void loadImages() {
		pigsty = new ImageIcon("images/property/pigsty.png");
	}
	
	/**
	 * returns image of the building
	 * 
	 * @return image of building
	 */
	public ImageIcon getImage() {
		return pigsty;
	}
	/**
	 * Method that return the capacity of the building
	 * @return the capacity of the building
	 */
	public static int getCapacity() {
		return capacity ;
	}
	/**
	 * Method that tells the animal-class if it has gained a new building to populate
	 */
	public static void gained() {
		Pig.alterStorage(true);
	}
	/**
	 * Method that tells the animal-class if it has lost a building to populate.
	 */
	public static void lost() {
		Pig.alterStorage(false);
	}
	/**
	 * @return the name of the occupant
	 */
	public static String getOccupant() {
		return "Pig";
	}

}
