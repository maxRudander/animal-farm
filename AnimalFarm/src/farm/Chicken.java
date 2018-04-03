package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Chicken extends Animal{
	private Image chickenleft, chickenright;

	public Chicken(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Chicken() {
		super();
		loadImages();
	}

	public void loadImages() {
		chickenleft = new ImageIcon("images/chickenleft.png").getImage();
		chickenright = new ImageIcon("images/chickenright.png").getImage();
	}

	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = chickenleft;
			} else if (getAnimation() == 1) {
				animation = chickenleft;
			} else if (getAnimation() == 2) {
				animation = chickenleft;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = chickenright;
			} else if (getAnimation() == 1) {
				animation = chickenright;
			} else if (getAnimation() == 2) {
				animation = chickenright;
			}
		}
		nextAnimation();
		return animation;
	}
}
