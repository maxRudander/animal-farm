package property;

import java.awt.Image;
/**
 * 
 * @author M
 *
 */
public class Field {
	private int x;
	private int y;
		
	public Field(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public Field() {
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
	
	
	public Image getImage() {
		return null;
	}

}
