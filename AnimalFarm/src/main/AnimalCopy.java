package main;

import java.io.Serializable;
//**

import java.util.Random;

public class AnimalCopy implements Serializable{
	private String animalType = "";
	private int x;
	private int y;
	private int speed = 1; // currently behaves as final, will be changed in future updates
	private int x_direction;
	private int y_direction;
	private int animation = 0;
	private Random rand = new Random();
	private final int MAX_X = 800;
	private final int MAX_Y = 800;

	public void setAnimalType(String type) {
		this.animalType = type;
	}
	public String getAnimalType() {
		return animalType;
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

	public int getAnimation() {
		return animation;
	}



	}

