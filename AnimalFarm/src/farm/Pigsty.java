package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pigsty extends Building {
	
	Image pigsty;
	
	public Pigsty(int x, int y) {
		super(x,y);
		loadImages();
	}
	
	public Pigsty() {
		super();
		loadImages();
	}
	
	public void loadImages() {
		pigsty = new ImageIcon("images/pigsty.png").getImage();
		
	}
	
	public Image getImage() {
		return pigsty;
	}

}
