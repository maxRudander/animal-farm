package farm;

import java.awt.Image;

/**
 * Superclass for all buildings. Keep track of x,y coordinates and restricted
 * areas
 * 
 * @author mlind
 *
 */
public class Building {
	private int x;
	private int y;
	private RestrictedArea restrictedArea;

	/**
	 * Constructor that sets location for the building and makes a new
	 * RestrictedArea. ( for animals to avoid )
	 * 
	 * @param x x coordinate location
	 * @param y y coordinate location
	 */
	public Building(int x, int y) {
		restrictedArea = new RestrictedArea();
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
	 * Method that sets an restricted area (where an building is located). Used for
	 * keeping animals out of buildings and to stop building of buildings on top of
	 * each other.
	 * 
	 * @param x1 - x coord upper left corner.
	 * @param y1 - y coord upper left corner
	 * @param x2 - x coord lower right corner
	 * @param y2 - y coord lower right corner
	 */
	public void setRestrictedArea(int x1, int y1, int x2, int y2) {
		restrictedArea.addOutBoundBorder(x1, y1, x2, y2);
	}

	/**
	 * Method that returns the restricted areas the building is bound to.
	 * 
	 * @return - restrictedArea with information where its allowed/not allowed to
	 *         go.
	 */
	public RestrictedArea getRestrictedArea() {
		return restrictedArea;
	}

	/**
	 * Method that returns the Image of the building.
	 * 
	 * @return Image of the building.
	 */
	public Image getImage() {
		return null;
	}
}
