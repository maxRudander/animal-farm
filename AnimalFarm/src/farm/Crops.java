package farm;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * author Elin O
 */
public class Crops implements Serializable{
	private int x;
	private int y;
	private RestrictedArea restrictedArea;

	public Crops(int x, int y) {
		restrictedArea = new RestrictedArea();
		this.x = x;
		this.y = y;
	}

	public Crops() {
		x = 0;
		y = 0;
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

	public void setRestrictedArea(int x1, int y1, int x2, int y2) {
		restrictedArea.addOutBoundBorder(x1, y1, x2, y2);
	}

	public RestrictedArea getRestrictedArea() {
		return restrictedArea;
	}

	public ImageIcon getImage() {
		return null;
	}
}
