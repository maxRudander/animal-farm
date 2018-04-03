package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Sheep extends Animal{
	private Image sheepleft, sheepright;

	public Sheep(int x, int y) {
		super(x, y);
		loadImages();
	}

	public Sheep() {
		super();
		loadImages();
	}

	public void loadImages() {
		sheepleft = new ImageIcon("images/sheepleft.png").getImage();
		sheepright = new ImageIcon("images/sheepright.png").getImage();
	}

	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = sheepleft;
			} else if (getAnimation() == 1) {
				animation = sheepleft;
			} else if (getAnimation() == 2) {
				animation = sheepleft;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = sheepright;
			} else if (getAnimation() == 1) {
				animation = sheepright;
			} else if (getAnimation() == 2) {
				animation = sheepright;
			}
		}
		nextAnimation();
		return animation;
	}
}
