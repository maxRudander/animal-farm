package farm;

import java.awt.Image;

/**
 * Class that handles the Hen House Builing
 * 
 * @author Malin Zederfeldt
 */

import javax.swing.ImageIcon;

public class HenHouse extends Building {
	
	private Image henHouse;
	
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
		henHouse = new ImageIcon("images/henhouse.png").getImage();
		
	}
	
	/**
	 * returns image of the building
	 * 
	 * @return image of building
	 */
	
	public Image getImage() {
		return henHouse;
	}

}
