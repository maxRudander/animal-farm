package property;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Superclass for all buildings. Keep track of x,y coordinates and restricted
 * areas.
 * 
 * @author Mikael Lindfors, Max Rudander
 *
 */
public class Building implements Serializable {
	private int x;
	private int y;
	private int nbrOfAnimals = 0;
	private int maxCapacity = 10;
	private boolean[][] node;

	/**
	 * Constructor that sets location for the building and makes a new
	 * RestrictedArea. ( for animals to avoid )
	 * 
	 * @param x x coordinate location
	 * @param y y coordinate location
	 */
	public Building(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Only used for removing buildings at the moment.
	 */
	public Building() {
		x = 0;
		y = 0;
	}
	/**
	 * sets a copy of the boards pixel-node
	 * @param node boards pixel node.
	 */
	public void setNode(boolean[][] node) {
		this.node = node;
	}

	/**
	 * Method that returns the x coordinate of the building.
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Method that sets the x coordinate of the building
	 * 
	 * @param x x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Method that return the y coordinate of the building.
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Method that sets the y coordinate of the building
	 * 
	 * @param y y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Method that returns the Image of the building.
	 * 
	 * @return Image of the building.
	 */
	public ImageIcon getImage() {
		return null;
	}

	/**
	 * Method used for setting restrictions around buildings and crops
	 * @param x1 x1 coordinate
	 * @param y1 y1 coordinate
	 * @param x2 x2 coordinate
	 * @param y2 y2 coordinate
	 * @param walkable pixel node with restrictions
	 */
	public void setWalkableArea(int x1, int y1, int x2, int y2, boolean walkable) {
		for (int x = x1; x <= x2; x++) {
			setWalkable(x, y1, walkable);
			setWalkable(x, y2, walkable);
		}
		for (int y = y1; y <= y2; y++) {
			setWalkable(x1, y, walkable);
			setWalkable(x2, y, walkable);
		}
	}

	/**
	 * Method that sets an pixel (x,y) walkable true or false
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param walkable boolean true or false
	 */
	public void setWalkable(int x, int y, boolean walkable) {
		node[x][y] = walkable;
	}

	/**
	 * Method that checks an pixel if its walkable or not.
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return boolean true or false
	 */
	public boolean checkWalkable(int x, int y) {
		return node[x][y]; 
	}

	/**
	 * Method that returns number of animals in the building.
	 * 
	 * @return int number of animals
	 */
	public int getNumberOfAnimals() {
		return nbrOfAnimals;
	}

	/**
	 * Method that increase number of animals in the building.
	 */
	public void addAnimal() {
		nbrOfAnimals++;
	}

	/**
	 * Method that reduce number of animals in the building.
	 */
	public void removeAnimal() {
		nbrOfAnimals--;
	}

	/**
	 * Method that checks if building is full or not.
	 * 
	 * @return Return boolean true or false.
	 */
	public boolean isFull() {
		if (nbrOfAnimals >= maxCapacity) {
			return true;
		}
		return false;
	}

	/**
	 * Used in children
	 */
	public static int getCapacity() {
		return 0;
	}

	/**
	 * Used in children
	 */
	public static String getOccupant() {
		return null;
	}

}
