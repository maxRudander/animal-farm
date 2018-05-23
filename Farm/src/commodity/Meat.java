package commodity;

import javax.swing.ImageIcon;

/**
 * Class for the product meat author Elin Olsson.
 */
public class Meat extends Goods {
	private ImageIcon meat;
	
	/**
	 * Constructor for the class Meat
	 */
	public Meat() {
		loadImages();
	}

	/**
	 * sets the ImageIcon of the Meat
	 */
	public void loadImages() {
		meat = new ImageIcon("images/product/meat.png");
	}

	/**
	 * returns the image of Meat
	 * @return meat the image for meat
	 */
	public ImageIcon getImage() {
		return meat;
	}
}
