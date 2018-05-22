package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Animal class for chickens.
 * 
 * @author Mikael Lindfors
 *
 */
public class Chicken extends Animal {
	private Image chickenLeft;
	private Image chickenRight;

	/**
	 * Constructor that loades animations for the animal and sends x,y coordinates
	 * to the superclass.
	 * 
	 * @param x x coordinate startlocation
	 * @param y y coordinate startlocation
	 */
	public Chicken(int x, int y) {
		super(x, y);
		loadImages();
	}

	/**
	 * Constructor that loades animations for the animal and let superclass decide
	 * start-coordinates.
	 */
	public Chicken() {
		super();
		loadImages();
	}

	/**
	 * Method that loads all images used for animations.
	 */
	public void loadImages() {
		chickenLeft = new ImageIcon("images/chickenleft.png").getImage();
		chickenRight = new ImageIcon("images/chickenright.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = chickenLeft;
			} else if (getAnimation() == 1) {
				animation = chickenLeft;
			} else if (getAnimation() == 2) {
				animation = chickenLeft;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = chickenRight;
			} else if (getAnimation() == 1) {
				animation = chickenRight;
			} else if (getAnimation() == 2) {
				animation = chickenRight;
			}
		}
		nextAnimation();
		return animation;
	}
}
