package farm;

public class Building {
	private int x;
	private int y;
	
	public Building( int x, int y) {
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
}
