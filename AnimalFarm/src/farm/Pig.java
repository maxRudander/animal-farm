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
	private Image pigLeft1;
	private Image pigLeft2;
	private Image pigLeft3;
	private Image pigRight1;
	private Image pigRight2;
	private Image pigRight3;

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
		pigLeft1 = new ImageIcon("images/pig1left.png").getImage();
		pigLeft2 = new ImageIcon("images/pig2left.png").getImage();
		pigLeft3 = new ImageIcon("images/pig1left.png").getImage();
		pigRight1 = new ImageIcon("images/pig1right.png").getImage();
		pigRight2 = new ImageIcon("images/pig2right.png").getImage();
		pigRight3 = new ImageIcon("images/pig1right.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = pigLeft1;
			} else if (getAnimation() == 1) {
				animation = pigLeft2;
			} else if (getAnimation() == 2) {
				animation = pigLeft3;
			}
		} else if (getX_direction() > 0) {
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
