package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product Eggs
	 * author Elin O. 
	 */
		public class Eggs extends Goods {
			private ImageIcon Eggs;
			
			public Eggs() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the Egg
			 */
			public void loadImages() {
			Eggs = new ImageIcon("images/product/eggs.png");
			}
			/**
			 * returns the image of Egg
			 */
			public ImageIcon getImage() {
				return Eggs;
			}
		}

