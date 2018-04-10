package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Stable extends Building {
	
	Image stable;
	
	public Stable(int x, int y) {
		super(x,y);
		loadImages();
	}
	
	public Stable() {
		super();
		loadImages();
	}
	
	public void loadImages() {
		stable = new ImageIcon("images/stable.png").getImage();
		
	}
	
	public Image getImage() {
		return stable;
	}

}
