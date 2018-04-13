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
	private Image chickenLeft1;
	private Image chickenLeft2;
	private Image chickenLeft3;
	private Image chickenRight1;
	private Image chickenRight2;
	private Image chickenRight3;

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
		chickenLeft1 = new ImageIcon("images/chicken1left.png").getImage();
		chickenLeft2 = new ImageIcon("images/chicken2left.png").getImage();
		chickenLeft3 = new ImageIcon("images/chicken1left.png").getImage();
		chickenRight1 = new ImageIcon("images/chicken1right.png").getImage();
		chickenRight2 = new ImageIcon("images/chicken2right.png").getImage();
		chickenRight3 = new ImageIcon("images/chicken1right.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = chickenLeft1;
			} else if (getAnimation() == 1) {
				animation = chickenLeft2;
			} else if (getAnimation() == 2) {
				animation = chickenLeft3;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = chickenRight1;
			} else if (getAnimation() == 1) {
				animation = chickenRight2;
			} else if (getAnimation() == 2) {
				animation = chickenRight3;
			}
		}
		nextAnimation();
		return animation;
	}
}
