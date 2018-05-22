package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product meat
	 * author Elin O. 
	 */
		public class Meat extends Goods {
			private ImageIcon Meat;
			
			public Meat() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the Meat
			 */
			public void loadImages() {
			Meat = new ImageIcon("images/product/meat.png");
			}
			/**
			 * returns the image of Meat
			 */
			public ImageIcon getImage() {
				return Meat;
			}
		}
