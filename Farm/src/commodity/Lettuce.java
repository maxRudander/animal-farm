package commodity;

import java.awt.Image;


import javax.swing.ImageIcon;
/**
 * Class for the crop Lettuce
 * author Elin Ol. 
 */
	public class Lettuce extends Crops {
		private ImageIcon lettuce;
		/**
		 * sets the location of the Lettuce and loads the image
		 * @param x the x location
		 * @param y the y location
		 */
		public Lettuce(int x, int y) {
			super(x, y);
			loadImages();
		}
		/**
		 * sets the location of the Lettuce to 0,0 and loads the image
		 */
		public Lettuce() {
			super();
			loadImages();
		}
		/**
		 * sets the ImageIcon of the Lettuce
		 */
		public void loadImages() {
			lettuce = new ImageIcon("images/crop/lettuce.png");
		}
		/**
		 * returns the image of Lettuce
		 */
		public ImageIcon getImage() {
			return lettuce;
		}
	}