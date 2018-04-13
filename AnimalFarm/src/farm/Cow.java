package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Animal class for cowss.
 * 
 * @author Mikael Lindfors
 *
 */
public class Cow extends Animal {
	Image cowLeft1;
	Image cowLeft2;
	Image cowLeft3;
	Image cowRight1;
	Image cowRight2;
	Image cowRight3;

	/**
	 * Constructor that loades animations for the animal and sends x,y coordinates
	 * to the superclass.
	 * 
	 * @param x x coordinate startlocation
	 * @param y y coordinate startlocation
	 */
	public Cow(int x, int y) {
		super(x, y);
		loadImages();
	}

	/**
	 * Constructor that loades animations for the animal and let superclass decide
	 * start-coordinates.
	 */
	public Cow() {
		super();
		loadImages();
	}

	/**
	 * Method that loads all images used for animations.
	 */
	public void loadImages() {
		cowLeft1 = new ImageIcon("images/cow1left.png").getImage();
		cowLeft2 = new ImageIcon("images/cow2left.png").getImage();
		cowLeft3 = new ImageIcon("images/cow1left.png").getImage();
		cowRight1 = new ImageIcon("images/cow1right.png").getImage();
		cowRight2 = new ImageIcon("images/cow2right.png").getImage();
		cowRight3 = new ImageIcon("images/cow1right.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = cowLeft1;
			} else if (getAnimation() == 1) {
				animation = cowLeft2;
			} else if (getAnimation() == 2) {
				animation = cowLeft3;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = cowRight1;
			} else if (getAnimation() == 1) {
				animation = cowRight2;
			} else if (getAnimation() == 2) {
				animation = cowRight3;
			}
		}
		nextAnimation();
		return animation;
	}
}
