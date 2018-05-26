package commodity;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Superclass for Goods
 * 
 * @author Elin Olsson
 *
 */
public class Goods implements Serializable {
	private int price;
	private int nbrOfGoods = 0;

	
	/**
	 * Method that returns the goods image
	 * @return  the goods image
	 */
	public ImageIcon getImage() {
		return null;
	}
/**
 * Method that gets the number of goods 
 * @return the number of goods
 */
	public int getNumberOfGoods() {
		return nbrOfGoods;
	}
/**
 * method that adds a good 
 */
	public void addGood() {
		nbrOfGoods++;
	}
	/**
	 * method that returns the price of a specific good
	 * @return the goods price 
	 */
	public int getPrice() {
		return price;
	}
/**
 * method that sets a goods price 
 * @param price a goods price 
 */
	public void setPrice(int price) {
		this.price = price;
	}
}