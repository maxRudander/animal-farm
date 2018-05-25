package main;

import java.io.Serializable;
//**
/**
 * A simplified version of Animal without images.
 * Used for saving and loading.
 * @author Mikael Lindfors
 *
 */

public class AnimalCopy implements Serializable{
	private String animalType = "";
	private int x;
	private int y;
	private int x_direction;
	private int y_direction;
	
/**
 * Method that sets the Animal-type in String format
 * @param type String Animal-type.
 */
	public void setAnimalType(String type) {
		this.animalType = type;
	}
	/**
	 * Method used for returning the Animal-type in String format.
	 * @return String Animal-type.
	 */
	public String getAnimalType() {
		return animalType;
	}
	/**
	 * Sets the x-coordinate.
	 * @param x x-coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Returns the x-coordinate.
	 * @return int x-coordinate
	 */
	public int getX() {
		return x;
	}
	/**
	 * Sets the y-coordinate.
	 * @param y y-coordinate
	 */
	
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Returns the x-coordinate.
	 * @return int x-coordinate
	 */
	
	public int getY() {
		return y;
	}

	/**
	 * Return the direction on the x axis.
	 * 
	 * @return the direction of thexy axis.
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
}

