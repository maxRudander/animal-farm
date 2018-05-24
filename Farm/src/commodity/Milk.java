package commodity;

import javax.swing.ImageIcon;

/**
 * Class for the product milk author Elin Olsson.
 */
public class Milk extends Goods {
	private ImageIcon milk;
	
	/**
	 * Constructor for the class Milk
	 */
	public Milk() {
		loadImages();
	}

	/**
	 * sets the ImageIcon of the milk
	 */
	public void loadImages() {
		milk = new ImageIcon("images/product/milk.png");
	}

	/**
	 * returns the image of milk
	 * @return Milk the image for the milk
	 */
	public ImageIcon getImage() {
		return milk;
	}
}
