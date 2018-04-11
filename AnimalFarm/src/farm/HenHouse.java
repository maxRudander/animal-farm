package farm;

import java.awt.Image;

import javax.swing.ImageIcon;

public class HenHouse extends Building {
	
	private Image henHouse;
	
	public HenHouse(int x, int y) {
		super(x,y);
		loadImages();
	}
	
	public HenHouse() {
		super();
		loadImages();
	}
	
	public void loadImages() {
		henHouse = new ImageIcon("images/henhouse.png").getImage();
		
	}
	
	public Image getImage() {
		return henHouse;
	}

}
