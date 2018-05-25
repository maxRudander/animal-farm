package property;

/**
 * Class that handles the Fence building.
 * 
 * @author Mikael Lindfors.
 *
 */
//Kodgranskning: Flytta till property paket
public class Fence extends Building {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	/**
	 * Constructor that sets x1,y1,x2,y2 coordinates.
	 * 
	 * @param x1 x1 coordinate
	 * @param y1 y1 coordinate
	 * @param x2 x2 coordinate
	 * @param y2 y2 coordinate
	 */
	public Fence(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	/**
	 * Method that returns x1 coordinate
	 * 
	 * @return x1 coordinate
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * Method that sets the x1 coordinate
	 * 
	 * @param x1 x1 coordinate
	 */
	public void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * Method that returns x2 coordinate
	 * 
	 * @return x2 coordinate
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Method that sets the x2 coordinate
	 * 
	 * @param x2 x2 coordinate
	 */
	public void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * Method that returns y1 coordinate
	 * 
	 * @return y1 coordinate
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Method that sets the y1 coordinate
	 * 
	 * @param y1 y1 coordinate
	 */
	public void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * Method that returns y2 coordinate
	 * 
	 * @return y2 coordinate
	 */
	public int getY2() {
		return y2;
	}

	/**
	 * Method that sets the y2 coordinate
	 * 
	 * @param y2 y2 coordinate
	 */
	public void setY2(int y2) {
		this.y2 = y2;
	}
}
