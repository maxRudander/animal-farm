package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pigsty extends Building {
	
	/**
	 * Class that handles the Hen House Builing.
	 * 
	 * @author Malin Zederfeldt
	 */
	
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
		pigsty = new ImageIcon("images/pigsty.png");
		
	}
	
	/**
	 * returns image of the building
	 * 
	 * @return image of building
	 */
	
	public ImageIcon getImage() {
		return pigsty;
	}

}
