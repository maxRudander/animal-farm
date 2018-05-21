package commodity;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Superclass for Goods
 * 
 * @author elin olsson
 *
 */
public class Goods implements Serializable {
	private int price;
	private int nbrOfGoods = 0;
	private int maxCapacity = 20;

	public ImageIcon getImage() {
		return null;
	}

	public int getNumberOfGoods() {
		return nbrOfGoods;
	}

	public void addGood() {
		nbrOfGoods++;
	}

	public void removeAnimal() {
		nbrOfGoods--;
	}

	public boolean isFull() {
		if (nbrOfGoods >= maxCapacity) {
			return true;
		}
		return false;
	}
	public static int getCapacity() {
		return 0;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}