package farm;

import java.io.Serializable;
/**
 * 
 * @author Micke!
 *
 */
public class Node implements Serializable{
	private boolean walkable;

	public Node() {
		this.walkable = true;
	}
	public Node (boolean walkable) {
		this.walkable = walkable;
	}
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	public boolean checkWalkable() {
		return walkable;
	}
}
