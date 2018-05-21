package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product milk
	 * author Elin O. 
	 */
		public class OatMeal extends Goods {
			private ImageIcon OatMeal;
			
			public OatMeal() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the Lettuce
			 */
			public void loadImages() {
			OatMeal = new ImageIcon("images/product/oats.png");
			}
			/**
			 * returns the image of Lettuce
			 */
			public ImageIcon getImage() {
				return OatMeal;
			}
		}