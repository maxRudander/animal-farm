package commodity;

import java.io.Serializable;
import javax.swing.ImageIcon;


/**
 * Superclass for all the crops
 * @author Elin Olsson.
 */
public class Crops implements Serializable {
	private int x;
	private int y;
	private boolean[][] node;
	/**
	 * Set the location for the crop
	 * @param x the x location
	 * @param y the y location
	 */
	public Crops(int x, int y) {

		this.x = x;
		this.y = y;
	}
	/**
	 * sets the crop to 0,0
	 */
	public Crops() {
		x = 0;
		y = 0;
	}
	/**
	 * declares the array node to the received node
	 * and sets the node for the board array 
	 * node is a  1600x1600 array 
	 * @param node 1600x1600 boolean array
	 */
	public void setNode(boolean[][] node) {
		this.node = node;
	}
	/**
	 * sets the x location
	 * @param x xlocation
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * return the x location
	 * @return the x location
	 */
	public int getX() {
		return x;
	}
	/**
	 * sets the y location
	 * @param y y location
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * return the y location
	 * @return the y location
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Method that returns the Image of the Crop
	 * 
	 * @return Image of the crops
	 */
	public ImageIcon getImage() {
		return null;
	}
	/**
	 * Method that sets an rectangle around an area between x1y1 and x2y2 walkable true or false
	 * @param x1 x1 coord 
	 * @param y1 y1 coord
	 * @param x2 x2 coord
	 * @param y2 y2 coord
	 * @param walkable boolean walkable 
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
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param walkable boolean true or false
	 */
	public void setWalkable(int x, int y, boolean walkable) {
		node[x][y] = walkable;
	}
	/**
	 * Method that checks an pixel if its walkable or not.
	 * @param x x coord 
	 * @param y y coord
	 * @return boolean true or false
	 */
	public boolean checkWalkable(int x, int y) {
		return node[x][y];
	}
}
