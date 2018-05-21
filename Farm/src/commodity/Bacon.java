package commodity;
import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;
	/**
	 * Class for the product bacon
	 * author Elin O. 
	 */
		public class Bacon extends Goods {
			private ImageIcon Bacon;
			
			public Bacon() {
				loadImages();
			}
			/**
			 * sets the ImageIcon of the Lettuce
			 */
			public void loadImages() {
			Bacon = new ImageIcon("images/product/bacon.png");
			}
			/**
			 * returns the image of Lettuce
			 */
			public ImageIcon getImage() {
				return Bacon;
			}
		}
