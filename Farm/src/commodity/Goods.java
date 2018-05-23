package commodity;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Superclass for Goods
 * 
 * @author Elin Olsson
 *
 */
	//Kodgranskning: lägg till kommentarer
public class Goods implements Serializable {
	private int price;
	private int nbrOfGoods = 0;

	public ImageIcon getImage() {
		return null;
	}

	public int getNumberOfGoods() {
		return nbrOfGoods;
	}

	public void addGood() {
		nbrOfGoods++;
	}
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}