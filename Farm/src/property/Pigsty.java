package property;

import java.awt.Image;

import javax.swing.ImageIcon;

import commodity.Cow;
import commodity.Pig;
	/**
	 * Class that handles the Hen House Builing.
	 * 
	 * @author Malin Zederfeldt
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
	 * @return
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

}
