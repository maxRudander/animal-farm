package commodity;

import javax.swing.ImageIcon;

/**
 * Class for the product bacon author Elin Olsson
 */
public class Bacon extends Goods {
	private ImageIcon bacon;

	/**
	 * Constructor for the class Bacon
	 */
	public Bacon() {
		loadImages();
	}

	/**
	 * sets the ImageIcon of the Bacon
	 */
	public void loadImages() {
		bacon = new ImageIcon("images/product/bacon.png");
	}

	/**
	 * returns the image of Bacon
	 * 
	 * @return bacon image for bacon
	 */
	public ImageIcon getImage() {
		return bacon;
	}
}
