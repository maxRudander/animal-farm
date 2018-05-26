package commodity;

import javax.swing.ImageIcon;
import property.Stable;

/**
 * Animal class for sheep.
 * 
 * @author Mikael Lindfors.
 *
 */
public class Sheep extends Animal {
	private static int nbrOfHouses = 0;
	private static int capacity = Stable.getCapacity() * nbrOfHouses;
	private ImageIcon sheepLeft1;
	private ImageIcon sheepLeft2;
	private ImageIcon sheepLeft3;
	private ImageIcon sheepRight1;
	private ImageIcon sheepRight2;
	private ImageIcon sheepRight3;

	/**
	 * Constructor that loads animations for the animal and sends x,y coordinates
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
	 * Constructor that loads animations for the animal and let superclass decide
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
		sheepLeft1 = new ImageIcon("images/animal/sheep1left.png");
		sheepLeft2 = new ImageIcon("images/animal/sheep2left.png");
		sheepLeft3 = new ImageIcon("images/animal/sheep1left.png");
		sheepRight1 = new ImageIcon("images/animal/sheep1right.png");
		sheepRight2 = new ImageIcon("images/animal/sheep2right.png");
		sheepRight3 = new ImageIcon("images/animal/sheep1right.png");
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
		} else if (getX_direction() >= 0) {
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

	/**
	 * Returns of the capacity.
	 * 
	 * @return Return capacity of the stables
	 */
	public static int getCapacity() {
		capacity = nbrOfHouses * Stable.getCapacity();
		return capacity;
	}

	/**
	 * Returns the type of the building.
	 * @return The name of the sheeps house
	 */
	public String getHouseType() {
		return "Stable";
	}
/**
 * Tells the animal if it gained or lost housing.
 * @param status If true it has gained housing, otherwise lost
 */
	public static void alterStorage(boolean status) {
		if (status) {
			nbrOfHouses += 1;
		} else {
			nbrOfHouses -= 1;
		}
	}
	public static void resetStorage () {
		nbrOfHouses = 0;
	}
}
