package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pig extends Animal {

	private Image pigleft, pigright;

	public Pig(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Pig() {
		super();
		loadImages();
	}

	public void loadImages() {
		pigleft = new ImageIcon("images/pigleft.png").getImage();
		pigright = new ImageIcon("images/pigright.png").getImage(); 
	}

	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = pigleft;
			} else if (getAnimation() == 1) {
				animation = pigleft;
			} else if (getAnimation() == 2) {
				animation = pigleft;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = pigright;
			} else if (getAnimation() == 1) {
				animation = pigright;
			} else if (getAnimation() == 2) {
				animation = pigright;
			}
		}
		nextAnimation();
		return animation;
	}
}
