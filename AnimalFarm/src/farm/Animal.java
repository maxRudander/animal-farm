package farm;

import java.awt.Image;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * Animal superclass with information about the animals coordinates and
 * direction.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk
 */
public class Animal implements Serializable{
	private int x;
	private int y;
	private int speed = 1; // currently behaves as final, will be changed in future updates
	private int x_direction;
	private int y_direction;
	private int animation = 0;
	private RestrictedArea restrictedArea;
	private Random rand = new Random();
	private final int MAX_X = 800;
	private final int MAX_Y = 800;

	/**
	 * Constructor that randomize the direction of the animals movement.
	 * 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Animal(int x, int y) {
		restrictedArea = new RestrictedArea();
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
		restrictedArea = new RestrictedArea();
		this.x = rand.nextInt(MAX_X - 40);
		this.y = rand.nextInt(MAX_Y - 40);
		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}
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

	/**
	 * Method that check if next step is not within an restricted area. If it is
	 * restricted the animal will change direction.
	 *
	 */
	public void move() {
		if (!restrictedArea.checkMovement(x + x_direction, y + y_direction)) {
			if (!restrictedArea.checkMovement(x + (x_direction * -1), y + y_direction)) {
				setY_direction(getY_direction() * -1);
				if (rand.nextInt(2) == 0) { // 50% chance that it change X direction as well for more randomness in
											// movement.
					setX_direction(getX_direction() * -1);
				}
			} else if (!restrictedArea.checkMovement(x + x_direction, (y + y_direction * -1))) {
				setX_direction(getX_direction() * -1);
				if (rand.nextInt(2) == 0) {// 50% chance that it change Y direction as well for more randomness in
											// movement.
					setY_direction(getY_direction() * -1);
				}
			}
		}
		this.x = getX_direction() * speed + getX();
		this.y = getY_direction() * speed + getY();
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
	 * Method that sets an restricted area for the animal (inbound). In this case
	 * the animal is trapped within an safe area with restriced space outside (aka
	 * surrounded by a fence).
	 * 
	 * @param x1 - x coord upper left corner.
	 * @param y1 - y coord upper left corner
	 * @param x2 - x coord lower right corner
	 * @param y2 - y coord lower right corner
	 */
	public void setRestrictedAreaInbound(int x1, int y1, int x2, int y2) {
		restrictedArea.addInBoundBorder(x1, y1, x2, y2);
	}

	/**
	 * Method that sets an restricted area for the animal (outbound). In this case
	 * the animal cant enter a restricted area (Building) but are free to go
	 * elsewhere.
	 * 
	 * @param x1 - x coord upper left corner.
	 * @param y1 - y coord upper left corner
	 * @param x2 - x coord lower right corner
	 * @param y2 - y coord lower right corner
	 */
	public void setRestrictedAreaOutBound(int x1, int y1, int x2, int y2) {
		restrictedArea.addOutBoundBorder(x1, y1, x2, y2);
	}
	public void removeRestrictedAreaOutbound(int x1, int y1, int x2, int y2) {
		restrictedArea.removeOutBoundBorder(x1, y1, x2, y2);
	}
	
	/**
	 * Method that returns the restricted areas the animal is bound to.
	 * 
	 * @return - restrictedArea with information where its allowed/not allowed to
	 *         go.
	 */
	public RestrictedArea getRestricedArea() {
		return restrictedArea;
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
}
