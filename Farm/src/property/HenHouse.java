package property;

import javax.swing.ImageIcon;
import commodity.Chicken;
/**
 * Class that handles the Hen House Builing
 * 
 * @author Malin Zederfeldt, Max Rudander
 */
public class HenHouse extends Building {
	private ImageIcon henHouse;
	private static final int capacity = 8;
	
	/**
	 * Constructor that sends x, y coordinates to superclass and load images
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public HenHouse(int x, int y) {
		super(x,y);
		loadImages();
	}
	
	/**
	 * Constructor only used when removing buildings, so coordinates needed
	 */
	public HenHouse() {
		super();
		loadImages();
	}
	
	/**
	 * method loads image for building
	 */
	public void loadImages() {
		henHouse = new ImageIcon("images/property/henhouse.png");
	}
	
	/**
	 * returns image of the building
	 * 
	 * @return image of building
	 */
	public ImageIcon getImage() {
		return henHouse;
	}
	/**
	 * @return the capacity of the building
	 */
	public static int getCapacity() {
		return capacity;
	}
	/**
	 * Informs the animal that it's capacity has increased
	 */
	public static void gained() {
		Chicken.alterStorage(true);
	}
	/**
	 * Informs the animal that it's capacity has decreased
	 */
	public static void lost() {
		Chicken.alterStorage(false);
	}
	/**
	 * @return the name of the occupant
	 */
	public static String getOccupant() {
		return "Chicken";
	}

}
