package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * author Elin O
 */
public class Carrot extends Crops {
	private Image Carrot;

	public Carrot(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Carrot() {
		super();
		loadImages();
	}

	public void loadImages() {
		Carrot = new ImageIcon("images/carrot.png").getImage();
	}

	public Image getImage() {
		return Carrot;
	}
}