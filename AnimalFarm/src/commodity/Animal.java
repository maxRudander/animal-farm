package commodity;

import java.awt.Image;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

//import farm.Node;
import main.AnimalCopy;
import property.Barn;

/**
 * Animal superclass with information about the animals coordinates and
 * direction.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk.
 */
public class Animal implements Serializable {
	private int x;
	private int y;
	private int speed = 1; // currently behaves as final, will be changed in future updates
	private int x_direction;
	private int y_direction;
	private int animation = 0;

	private Random rand = new Random();
	private final int MAX_X = 800;
	private final int MAX_Y = 800;
//	private Node[][] node;
	private Boolean[][] node;
	private int lastY_direction = 0;
	private int lastX_direction = 0;
	private int bufferFromWallX = 0;
	private int bufferFromWallY = 0;

	/**
	 * Constructor that randomize the direction of the animals movement.
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Animal(int x, int y) {
		this.x = x;
		this.y = y;
		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}
	}

	/**
	 * Constructor that randomize the location and direction of the animals
	 * movement.
	 *
	 */
	public Animal() {

		this.x = rand.nextInt(MAX_X - 140) + 100;
		this.y = rand.nextInt(MAX_Y - 140) + 100;
		// System.out.println("X= " + this.x + " Y= " + this.y );
		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setNode(Boolean[][] node) {
		this.node = node;
	}

	public AnimalCopy fetchAnimal() {
		AnimalCopy copy = new AnimalCopy();
		copy.setX(x);
		copy.setY(y);
//		copy.setRestrictedArea(restrictedArea);
		copy.setSpeed(speed);
		copy.setX_direction(x_direction);
		copy.setY_direction(y_direction);
		return copy;
	}

	/**
	 * Returns the x coordinate.
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the y coordinate.
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Return the speed of the animal
	 * 
	 * @return int speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed of the animal
	 * 
	 * @param speed - the speed.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Return the direction on the x axis.
	 * 
	 * @return the direction of the x axis.
	 */
	public int getX_direction() {
		return x_direction;
	}

	/**
	 * Set the direction on the x-axis.
	 * 
	 * @param x_direction - direction on x-axis
	 */
	public void setX_direction(int x_direction) {
		this.x_direction = x_direction;
	}

	/**
	 * Return the direction on the y axis.
	 * 
	 * @return the direction of the y axis.
	 */
	public int getY_direction() {
		return y_direction;
	}

	/**
	 * Set the direction on the y-axis.
	 * 
	 * @param y_direction - direction on y-axis
	 */
	public void setY_direction(int y_direction) {
		this.y_direction = y_direction;
	}

		
	public void move() {
		int winner;
		boolean[][] walkable = new boolean[3][3];

		checkWallBuffer();
		if (!checkWalkable(x + bufferFromWallX + x_direction, y + bufferFromWallY + y_direction)) {
			winner = rand.nextInt(checkAreaAroundCurrentNode(walkable));
			setMovementDirections(winner, walkable);
		}
		this.x = getX_direction() * speed + getX();
		this.y = getY_direction() * speed + getY();
	}

	public int checkAreaAroundCurrentNode(boolean[][] walkable) {
		int c1 = 0;
		walkable[0][0] = checkWalkable(x - 1, y - 1);
		walkable[0][1] = checkWalkable(x - 1, y);
		walkable[0][2] = checkWalkable(x - 1, y + bufferFromWallY + 1);
		walkable[1][0] = checkWalkable(x, y - 1);
		walkable[1][1] = false;
		walkable[1][2] = checkWalkable(x, y + bufferFromWallY + 1);
		walkable[2][0] = checkWalkable(x + bufferFromWallX + 1, y - 1);
		walkable[2][1] = checkWalkable(x + bufferFromWallX + 1, y);
		walkable[2][2] = checkWalkable(x + bufferFromWallX + 1, y + bufferFromWallY + 1);

		for (int xIndex = 0; xIndex < 3; xIndex++) {
			for (int yIndex = 0; yIndex < 3; yIndex++) {
				if (walkable[xIndex][yIndex] == true) {
					c1++;
				}
			}
		}
		return c1;
	}

	public void setMovementDirections(int winner, boolean[][] walkable) {
		int c2 = 0;
		int xresult = 0;
		int yresult = 0;
		boolean finished = false;
		for (int xIndex = 0; xIndex < 3 && !finished; xIndex++) {
			for (int yIndex = 0; yIndex < 3 && !finished; yIndex++) {
				if (walkable[xIndex][yIndex] == true) {
					if (winner == c2) {
						xresult = xIndex;
						yresult = yIndex;
						finished = true;
						break;
					}
					c2++;
				}
			}
		}
		if (xresult == 0) {
			x_direction = -1;
		}
		if (xresult == 1) {
			x_direction = 0;
		}
		if (xresult == 2) {
			x_direction = 1;
		}
		if (yresult == 0) {
			y_direction = -1;
		}
		if (yresult == 1) {
			y_direction = 0;
		}
		if (yresult == 2) {
			y_direction = 1;
		}

	}

	public void checkWallBuffer() {

		if (y_direction == 1) {
			bufferFromWallY = 40;
			lastY_direction = y_direction;
		} else if (y_direction == 0 && lastY_direction == 1) {
			bufferFromWallY = 40;
		} else {
			bufferFromWallY = 0;
			lastY_direction = -1;
		}

		if (x_direction == 1) {
			bufferFromWallX = 40;
			lastX_direction = x_direction;
		} else if (x_direction == 0 && lastX_direction == 1) {
			bufferFromWallX = 40;
		} else {
			bufferFromWallX = 0;
			lastX_direction = -1;
		}
	}

	/**
	 * Method used for stopmotion animation. Changes the picture every time method
	 * is run.
	 */
	public void nextAnimation() {
		animation++;
		if (animation > 2)
			animation = 0;
	}

	/**
	 * Return the current animationstate
	 * 
	 * @return the current animationstate.
	 */
	public int getAnimation() {
		return animation;
	}
	/**
	 * Not used here. Used in the children. Superclass dont got animations by
	 * itself.
	 * 
	 * @return null
	 */
	public ImageIcon getNextAnimation() {
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
		try {
			return node[x][y];
		} catch (Exception e) {
			System.out.println("X= " + x + "x_dir = " + x_direction + " Y= " + y + "y_dir= " + y_direction);
			return false;
		}
	}

	public static int getCapacity () {
		return -1;
	}
	public String getHouseType() {
		return null;
	}


}
