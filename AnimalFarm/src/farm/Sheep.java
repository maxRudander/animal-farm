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
	private ImageIcon sheepLeft1;
	private ImageIcon sheepLeft2;
	private ImageIcon sheepLeft3;
	private ImageIcon sheepRight1;
	private ImageIcon sheepRight2;
	private ImageIcon sheepRight3;

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
		sheepLeft1 = new ImageIcon("images/sheep1left.png");
		sheepLeft2 = new ImageIcon("images/sheep2left.png");
		sheepLeft3 = new ImageIcon("images/sheep1left.png");
		sheepRight1 = new ImageIcon("images/sheep1right.png");
		sheepRight2 = new ImageIcon("images/sheep2right.png");
		sheepRight3 = new ImageIcon("images/sheep1right.png");
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public ImageIcon getNextAnimation() {
		ImageIcon animation = null;
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
