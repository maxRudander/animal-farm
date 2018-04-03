package farm;

import java.awt.Image;

public class Building {
	private int x;
	private int y;
	private RestrictedArea restrictedArea;

	public Building(int x, int y) {
		restrictedArea = new RestrictedArea();
		this.x = x;
		this.y = y;
	}

	public Building() {
		x = 0;
		y = 0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setRestrictedArea(int x1, int y1, int x2, int y2) {
		restrictedArea.addOutBoundBorder(x1, y1, x2, y2);
	}

	public RestrictedArea getRestrictedArea() {
		return restrictedArea;
	}
	public Image getImage() {
		return null;
	}
}
