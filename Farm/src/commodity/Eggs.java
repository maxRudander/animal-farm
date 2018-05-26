package commodity;

import javax.swing.ImageIcon;

/**
 * Class for the product Eggs author Elin Olsson.
 */
public class Eggs extends Goods {
	private ImageIcon eggs;

	/**
	 * Constructor for the class Eggs
	 */
	public Eggs() {
		loadImages();
	}

	/**
	 * sets the ImageIcon of the Egg
	 */
	public void loadImages() {
		eggs = new ImageIcon("images/product/egg.png");
	}

	/**
	 * returns the image of Egg
	 * 
	 * @return eggs the image of the eggs
	 */
	public ImageIcon getImage() {
		return eggs;
	}
}
