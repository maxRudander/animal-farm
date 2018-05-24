package commodity;

import javax.swing.ImageIcon;

/**
 * Class for the product OatMeal
 * 
 * @author Elin Olsson.
 */
public class OatMeal extends Goods {
	private ImageIcon oatMeal;

	/**
	 * Constructor for the class OatMeal
	 */
	public OatMeal() {
		loadImages();
	}

	/**
	 * sets the ImageIcon of the OatMeal
	 */
	public void loadImages() {
		oatMeal = new ImageIcon("images/product/oats.png");
	}

	/**
	 * returns the image of OatMeal
	 * 
	 * @return oatmeal Image for oatmeal
	 */
	public ImageIcon getImage() {
		return oatMeal;
	}
}