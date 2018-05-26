package commodity;

import javax.swing.ImageIcon;

/**
 * Class for the product Sheepskin 
 * @author Elin Olsson.
 */
public class Sheepskin extends Goods {
	private ImageIcon sheepskin;
	
	/**
	 * Constructor for the class Sheepskin
	 */
	public Sheepskin() {
		loadImages();
	}

	/**
	 * sets the ImageIcon of the sheepskin
	 */
	public void loadImages() {
		sheepskin = new ImageIcon("images/product/sheepskin.png");
	}

	/**
	 * returns the image of sheepskin
	 * @return sheepskin The image of sheepskin
	 */
	public ImageIcon getImage() {
		return sheepskin;
	}
}
