package property;

import java.awt.Image;

/**
 * Class that handles the Hen House Builing
 * 
 * @author Malin Zederfeldt
 */


import javax.swing.ImageIcon;
//

public class Stable extends Building {
	
	private ImageIcon stable;
	
	/**
	 * Constructor that sends x, y coordinates to superclass and load images
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	
	public Stable(int x, int y) {
		super(x,y);
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

}
