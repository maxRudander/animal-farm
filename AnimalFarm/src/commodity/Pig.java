package commodity;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Animal class for pigs
 * 
 * @author Mikael Lindfors
 *
 */
public class Pig extends Animal {
	private ImageIcon pigLeft1;
	private ImageIcon pigLeft2;
	private ImageIcon pigLeft3;
	private ImageIcon pigRight1;
	private ImageIcon pigRight2;
	private ImageIcon pigRight3;

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
		pigLeft1 = new ImageIcon("images/animal/pig1left.png");
		pigLeft2 = new ImageIcon("images/animal/pig2left.png");
		pigLeft3 = new ImageIcon("images/animal/pig1left.png");
		pigRight1 = new ImageIcon("images/animal/pig1right.png");
		pigRight2 = new ImageIcon("images/animal/pig2right.png");
		pigRight3 = new ImageIcon("images/animal/pig1right.png");
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public ImageIcon getNextAnimation() {
		ImageIcon animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = pigLeft1;
			} else if (getAnimation() == 1) {
				animation = pigLeft2;
			} else if (getAnimation() == 2) {
				animation = pigLeft3;
			}
		} else if (getX_direction() >= 0) {
			if (getAnimation() == 0) {
				animation = pigRight1;
			} else if (getAnimation() == 1) {
				animation = pigRight2;
			} else if (getAnimation() == 2) {
				animation = pigRight3;
			}
		}
		nextAnimation();
		return animation;
	}
}
