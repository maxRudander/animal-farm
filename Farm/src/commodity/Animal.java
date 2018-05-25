package commodity;

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
 *         Matthias Falk.
 */
public class Animal implements Serializable {
	private int x;
	private int y;
	private int x_direction;
	private int y_direction;
	private int animation = 0;
	private Random rand = new Random();
	private final int SPEED = 1;
	private final int MAX_X = 1600;
	private final int MAX_Y = 1600;
	private int lastY_direction = 0;
	private int lastX_direction = 0;
	private int bufferFromWallX = 0;
	private int bufferFromWallY = 0;
	private boolean[][] node;

	/**
	 * Constructor that sets x and y coordinates and randomize the direction of the
	 * animals movement.
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
		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}
	}

	/**
	 * Method that sets x-coordinate for the animal
	 * 
	 * @param x x-coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Method that sets y-coordinate for the animal
	 * 
	 * @param y y-coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Method that gets a copy of the Boards node on pixel level (1600x1600)
	 * 
	 * @param node boolean array with every pixels walkable status.
	 */
	public void setNode(boolean[][] node) {
		this.node = node;
	}

	/**
	 * Method used for saving games. Takes a animals location and direction and
	 * return it (without ImageIcon object). Used for saving space in savegames.
	 * 
	 * @return Animalcopy - Animalclass-copy without ImageIcons and unnecessary data
	 */

	public AnimalCopy fetchAnimal() {
		AnimalCopy copy = new AnimalCopy();
		copy.setX(x);
		copy.setY(y);
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
		return SPEED;
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

	/**
	 * Method that check all pixels around the animals coordinate and check which
	 * one is walkable. When all walkable areas is found, a random function chose a
	 * winning direction and the animal will change direction.
	 */

	public void move() {
		int winner;
		boolean[][] walkable = new boolean[3][3];
		checkWallBuffer();
		if (!checkWalkable(x + bufferFromWallX + x_direction, y + bufferFromWallY + y_direction)) {
			winner = rand.nextInt(checkAreaAroundCurrentNode(walkable));
			setMovementDirections(winner, walkable);
		}
		this.x = getX_direction() * SPEED + getX();
		this.y = getY_direction() * SPEED + getY();
	}

	/**
	 * Method that checks all pixels around the animal and check which one is
	 * walkable.
	 * 
	 * @param walkable node array with 1600x1600 slots.
	 * @return the winning direction in integer.
	 */
	public int checkAreaAroundCurrentNode(boolean[][] walkable) {
		int counter = 0;
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
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * Method that sets the movement direction for the animal using the winning
	 * direction
	 * 
	 * @param winner Integer with the winning direction
	 * @param walkable the walkable node in 1600x1600.
	 */
	public void setMovementDirections(int winner, boolean[][] walkable) {
		int counter = 0;
		int xresult = 0;
		int yresult = 0;
		boolean finished = false;
		for (int xIndex = 0; xIndex < 3 && !finished; xIndex++) {
			for (int yIndex = 0; yIndex < 3 && !finished; yIndex++) {
				if (walkable[xIndex][yIndex] == true) {
					if (winner == counter) {
						xresult = xIndex;
						yresult = yIndex;
						finished = true;
						break;
					}
					counter++;
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

	/**
	 * Method that sets the wallbuffer (the difference between x,y coordinate and
	 * non-walkablearea) When x_direction or y_direction the buffer will be 40
	 * pixels for the image size.
	 */
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

	/**
	 * Method that sets an rectangle around an area between x1y1 and x2y2 walkable
	 * true or false
	 * 
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
	 * @param x x coord
	 * @param y y coord
	 * @return boolean true or false
	 */
	public boolean checkWalkable(int x, int y) {
		return node[x][y];
	}

	/**
	 * Method used in children
	 * 
	 * @return not used
	 */
	public static int getCapacity() {
		return -1;
	}

	/**
	 * Method used in children
	 * 
	 * @return not used
	 */
	public String getHouseType() {
		return null;
	}

}
