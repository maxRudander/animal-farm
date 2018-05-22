package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Animal class for pigs.
 * 
 * @author Mikael Lindfors
 *
 */
public class Pig extends Animal {
	private Image pigLeft;
	private Image pigRight;

	/**
	 * Constructor that loades animations for the animal and sends x,y coordinates
	 * to the superclass.
	 * 
	 * @param x x coordinate startlocation
	 * @param y y coordinate startlocation
	 */
	public Pig(int x, int y) {
		super(x, y);
		loadImages();
	}

	/**
	 * Constructor that loades animations for the animal and let superclass decide
	 * start-coordinates.
	 */
	public Pig() {
		super();
		loadImages();
	}

	/**
	 * Method that loads all images used for animations.
	 */
	public void loadImages() {
		pigLeft = new ImageIcon("images/pigleft.png").getImage();
		pigRight = new ImageIcon("images/pigright.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = pigLeft;
			} else if (getAnimation() == 1) {
				animation = pigLeft;
			} else if (getAnimation() == 2) {
				animation = pigLeft;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = pigRight;
			} else if (getAnimation() == 1) {
				animation = pigRight;
			} else if (getAnimation() == 2) {
				animation = pigRight;
			}
		}
		nextAnimation();
		return animation;
	}
}
