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
	ImageIcon cowLeft1;
	ImageIcon cowLeft2;
	ImageIcon cowLeft3;
	ImageIcon cowRight1;
	ImageIcon cowRight2;
	ImageIcon cowRight3;

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
		cowLeft1 = new ImageIcon("images/cow1left.png");
		cowLeft2 = new ImageIcon("images/cow2left.png");
		cowLeft3 = new ImageIcon("images/cow1left.png");
		cowRight1 = new ImageIcon("images/cow1right.png");
		cowRight2 = new ImageIcon("images/cow2right.png");
		cowRight3 = new ImageIcon("images/cow1right.png");
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public ImageIcon getNextAnimation() {
		ImageIcon animation = null;
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
