package commodity;

/**
 * author Elin Ol. 
 */
import java.awt.Image;

import javax.swing.ImageIcon;

public class Corn extends Crops {
	private ImageIcon corn;

	public Corn(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Corn() {
		super();
		loadImages();
	}

	public void loadImages() {
		corn = new ImageIcon("images/crop/corn.png");
	}

	public ImageIcon getImage() {
		return corn;
	}
}