package commodity;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * Class for the crop Oat
 * author Elin Ol. 
 */

public class Oat extends Crops {

	private ImageIcon oat;
	/**
	 * sets the location of the Oat and loads the image
	 * @param x the x location
	 * @param y the y location
	 */
	public Oat(int x, int y) {
		super(x, y);
		loadImages();
	}
	/**
	 * sets the location of the Oat to 0,0 and loads the image
	 */
	public Oat() {
		super();
		loadImages();
	}
	/**
	 * sets the ImageIcon of the Oat
	 */
	public void loadImages() {
		oat = new ImageIcon("images/crop/oat.png");
	}
	/**
	 * returns the image of Oat
	 */
	public ImageIcon getImage() {
		return oat;
	}
}