package farm;

import java.awt.Image;

import javax.swing.ImageIcon;
/** 
 * 
 * @author elin olsson
 *
 */

public class Oat extends Crops {

	private ImageIcon oat;

	public Oat(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Oat() {
		super();
		loadImages();
	}

	public void loadImages() {
		oat = new ImageIcon("images/oat.png");
	}

	public ImageIcon getImage() {
		return oat;
	}
}