package farm;

import java.awt.Image;

import javax.swing.ImageIcon;
/** 
 * 
 * @author elinolsson
 *
 */

public class Oat extends Crops {

	private Image Oat;

	public Oat(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Oat() {
		super();
		loadImages();
	}

	public void loadImages() {
		Oat = new ImageIcon("images/oat.png").getImage();
	}

	public Image getImage() {
		return Oat;
	}
}