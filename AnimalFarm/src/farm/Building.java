package farm;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Superclass for all buildings. Keep track of x,y coordinates and restricted
 * areas.
 * 
 * @author mlind
 *
 */
public class Building implements Serializable{
	private int x;
	private int y;

	private Node[][] node;
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
	public void setNode(Node[][] node) {
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
	public void setWalkableArea(int x1,int y1, int x2,int y2, boolean walkable) {
		for (int x=x1;x<=x2;x++) {
			setWalkable(x,y1,walkable);
			setWalkable(x,y2,walkable);			
		}
		for (int y=y1;y<=y2;y++) {
			setWalkable(x1,y,walkable);
			setWalkable(x2,y,walkable);
		}
	}
	
	public void setWalkable(int x, int y, boolean walkable) {
		node[x][y].setWalkable(walkable);
	}
	public boolean checkWalkable(int x, int y) {
		return node[x][y].checkWalkable();
	}
	
}
