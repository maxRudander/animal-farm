package farm;

/**
 * author Elin O 
 */
import java.awt.Image;

import javax.swing.ImageIcon;

public class Corn extends Crops {
	private Image Corn;

	public Corn(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Corn() {
		super();
		loadImages();
	}

	public void loadImages() {
		Corn = new ImageIcon("images/Corn.png").getImage();
	}

	public Image getImage() {
		return Corn;
	}
}