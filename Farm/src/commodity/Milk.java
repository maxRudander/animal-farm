package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product milk
	 * author Elin O. 
	 */
		public class Milk extends Goods {
			private ImageIcon Milk;
			
			public Milk() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the milk
			 */
			public void loadImages() {
			Milk = new ImageIcon("images/product/milk.png");
			}
			/**
			 * returns the image of milk
			 */
			public ImageIcon getImage() {
				return Milk;
			}
		}
