package farm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * Class that handles the animations in the mainpanel.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt, Matthias Svensson Falk
 *
 */
public class Board extends JPanel implements ActionListener {

	public int x = 800;					//Original value 800
	public int y = 600;					//Original value 600
	private int animalsize = 40;
	private int buildingsize = 80;

	private int max_x = 800;			//Original value 665
	private int max_y = 600;			//OriginalValue 535
	int choice = 0;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private Image cowleft1, cowleft2, cowleft3, pinkcow, barnpic, cowright1, cowright2, cowright3;
	private Timer timer;

	public Board() {
		initBoard();
	}
	
	/**
	 * Method that loads all images used for animations.
	 */
	private void loadImages() {
		cowleft1 = new ImageIcon("images/ko1.png").getImage();
		cowleft2 = new ImageIcon("images/ko2.png").getImage();
		cowleft3 = new ImageIcon("images/ko3.png").getImage();

		cowright1 = new ImageIcon("images/ko1right.png").getImage();
		cowright2 = new ImageIcon("images/ko2right.png").getImage();
		cowright3 = new ImageIcon("images/ko3right.png").getImage();
		barnpic = new ImageIcon("images/Barn.png").getImage();
	}

	/**
	 * Method that initilize the board.
	 */
	private void initBoard() {
		
		this.setBackground(new Color(130,202,112));
		setPreferredSize(new Dimension(800, 600));
		setDoubleBuffered(true);
		loadImages();
		buildingList.add(new Barn(100,100));
		timer = new Timer(40, this);
		timer.start();
	}

	/**
	 * Adds an animal to the list with animals.
	 * @param animal - An object to be added 
	 */
	public void addAnimal(Animal animal) {
		animalList.add(animal);
	}

	/**
	 * Remove an animal from the list with animals.
	 * @param animal - An object to be removed.
	 */
	public void removeAnimal(Animal animal) {
		if (animal instanceof Cow) {
			for (int i=0;i<animalList.size();i++ ) {
				if (animalList.get(i) instanceof Cow) {
					animalList.remove(i);
					break;
				}
			}
			
		}
		if (animal instanceof Pig) {
			for(int i=0;i<animalList.size();i++) {
				if (animalList.get(i) instanceof Pig) {
					animalList.remove(i);
					break;
				}
			}
			
		}

	}

	/**
	 * Method that draws the animations in the mainpanel.
	 *  @param gg - Graphics where animations will be drawn.
	 */
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHints(rh);

		drawEdges(g);
		drawAnimals(g);
		drawBuildings(g);
	}
/**
 *	Method that draws buildings in the mainpanel 
 * @param g - Graphics g where animations will be drawn.
 */
	public void drawBuildings(Graphics2D g) {
		for (int i = 0; i < buildingList.size(); i++) {
			if (buildingList.get(i) instanceof Barn) {
				Barn barn = (Barn) buildingList.get(i);
				g.drawImage(barnpic, barn.getX(), barn.getY(), barn.getX() + buildingsize, barn.getY() + buildingsize,
						0, 0, 856, 638, this);
			}
		}
	}

	public void drawAnimals(Graphics2D g) {
		for (int i = 0; i < animalList.size(); i++) {
			if (animalList.get(i) instanceof Cow) {
				Cow cow = (Cow) animalList.get(i);
				if (cow.getX_direction() < 0) {
					if (cow.getAnimation() == 0) {
						g.drawImage(cowleft1, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 1) {
						g.drawImage(cowleft2, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 2) {
						g.drawImage(cowleft3, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					}
				} else if (cow.getX_direction() > 0) {
					if (cow.getAnimation() == 0) {
						g.drawImage(cowright1, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 1) {
						g.drawImage(cowright2, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 2) {
						g.drawImage(cowright3, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					}
				}
				cow.nextAnimation();
			} else if (animalList.get(i) instanceof Pig) {

			}
		}

	}
	/**
	 * Method that draws a border where the animals can wander around.
	 * @param g - Graphics2D where the rectangle will be drawn.
	 */
	public void drawEdges(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, max_x, max_y);
	}

	
/**
 * ActionListener that moves the animals every 40ms (25fps) and repaints the mainpanel. 
 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).move();
		}
		repaint();

	}
}
