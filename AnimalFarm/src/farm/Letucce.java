package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

	public class Letucce extends Crops {
		private Image Letucce;

		public Letucce(int x, int y) {
			super(x, y);
			loadImages();
		}

		public Letucce() {
			super();
			loadImages();
		}

		public void loadImages() {
			Letucce = new ImageIcon("images/Lettuce.png").getImage();
		}

		public Image getImage() {
			return Letucce;
		}
	}