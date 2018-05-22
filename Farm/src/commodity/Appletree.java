package commodity;

import javax.swing.ImageIcon;

public class Appletree extends Crops{
	private ImageIcon appletree;
	/**
	 * sets the location of the carrot and loads the image
	 * @param x the x location
	 * @param y the y location
	 */
	public Appletree(int x, int y) {
		super(x, y);
		loadImages();
	}
	/**
	 * sets the location of the carrot to 0,0 and loads the image
	 */
	public Appletree() {
		super();
		loadImages();
	}
	/**
	 * sets the ImageIcon of the carrot
	 */
	public void loadImages() {
		appletree = new ImageIcon("images/property/tree.png");
	}
	/**
	 * returns the image of carrot
	 */
	public ImageIcon getImage() {
		return appletree;
	}
}
