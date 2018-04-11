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

	private final int MAX_X = 800; // OriginalValue 665
	private final int MAX_Y = 800; // OriginalValue 535
	private final int ANIMAL_SIZE = 40;
	private final int BUILDING_SIZE = 80; 
	private final int DELAY = 40;
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
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void addFence(Fence fence) {
		int restrictedX;
		int restrictedY;
		fence.setRestrictedArea(fence.getX1(), fence.getY1(), fence.getX2(), fence.getX1());
		for (int i = 0; i < buildingList.size(); i++) {
			restrictedX = buildingList.get(i).getX();
			restrictedY = buildingList.get(i).getY();
			fence.setRestrictedArea(restrictedX, restrictedY, restrictedX + BUILDING_SIZE, restrictedY + BUILDING_SIZE);
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
		int x1;
		int y1;
		for (int i = 0; i < buildingList.size(); i++) {
			x1 = buildingList.get(i).getX();
			y1 = buildingList.get(i).getY();
			animal.setRestrictedAreaOutBound(x1, y1, x1 + BUILDING_SIZE, y1 + BUILDING_SIZE);

		}
		animal.setRestrictedAreaInbound(0, 0, MAX_X, MAX_Y);
		animalList.add(animal);
	}
	
	public void addBuilding(Building building) {
		building.setRestrictedArea(building.getX(), building.getY(), building.getX() + BUILDING_SIZE, building.getY() + BUILDING_SIZE);
		buildingList.add(building);
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).setRestrictedAreaOutBound(building.getX(), building.getY(), building.getX() + BUILDING_SIZE,
					building.getY() + BUILDING_SIZE);
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
		if (animal instanceof Sheep) {
			for (int i = 0; i < animalList.size(); i++) {
				if (animalList.get(i) instanceof Sheep) {
					animalList.remove(i);
					break;
				}
			}
		}
		if (animal instanceof Chicken) {
			for (int i = 0; i < animalList.size(); i++) {
				if (animalList.get(i) instanceof Chicken) {
					animalList.remove(i);
					break;
				}
			}
		}
	}
			public void removeBuilding(Building building) {
				if (building instanceof Barn) {
					for (int i = 0; i < buildingList.size(); i++) {
						if (buildingList.get(i) instanceof Barn) {
							buildingList.remove(i);
							break;
						}
					}
				}
				if (building instanceof Pigsty) {
					for (int i = 0; i < buildingList.size(); i++) {
						if (buildingList.get(i) instanceof Pigsty) {
							buildingList.remove(i);
							break;
						}
					}
				}
				if (building instanceof HenHouse) {
					for (int i = 0; i < buildingList.size(); i++) {
						if (buildingList.get(i) instanceof HenHouse) {
							buildingList.remove(i);
							break;
						}
					}
				}
				if (building instanceof Stable) {
					for (int i = 0; i < buildingList.size(); i++) {
						if (buildingList.get(i) instanceof Stable) {
							buildingList.remove(i);
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
		int gridSize = MAX_X / 20;
		int buildingIndex;
		boolean onBuilding = false;
		ArrayList list = new ArrayList();
		RestrictedArea area;
		for (int x = 0; x < MAX_X; x = x + gridSize) {
			for (buildingIndex = 0; buildingIndex < buildingList.size(); buildingIndex++) {
				area = buildingList.get(buildingIndex).getRestrictedArea();
				for (int y = 0; y < MAX_Y; y++) {
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
				g.drawLine(x, (int) list.get(list.size() - 1), x, MAX_Y);
			} else {
				g.drawLine(x, 0, x, MAX_Y);
			}
		}
		for (int y = 0; y < MAX_Y; y = y + gridSize) {
			list = new ArrayList();
			for (buildingIndex = 0; buildingIndex < buildingList.size(); buildingIndex++) {
				area = buildingList.get(buildingIndex).getRestrictedArea();
				for (int x = 0; x < MAX_X; x++) {
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
				g.drawLine((int) list.get(list.size() - 1), y, MAX_X, y);
			} else {
				g.drawLine(0, y, MAX_X, y);
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
		Image animation;
		Building building;
		for (int i = 0; i < buildingList.size(); i++) {
			building = buildingList.get(i);
			animation = building.getImage();
			g.drawImage(animation, building.getX(), building.getY(), building.getX() + BUILDING_SIZE,
					building.getY() + BUILDING_SIZE, 0, 0, animation.getWidth(this), animation.getHeight(this), this);
		}

	}
	public void drawAnimals(Graphics2D g) {
		Image animation;
		Animal animal;
		for (int i = 0; i < animalList.size(); i++) {
			animal = animalList.get(i);
			animation = animal.getNextAnimation();
			g.drawImage(animation, animal.getX(), animal.getY(), animal.getX() + ANIMAL_SIZE, animal.getY() + ANIMAL_SIZE,
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
		g.drawRect(0, 0, MAX_X, MAX_Y);
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
