package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Animal class for sheep.
 * 
 * @author Mikael Lindfors
 *
 */
public class Sheep extends Animal {
	private Image sheepLeft;
	private Image sheepRight;

	/**
	 * Constructor that loades animations for the animal and sends x,y coordinates
	 * to the superclass.
	 * 
	 * @param x x coordinate startlocation
	 * @param y y coordinate startlocation
	 */
	public Sheep(int x, int y) {
		super(x, y);
		loadImages();
	}

	/**
	 * Constructor that loades animations for the animal and let superclass decide
	 * start-coordinates.
	 */
	public Sheep() {
		super();
		loadImages();
	}

	/**
	 * Method that loads all images used for animations.
	 */
	public void loadImages() {
		sheepLeft = new ImageIcon("images/sheepleft.png").getImage();
		sheepRight = new ImageIcon("images/sheepright.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = sheepLeft;
			} else if (getAnimation() == 1) {
				animation = sheepLeft;
			} else if (getAnimation() == 2) {
				animation = sheepLeft;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = sheepRight;
			} else if (getAnimation() == 1) {
				animation = sheepRight;
			} else if (getAnimation() == 2) {
				animation = sheepRight;
			}
		}
		nextAnimation();
		return animation;
	}
}
