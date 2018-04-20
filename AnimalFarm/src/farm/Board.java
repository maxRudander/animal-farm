package farm;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

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
	private final int MAX_X = 800; // Original value 665
	private final int MAX_Y = 800; // OriginalValue 535
	private final int ANIMALSIZE = 40;
	private final int BUILDINGSIZE = 80;
	private final int CROPSSIZE = 40;
	public static final Color SPRING = new Color(183, 215, 132);
	public static final Color SUMMER = new Color(130, 202, 112);
	public static final Color AUTUMN = new Color(243, 188, 46);
	public static final Color WINTER = new Color(179, 218, 241);
	private boolean grid = false;
	private Graphics2D g;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private LinkedList<Crops> cropsList = new LinkedList<Crops>();
	private ArrayList<Fence> fenceList = new ArrayList<Fence>();
	private Timer timer;

	public Board() {
		initBoard();
	}

	/**
	 * Method that initializes the board.
	 */
	private void initBoard() {
		//		this.setBackground(new Color(130, 202, 112));
		alterSeason(SUMMER);
		setPreferredSize(new Dimension(MAX_X, MAX_Y));
		setDoubleBuffered(true);
		addFence(100, 100, 300, 300); // #1
		addFence(400, 100, 600, 300); // #2
		addFence(100, 400, 300, 600); // #3
		addBuilding(new Barn(400, 400));
		for (int i = 0; i < 10; i++) {
			addAnimal(new Cow(150, 150));
		}
		for (int i = 0; i < 10; i++) {
			addAnimal(new Pig(450, 150));
		}
		for (int i = 0; i < 10; i++) {
			addAnimal(new Sheep(150, 450));
		}
		timer = new Timer(40, this);
		timer.start();
	}
	public void alterSeason(Color color) {
		setBackground(color);
	}

	/**
	 * Method that adds an animal to the list with animals and sets restricted area
	 * for the animal.
	 * 
	 * @param animal - An animal object to be added
	 */
	public void addAnimal(Animal animal) {
		int x1;
		int y1;
		int x2;
		int y2;
		for (int i = 0; i < buildingList.size(); i++) {
			x1 = buildingList.get(i).getX();
			y1 = buildingList.get(i).getY();
			animal.setRestrictedAreaOutBound(x1, y1, x1 + BUILDINGSIZE, y1 + BUILDINGSIZE);
		}
		for (int i = 0; i < fenceList.size(); i++) {
			x1 = fenceList.get(i).getX1();
			y1 = fenceList.get(i).getY1();
			x2 = fenceList.get(i).getX2();
			y2 = fenceList.get(i).getY2();
			animal.setRestrictedAreaInbound(x1, y1, x2, y2);
			animal.setRestrictedAreaOutBound(x1, y1, x2, y2);
		}
		animal.setRestrictedAreaInbound(0, 0, MAX_X, MAX_Y);
		animalList.add(animal);
	}

	/**
	 * Method that adds buildings to the list with buildings and set the buildings
	 * area to a restricted area for all animals.
	 * 
	 * @param building - An building object to be added.
	 */
	public void addBuilding(Building building) {
		building.setRestrictedArea(building.getX(), building.getY(), building.getX() + BUILDINGSIZE,
				building.getY() + BUILDINGSIZE);
		buildingList.add(building);
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).setRestrictedAreaOutBound(building.getX(), building.getY(),
					building.getX() + BUILDINGSIZE, building.getY() + BUILDINGSIZE);
		}
	}
	public void addCrops(Crops crops) {
		crops.setRestrictedArea(crops.getX(), crops.getY(), crops.getX() + CROPSSIZE,
				crops.getY() + CROPSSIZE);
		cropsList.add(crops);
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).setRestrictedAreaOutBound(crops.getX(), crops.getY(),
					crops.getX() + CROPSSIZE, crops.getY() + CROPSSIZE);
		}
	}

	/**
	 * Method that adds a fence to the list with fences and sets everything outside
	 * the fence as restricted area.
	 * 
	 * @param x1 - x coordinate upper left corner.
	 * @param y1 - y coordinate upper left corner
	 * @param x2 - x coordinate lower right corner
	 * @param y2 - y coordinate lower right corner
	 */
	public void addFence(int x1, int y1, int x2, int y2) {
		Fence fence = new Fence(x1, y1, x2, y2);
		fence.setRestrictedArea(x1, y1, x2, y2);
		fenceList.add(fence);
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).setRestrictedAreaInbound(x1, y1, x2, y2);
		}
	}
	/**
	 * Method not used atm. Might be used for identifying buildings
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 */
	
	public void drawNumbersOnBuildings(Graphics2D g) {
		for (int i=0;i<buildingList.size();i++) {
			g.drawString(""+i, buildingList.get(i).getX()+37, buildingList.get(i).getY()-10);
		}
	}

	/**
	 * Method that will draw all fences found in fenceList to the game-board.
	 * 
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 */
	public void drawFence(Graphics2D g) {
		for (int i = 0; i < fenceList.size(); i++) {
			Fence fence = fenceList.get(i);
			g.setStroke(new BasicStroke(2));
			g.drawRect(fence.getX1(), fence.getY1(), fence.getX2() - fence.getX1(), fence.getY2() - fence.getY1());
		}
	}

	/**
	 * Method that will draw all buildings found in the buildingList to the
	 * game-board.
	 * 
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 * 
	 */
	public void drawBuildings(Graphics2D g) {
		Image animation;
		for (int i = 0; i < buildingList.size(); i++) {
			Building building = buildingList.get(i);
			animation = building.getImage().getImage();
			g.drawImage(animation, building.getX(), building.getY(), building.getX() + BUILDINGSIZE,
					building.getY() + BUILDINGSIZE, 0, 0, animation.getWidth(this), animation.getHeight(this), this);
		}
	}
	/**
	 * Method that will draw all animals found in the animalList to the game-board.
	 * 
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 * 
	 */
	public void drawAnimals(Graphics2D g) {
		Image animation;
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			animation = animal.getNextAnimation().getImage();
			g.drawImage(animation, animal.getX(), animal.getY(), animal.getX() + ANIMALSIZE, animal.getY() + ANIMALSIZE,
					0, 0, animation.getWidth(this), animation.getHeight(this), this);
		}
	}
	public void drawCrops(Graphics2D g) {
		Image animation;
		for (int i = 0; i < cropsList.size(); i++) {
			Crops crops = cropsList.get(i);
			animation = crops.getImage().getImage();
			g.drawImage(animation,crops.getX(), crops.getY(), crops.getX() + CROPSSIZE,
					crops.getY() + CROPSSIZE, 0, 0, animation.getWidth(this), animation.getHeight(this), this);
		}
	}
	/**
	 * Method that draws an outer border where the animals can wander around.
	 * 
	 * @param g - Graphics2D where the rectangle will be drawn.
	 */
	public void drawEdges(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, MAX_X, MAX_Y);

	}
	/**
	 * Turns the grid on or off.
	 * 
	 * @param status - boolean
	 */
	public void grid(boolean status) {
		this.grid = status;
	}

	/**
	 * Remove an animal from the list with animals.
	 * 
	 * @param animal - An object to be removed.
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

	/**
	 * Method that draws a grid on the board, but avoids buildings.
	 * 
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 */
	public void drawGrid(Graphics2D g) {
		int gridSize = MAX_X / 20;
		boolean onBuilding = false;
		for (int x = 0; x < MAX_X; x = x + gridSize) {
			ArrayList list = new ArrayList();
			for (int buildingIndex = 0; buildingIndex < buildingList.size(); buildingIndex++) {
				RestrictedArea area = buildingList.get(buildingIndex).getRestrictedArea();
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
			ArrayList list = new ArrayList();
			for (int buildingIndex = 0; buildingIndex < buildingList.size(); buildingIndex++) {
				RestrictedArea area = buildingList.get(buildingIndex).getRestrictedArea();
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
	 * Method that draws the animations in the game-board.
	 * 
	 * @param gg - Graphics that will be converted to Graphics2D and painted at.
	 */
	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		g = (Graphics2D) gg;
		// Used for smoother animations
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHints(rh);
		drawEdges(g);
		drawAnimals(g);
		drawCrops(g);
		drawBuildings(g);
		drawFence(g);
		if (grid) {
			drawGrid(g);
		}
	}

	/**
	 * ActionListener that moves the animals every 40ms (25fps) and repaints the
	 * game-board.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).move();
		}
		repaint();
	}

	/**
	 * Method that isnt implemented yet.
	 * 
	 * @param Building to be removed.
	 */
	public void removeBuilding(Building building) {
		/**
		 * Doesnt work until we get unique buildings.
		 */
//		for (int i=0;i<animalList.size();i++) {
//			animalList.get(i).removeRestrictedAreaOutbound(building.getX(),building.getY(), building.getX()+BUILDINGSIZE, building.getY()+BUILDINGSIZE);
//			
//		}
		
		if (building instanceof Barn) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Barn) {
					for (int index=0;index<animalList.size();index++) {
						animalList.get(index).removeRestrictedAreaOutbound(buildingList.get(i).getX(),buildingList.get(i).getY(), buildingList.get(i).getX()+BUILDINGSIZE, buildingList.get(i).getY()+BUILDINGSIZE);
					}					
					buildingList.remove(i);
					break;
				}
			}
		}
		if (building instanceof Pigsty) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Pigsty) {
					for (int index=0;index<animalList.size();index++) {
						animalList.get(index).removeRestrictedAreaOutbound(buildingList.get(i).getX(),buildingList.get(i).getY(), buildingList.get(i).getX()+BUILDINGSIZE, buildingList.get(i).getY()+BUILDINGSIZE);
					}
					buildingList.remove(i);
					break;
				}
			}
		}
		if (building instanceof HenHouse) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof HenHouse) {
					for (int index=0;index<animalList.size();index++) {
						animalList.get(index).removeRestrictedAreaOutbound(buildingList.get(i).getX(),buildingList.get(i).getY(), buildingList.get(i).getX()+BUILDINGSIZE, buildingList.get(i).getY()+BUILDINGSIZE);
					}
					buildingList.remove(i);
					break;
				}
			}
		}
		if (building instanceof Stable) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Stable) {
					for (int index=0;index<animalList.size();index++) {
						animalList.get(index).removeRestrictedAreaOutbound(buildingList.get(i).getX(),buildingList.get(i).getY(), buildingList.get(i).getX()+BUILDINGSIZE, buildingList.get(i).getY()+BUILDINGSIZE);
					}
					buildingList.remove(i);
					break;
				}
			}
		}
	}
	public void removeCrops(Crops crops) {
		if (crops instanceof Carrot) {
			for (int i = 0; i < cropsList.size(); i++) {
				if (cropsList.get(i) instanceof Carrot) {
					cropsList.remove(i);
					break;
				}
			}
		}
		if (crops instanceof Corn) {
			for (int i = 0; i < cropsList.size(); i++) {
				if (cropsList.get(i) instanceof Corn) {
					cropsList.remove(i);
					break;
				}
			}
		}
		if (crops instanceof Oat) {
			for (int i = 0; i < cropsList.size(); i++) {
				if (cropsList.get(i) instanceof Oat) {
					cropsList.remove(i);
					break;
				}
			}
		}
		if (crops instanceof Lettuce) {
			for (int i = 0; i < cropsList.size(); i++) {
				if (cropsList.get(i) instanceof Lettuce) {
					cropsList.remove(i);
					break;
				}
			}
		}
	}
	public LinkedList<Animal> getAnimalList() {
		return this.animalList;
	}
	public LinkedList<Building> getBuildingList() {
		return this.buildingList;
	}
	public LinkedList<Crops> getCropsList() {
		return this.cropsList;
	}
}