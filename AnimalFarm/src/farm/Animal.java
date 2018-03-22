package farm;

import java.util.Random;

public class Animal {
	private int x;
	private int y;
	private int speed = 3;
	private int x_direction;
	private int y_direction;
	private int animation = 0;
	
	Random rand = new Random();

	public Animal(int x, int y) {
		this.x = x;
		this.y = y;

		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}

	}

	public Animal() {
		this.x = rand.nextInt(665-80);
		this.y = rand.nextInt(535-80);
		while (x_direction == 0 || y_direction == 0) {
			x_direction = rand.nextInt(3) - 1;
			y_direction = rand.nextInt(3) - 1;
		}
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX_direction() {
		return x_direction;
	}

	public void setX_direction(int x_direction) {
		this.x_direction = x_direction;
	}

	public int getY_direction() {
		return y_direction;
	}

	public void setY_direction(int y_direction) {
		this.y_direction = y_direction;
	}
	public void move() {										// X & Y values = board max -80
		
		if ( x <= 0 || x >=800-80 ) {
			setX_direction(getX_direction() * -1);
		}
		else if ( y<=0 || y >=600-80) {
			setY_direction(getY_direction() * -1);
		}
		
		
		this.x = getX_direction() + getX();
		this.y = getY_direction() + getY();
	}
	
	public void nextAnimation() {
		animation++;
		if ( animation > 2 )
			animation = 0;
	}
	public int getAnimation() {
		return animation;
	}
	
}
