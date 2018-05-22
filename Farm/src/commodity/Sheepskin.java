package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product Sheepskin
	 * author Elin O. 
	 */
		public class Sheepskin extends Goods {
			private ImageIcon Sheepskin;
			
			public Sheepskin() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the sheepskin
			 */
			public void loadImages() {
			Sheepskin = new ImageIcon("images/product/skeepskin.png");
			}
			/**
			 * returns the image of sheepskin
			 */
			public ImageIcon getImage() {
				return Sheepskin;
			}
		}
