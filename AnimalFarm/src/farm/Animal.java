package farm;

import java.util.Random;
/**
 *  Animal superclass with information about the animals coordinates and direction.
 *  
 *  @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt, Matthias Svensson Falk
 */


public class Animal {
	private int x;
	private int y;
	private int speed = 3;
	private int x_direction;
	private int y_direction;
	private int animation = 0;
	
	Random rand = new Random();

	/**
	 * Constructor that randomize the direction of the animals movement.
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
	 * Constructor that randomize the location and direction of the animals movement.
	 * @param x - Random x coordinate
	 * @param y - Random y coordinate
	 */
	public Animal() {
		this.x = rand.nextInt(665-80);
		this.y = rand.nextInt(535-80);
		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}
		
	}
/**
 * Returns the x coordinate.
 * @return x coordinate
 */
	public int getX() {
		return x;
	}
	/**
	 * Returns the y coordinate.
	 * @return y coordinate
	 */

	public int getY() {
		return y;
	}

	/**
	 * Return the speed of the animal
	 * @return int speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed of the animal
	 * @param speed - the speed.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Return the direction on the x axis.
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
	 * @return the direction of the y axis.
	 */
	public int getY_direction() {
		return y_direction;
	}
	/**
	 * Set the direction on the y-axis.
	 * 
	 * @param x_direction - direction on y-axis
	 */
	public void setY_direction(int y_direction) {
		this.y_direction = y_direction;
	}
	/**
	 * Method that moves the animal, but  when it reach a border it will change direction.
	 */
	public void move() {										// X & Y values = board max -80
		
		if ( x <= 0 || x >=800-80 ) {
			setX_direction(getX_direction() * -1);
		}
		else if ( y<=0 || y >=600-80) {
			setY_direction(getY_direction() * -1);
		}
		
		
		this.x = getX_direction() + getX();
		this.y = getY_direction() + getY();
	}
	
	/**
	 * Method used for stopmotion animation. Changes the picture every time method is run.
	 */
	public void nextAnimation() {
		animation++;
		if ( animation > 2 )
			animation = 0;
	}
	/**
	 * Return the current animationstate
	 * @return the current animationstate.
	 */
	public int getAnimation() {
		return animation;
	}
	
}
