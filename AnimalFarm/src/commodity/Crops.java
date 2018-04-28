package commodity;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

//import farm.Node;

/**
 * author Elin O.
 */
public class Crops implements Serializable {
	private int x;
	private int y;

	// private Node[][] node;
	private Boolean[][] node;

	public Crops(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public Crops() {
		x = 0;
		y = 0;
	}

	public void setNode(Boolean[][] node) {
		this.node = node;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public ImageIcon getImage() {
		return null;
	}

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

	public void setWalkable(int x, int y, boolean walkable) {
		node[x][y] = walkable;
	}

	public boolean checkWalkable(int x, int y) {
		return node[x][y];
	}
}
