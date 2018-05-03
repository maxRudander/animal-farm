package commodity;

import java.awt.Image;

import javax.swing.ImageIcon;

import property.Barn;
import property.HenHouse;

/**
 * Animal class for chickens.
 * 
 * @author Mikael Lindfors.
 *
 */
public class Chicken extends Animal {
	private static int nbrOfHouses = 0;
	private static int capacity = HenHouse.getCapacity()*nbrOfHouses;
	private ImageIcon chickenLeft1;
	private ImageIcon chickenLeft2;
	private ImageIcon chickenLeft3;
	private ImageIcon chickenRight1;
	private ImageIcon chickenRight2;
	private ImageIcon chickenRight3;

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
		chickenLeft1 = new ImageIcon("images/animal/chicken1left.png");
		chickenLeft2 = new ImageIcon("images/animal/chicken2left.png");
		chickenLeft3 = new ImageIcon("images/animal/chicken1left.png");
		chickenRight1 = new ImageIcon("images/animal/chicken1right.png");
		chickenRight2 = new ImageIcon("images/animal/chicken2right.png");
		chickenRight3 = new ImageIcon("images/animal/chicken1right.png");
	}

	/**
	 * Method that fetch the next Image in line for stopmotion animation.
	 */
	public ImageIcon getNextAnimation() {
		ImageIcon animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = chickenLeft1;
			} else if (getAnimation() == 1) {
				animation = chickenLeft2;
			} else if (getAnimation() == 2) {
				animation = chickenLeft3;
			}
		} else if (getX_direction() >= 0) {
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
	/**
	 * returns the capacity of chickens that the henhouse can hold
	 * @return capacity of the henhouses
	 */
	public static int getCapacity () {
		capacity = nbrOfHouses*HenHouse.getCapacity();
		return capacity;
	}
	/**
	 * returns the type of building
	 */
	public String getHouseType () {
		return "HenHouse";
	}
	/**
	 * tells the animal if it has gained or lost housing
	 * @param b if true it has gained, if false it has lost housing
	 */
	public static void alterStorage (boolean b) {
		if (b) {
			nbrOfHouses+=1;
		}
		else {
			nbrOfHouses-=1;
		}
	}
}
