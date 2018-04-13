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
	private Image sheepLeft1;
	private Image sheepLeft2;
	private Image sheepLeft3;
	private Image sheepRight1;
	private Image sheepRight2;
	private Image sheepRight3;

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
		sheepLeft1 = new ImageIcon("images/sheep1left.png").getImage();
		sheepLeft2 = new ImageIcon("images/sheep2left.png").getImage();
		sheepLeft3 = new ImageIcon("images/sheep1left.png").getImage();
		sheepRight1 = new ImageIcon("images/sheep1right.png").getImage();
		sheepRight2 = new ImageIcon("images/sheep2right.png").getImage();
		sheepRight3 = new ImageIcon("images/sheep1right.png").getImage();
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = sheepLeft1;
			} else if (getAnimation() == 1) {
				animation = sheepLeft2;
			} else if (getAnimation() == 2) {
				animation = sheepLeft3;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = sheepRight1;
			} else if (getAnimation() == 1) {
				animation = sheepRight2;
			} else if (getAnimation() == 2) {
				animation = sheepRight3;
			}
		}
		nextAnimation();
		return animation;
	}
}
