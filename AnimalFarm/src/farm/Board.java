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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class that handles the animations in the mainpanel.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk
 *
 */
public class Board extends JPanel implements ActionListener {

	private final int max_x = 800; // Original value 665
	private final int max_y = 800; // OriginalValue 535
	private final int animalsize = 40;
	private final int buildingsize = 80;
	private boolean grid = false;
	private Graphics2D g;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private Timer timer;

	public Board() {
		initBoard();
	}

	/**
	 * Method that initilize the board.
	 */
	private void initBoard() {

		this.setBackground(new Color(130, 202, 112));
		setPreferredSize(new Dimension(800, 600));
		setDoubleBuffered(true);

		// addBarn(new Barn(100, 100));
		// addBarn(new Barn(100, 380));
		// addBarn(new Barn(380, 100));
		// addBarn(new Barn(380, 380));
		// // timer = new Timer(40, this);
		timer = new Timer(15, this);
		timer.start();
	}

	public void addFence(Fence fence) {

		fence.setRestrictedArea(fence.getX1(), fence.getY1(), fence.getX2(), fence.getX1());
		for (int i = 0; i < buildingList.size(); i++) {
			int restrictedX = buildingList.get(i).getX();
			int restrictedY = buildingList.get(i).getY();
			fence.setRestrictedArea(restrictedX, restrictedY, restrictedX + buildingsize, restrictedY + buildingsize);
		}
		buildingList.add(fence);
	}

	/**
	 * Adds an animal to the list with animals.
	 * 
	 * @param animal
	 *            - An object to be added
	 */

	public void addAnimal(Animal animal) {

		for (int i = 0; i < buildingList.size(); i++) {
			int x1 = buildingList.get(i).getX();
			int y1 = buildingList.get(i).getY();
			animal.setRestrictedAreaOutBound(x1, y1, x1 + buildingsize, y1 + buildingsize);

		}
		animal.setRestrictedAreaInbound(0, 0, max_x, max_y);
		animalList.add(animal);
	}
	public void addBuilding(Building building) {
		building.setRestrictedArea(building.getX(), building.getY(), building.getX() + buildingsize, building.getY() + buildingsize);
		buildingList.add(building);
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).setRestrictedAreaOutBound(building.getX(), building.getY(), building.getX() + buildingsize,
					building.getY() + buildingsize);
		}
	}
	

	/**
	 * Remove an animal from the list with animals.
	 * 
	 * @param animal
	 *            - An object to be removed.
	 */
	public void removeAnimal(Animal animal) {
		if (animal instanceof Cow) {
			for (int i = 0; i < animalList.size(); i++) {
				if (animalList.get(i) instanceof Cow) {
					animalList.remove(i);
					break;
				}
			}
		}
		if (animal instanceof Pig) {
			for (int i = 0; i < animalList.size(); i++) {
				if (animalList.get(i) instanceof Pig) {
					animalList.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * Method that draws the animations in the mainpanel.
	 * 
	 * @param gg
	 *            - Graphics where animations will be drawn.
	 */
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);

		g = (Graphics2D) gg;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHints(rh);

		drawEdges(g);
		drawAnimals(g);
		drawBuildings(g);
		
		if (grid) {
			drawGrid(g);
		}

	}

	public void drawFence(Graphics2D g, int x1, int y1, int x2, int y2) {
		int xdistance = x2 - x1;
		int ydistance = y2 - y1;
		ArrayList coords = new ArrayList();
		int nbrFenceParts = xdistance / 20;
		for (int i = 0; i < nbrFenceParts; i = i + 20) {
			coords.add(x1 + i);
		}
		for (int i = 0; i < coords.size(); i++) {
			g.drawLine((int) coords.get(i), y1, (int) coords.get(i), y2);
		}
	}

	public void drawGrid(Graphics2D g) {
		int gridSize = max_x / 20;
		boolean onBuilding = false;
		for (int x = 0; x < max_x; x = x + gridSize) {
			ArrayList list = new ArrayList();
			for (int buildingIndex = 0; buildingIndex < buildingList.size(); buildingIndex++) {
				RestrictedArea area = buildingList.get(buildingIndex).getRestrictedArea();
				for (int y = 0; y < max_y; y++) {
					if (!area.check(x, y) && !onBuilding) {
						list.add(y);
						onBuilding = true;
					} else if (area.check(x, y) && onBuilding) {
						list.add(y);
						onBuilding = false;
					}
				}
			}
			if (!list.isEmpty()) {
				g.drawLine(x, 0, x, (int) list.get(0));
				for (int i = 1; i < list.size() - 2; i++) {
					g.drawLine(x, (int) list.get(i), x, (int) list.get(i + 1));
				}
				g.drawLine(x, (int) list.get(list.size() - 1), x, max_y);
			} else {
				g.drawLine(x, 0, x, max_y);
			}
		}
		for (int y = 0; y < max_y; y = y + gridSize) {
			ArrayList list = new ArrayList();
			for (int buildingIndex = 0; buildingIndex < buildingList.size(); buildingIndex++) {
				RestrictedArea area = buildingList.get(buildingIndex).getRestrictedArea();
				for (int x = 0; x < max_x; x++) {
					if (!area.check(x, y) && !onBuilding) {
						list.add(x);
						onBuilding = true;
					} else if (area.check(x, y) && onBuilding) {
						list.add(x);
						onBuilding = false;
					}
				}
			}
			if (!list.isEmpty()) {
				g.drawLine(0, y, (int) list.get(0), y);
				for (int i = 1; i < list.size() - 2; i++) {
					g.drawLine((int) list.get(i), y, (int) list.get(i + 1), y);
				}
				g.drawLine((int) list.get(list.size() - 1), y, max_x, y);
			} else {
				g.drawLine(0, y, max_x, y);
			}
		}
	}

	/**
	 * Method that draws buildings in the mainpanel
	 * 
	 * @param g
	 *            - Graphics g where animations will be drawn.
	 */

	public void drawBuildings(Graphics2D g) {
		Image animation = null;
		for (int i = 0; i < buildingList.size(); i++) {
			Building building = buildingList.get(i);
			animation = building.getImage();
			g.drawImage(animation, building.getX(), building.getY(), building.getX() + buildingsize,
					building.getY() + buildingsize, 0, 0, animation.getWidth(this), animation.getHeight(this), this);
		}

	}
	public void drawAnimals(Graphics2D g) {
		Image animation = null;
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			animation = animal.getNextAnimation();
			g.drawImage(animation, animal.getX(), animal.getY(), animal.getX() + animalsize, animal.getY() + animalsize,
					0, 0, animation.getWidth(this), animation.getHeight(this), this);
		}
	}

	/**
	 * Method that draws a border where the animals can wander around.
	 * 
	 * @param g
	 *            - Graphics2D where the rectangle will be drawn.
	 */
	public void drawEdges(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, max_x, max_y);
	}

	public void drawBorder(Graphics2D g, int x1, int y1, int x2, int y2) {
		g.setColor(Color.BLACK);
		g.drawRect(x1, y1, x2 - x1, y2 - y1);		
	}

	/**
	 * Method that loads all images used for animations.
	 */

	public void grid(boolean status) {
		this.grid = status;
	}

	/**
	 * ActionListener that moves the animals every 40ms (25fps) and repaints the
	 * mainpanel.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).move();
		}
		repaint();
	}
}
