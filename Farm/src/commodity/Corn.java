package commodity;

/**
 * Class for the crop corn
 * author Elin Ol. 
 */
import java.awt.Image;

import javax.swing.ImageIcon;

public class Corn extends Crops {
	private ImageIcon corn;
	/**
	 * sets the location of the corn and loads the image
	 * @param x the x location
	 * @param y the y location
	 */
	public Corn(int x, int y) {
		super(x, y);
		loadImages();
	}
	/**
	 * sets the location of the corn to 0,0 and loads the image
	 */
	public Corn() {
		super();
		loadImages();
	}
	/**
	 * sets the ImageIcon of the corn
	 */
	public void loadImages() {
		corn = new ImageIcon("images/crop/corn.png");
	}
	/**
	 * returns the image of corn
	 */
	public ImageIcon getImage() {
		return corn;
	}
}