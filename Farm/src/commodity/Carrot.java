package commodity;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class for the crop carrot
 * author Elin Ol.
 */
public class Carrot extends Crops {
	private ImageIcon carrot;
	/**
	 * sets the location of the carrot and loads the image
	 * @param x the x location
	 * @param y the y location
	 */
	public Carrot(int x, int y) {
		super(x, y);
		loadImages();
	}
	/**
	 * sets the location of the carrot to 0,0 and loads the image
	 */
	public Carrot() {
		super();
		loadImages();
	}
	/**
	 * sets the ImageIcon of the carrot
	 */
	public void loadImages() {
		carrot = new ImageIcon("images/crop/carrot.png");
	}
	/**
	 * returns the image of carrot
	 */
	public ImageIcon getImage() {
		return carrot;
	}
}