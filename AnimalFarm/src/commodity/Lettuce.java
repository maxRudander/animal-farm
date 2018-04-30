package commodity;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * 
 * @author Elin
 *
 */
	public class Lettuce extends Crops {
		private ImageIcon lettuce;

		public Lettuce(int x, int y) {
			super(x, y);
			loadImages();
		}

		public Lettuce() {
			super();
			loadImages();
		}

		public void loadImages() {
			lettuce = new ImageIcon("images/crop/lettuce.png");
		}

		public ImageIcon getImage() {
			return lettuce;
		}
	}