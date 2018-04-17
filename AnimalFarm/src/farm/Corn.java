package farm;

/**
 * author Elin O 
 */
import java.awt.Image;

import javax.swing.ImageIcon;

public class Corn extends Crops {
	private Image corn;

	public Corn(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Corn() {
		super();
		loadImages();
	}

	public void loadImages() {
		corn = new ImageIcon("images/corn.png").getImage();
	}

	public Image getImage() {
		return corn;
	}
}