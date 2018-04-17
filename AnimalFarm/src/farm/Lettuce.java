package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

	public class Lettuce extends Crops {
		private Image lettuce;

		public Lettuce(int x, int y) {
			super(x, y);
			loadImages();
		}

		public Lettuce() {
			super();
			loadImages();
		}

		public void loadImages() {
			lettuce = new ImageIcon("images/lettuce.png").getImage();
		}

		public Image getImage() {
			return lettuce;
		}
	}