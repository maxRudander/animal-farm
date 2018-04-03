package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Cow extends Animal{
	
	Image cowleft1, cowleft2,cowleft3, cowright1,cowright2,cowright3;
	
	public Cow( int x , int y) {
		super(x,y);
		loadImages();
	}
	public Cow() {
		super();
		loadImages();
	}
	public void loadImages() {
		cowleft1 = new ImageIcon("images/ko1.png").getImage();
		cowleft2 = new ImageIcon("images/ko2.png").getImage();
		cowleft3 = new ImageIcon("images/ko3.png").getImage();

		cowright1 = new ImageIcon("images/ko1right.png").getImage();
		cowright2 = new ImageIcon("images/ko2right.png").getImage();
		cowright3 = new ImageIcon("images/ko3right.png").getImage();
	}
	
	public Image getNextAnimation() {
		Image animation = null;
		if (getX_direction() < 0) {
			if (getAnimation() == 0) {
				animation = cowleft1;
			} else if (getAnimation() == 1) {
				animation = cowleft2;
			} else if (getAnimation() == 2) {
				animation = cowleft3;
			}
		} else if (getX_direction() > 0) {
			if (getAnimation() == 0) {
				animation = cowright1;
			} else if (getAnimation() == 1) {
				animation = cowright2;
			} else if (getAnimation() == 2) {
				animation = cowright3;
			}
		}
		nextAnimation();
		return animation;
	
	
	
	}
	
	
}
