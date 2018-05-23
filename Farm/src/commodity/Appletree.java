package commodity;

import javax.swing.ImageIcon;
/**
 * Class for the crop appletree
 * @author Matthias Falk
 *
 */
public class Appletree extends Crops{
	private ImageIcon appletree;
	/**
	 * sets the location of the appletree and loads the image
	 * @param x the x location
	 * @param y the y location
	 */
	public Appletree(int x, int y) {
		super(x, y);
		loadImages();
	}
	/**
	 * sets the location of the appletree to 0,0 and loads the image
	 */
	public Appletree() {
		super();
		loadImages();
	}
	/**
	 * sets the ImageIcon of the appletree
	 */
	public void loadImages() {
		appletree = new ImageIcon("images/property/tree.png");
	}
	/**
	 * returns the image of appletree
	 * @return appletree image the appletree
	 */
	public ImageIcon getImage() {
		return appletree;
	}
}
