package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product milk
	 * author Elin O. 
	 */
		public class Meat extends Goods {
			private ImageIcon Meat;
			
			public Meat() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the Lettuce
			 */
			public void loadImages() {
			Meat = new ImageIcon("images/product/meat.png");
			}
			/**
			 * returns the image of Lettuce
			 */
			public ImageIcon getImage() {
				return Meat;
			}
		}
