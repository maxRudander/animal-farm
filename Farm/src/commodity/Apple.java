package commodity;

import javax.swing.ImageIcon;
/**
 * Class for the product apple
 * @author Matthias Falk
 *
 */

public class Apple extends Goods {
	private ImageIcon apple;
	
	public Apple() {
		loadImages();
	}
	/**
	 * sets the ImageIcon of the Apple
	 */
	public void loadImages() {
	apple = new ImageIcon("images/product/apple.png");
	}
	/**
	 * returns the image of Apple
	 */
	public ImageIcon getImage() {
		return apple;
	}

}
