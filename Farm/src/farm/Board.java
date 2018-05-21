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
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.Timer;

import commodity.Animal;
import commodity.Carrot;
import commodity.Chicken;
import commodity.Corn;
import commodity.Cow;
import commodity.Crops;
import commodity.Goods;
import commodity.Lettuce;
import commodity.Oat;
import commodity.Pig;
import commodity.Sheep;
import event.Season;
import main.AnimalCopy;
import property.Barn;
import property.Building;
import property.HenHouse;
import property.Pigsty;
import property.Stable;

/**
 * Class that handles the animations in the mainpanel.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk.
 *
 */
public class Board extends JPanel implements ActionListener {
	private final int MAX_X = 1600;
	private final int MAX_Y = 1600;
	private final int ANIMALSIZE = 40;
	private final int BUILDINGSIZE = 80;
	private final int CROPSSIZE = 40;
	private final int gridSize = 40;
	private Graphics2D g;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private LinkedList<Crops> cropsList = new LinkedList<Crops>();
	private ArrayList<Fence> fenceList = new ArrayList<Fence>();
	private LinkedList<Goods> goodsList = new LinkedList<Goods>();
	private Timer timer;
	private Season season;

	private boolean[][] node = new boolean[MAX_X][MAX_Y];
	private boolean[][] grid = new boolean[MAX_X / gridSize][MAX_Y / gridSize];
	private int gridX = 5;
	private int gridY = 5;
	private boolean drawAreasNotWalkable = false;
	private boolean gridStatus = false;
	private boolean marker = false;
	private boolean placementOK = false;

	private int markerSize = 4;

	public Board() {

		initBoard();
	}

	/**
	 * Method that initializes the board.
	 */
	private void initBoard() {
		alterSeason(Season.SPRING);
		setPreferredSize(new Dimension(MAX_X, MAX_Y));
		setDoubleBuffered(true);

		for (int x = 0; x < MAX_X; x++) {
			for (int y = 0; y < MAX_Y; y++) {
				node[x][y] = true;
			}
		}
		timer = new Timer(10, this);
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

		// int x1;
		// int y1;
		// int x2;
		// int y2;
		int buildingIndex = findIndexOfBuildingWithLeastAnimals(animal.getHouseType());

		if (buildingIndex > -1) { // Kan troligen tas bort
			animal.setX(buildingList.get(buildingIndex).getX() + (int) Math.random() * 40 + 81);
			animal.setY(buildingList.get(buildingIndex).getY() + (int) Math.random() * 40 + 81);
			buildingList.get(buildingIndex).addAnimal();

			animal.setNode(node);
			// for (int i = 0; i < buildingList.size(); i++) {
			// x1 = buildingList.get(i).getX();
			// y1 = buildingList.get(i).getY();
			// }
			// for (int i = 0; i < fenceList.size(); i++) {
			// x1 = fenceList.get(i).getX1();
			// y1 = fenceList.get(i).getY1();
			// x2 = fenceList.get(i).getX2();
			// y2 = fenceList.get(i).getY2();
			// }
			animal.setWalkableArea(0, 0, MAX_X - 1, MAX_Y - 1, false);
			animalList.add(animal);
		}

	}

	/**
	 * Method that adds buildings to the list with buildings and set the buildings
	 * area to a restricted area for all animals.
	 * 
	 * @param building - An building object to be added.
	 */
	public void addBuilding(Building building) {
		building.setNode(node);
		addFence(building.getX(), building.getY(), building.getX() + BUILDINGSIZE * 2,
				building.getY() + BUILDINGSIZE * 2);
		building.setWalkableArea(building.getX() + 1, building.getY() + 1, building.getX() - 1 + BUILDINGSIZE,
				building.getY() - 1 + BUILDINGSIZE, false);
		buildingList.add(building);

	}

	/**
	 * Method that is used for placement of crops on the board.
	 * 
	 * @param crops Crops object
	 */
	public void addCrops(Crops crops) {
		crops.setNode(node);
		crops.setWalkableArea(crops.getX() + 1, crops.getY() + 1, crops.getX() + CROPSSIZE - 1,
				crops.getY() + CROPSSIZE - 1, false);
		cropsList.add(crops);

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
		fence.setNode(node);
		fence.setWalkableArea(x1 + 1, y1 + 1, x2 - 1, y2 - 1, false);
		fenceList.add(fence);
	}
	public void addGoods(Goods goods) {
		goodsList.add(goods);
		for (int i = 0; i < goodsList.size(); i++) {
		}
	}

	/**
	 * Method not used atm. Might be used for identifying buildings
	 * 
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 */
	public void drawNumbersOnBuildings(Graphics2D g) {
		for (int i = 0; i < buildingList.size(); i++) {
			g.drawString("" + i, buildingList.get(i).getX() + 37, buildingList.get(i).getY() - 10);
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

	/**
	 * Method that is used for drawing crops on the board.
	 * 
	 * @param g Graphics2D to draw on.
	 */
	public void drawCrops(Graphics2D g) {
		Image animation;
		for (int i = 0; i < cropsList.size(); i++) {
			Crops crops = cropsList.get(i);
			animation = crops.getImage().getImage();
			g.drawImage(animation, crops.getX(), crops.getY(), crops.getX() + CROPSSIZE, crops.getY() + CROPSSIZE, 0, 0,
					animation.getWidth(this), animation.getHeight(this), this);
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

	public void drawBuildingMarker(Graphics2D g, int gridX, int gridY, int size) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				int xcoord = (gridX + x) * 40;
				int ycoord = (gridY + y) * 40;

				if (node[xcoord + 1][ycoord + 1] && node[xcoord -1 + gridSize][ycoord +1] && node[xcoord+1][ycoord-1+gridSize] && node[xcoord-1+gridSize][ycoord-1+gridSize]) { // TEST TA BORT OM DET PÅVERKAR PÅ NÅGOT SÄTT
					g.setColor(Color.GREEN);
					g.fillRect(xcoord, ycoord, gridSize, gridSize);
				} else {
					g.setColor(Color.RED);
					g.fillRect(xcoord, ycoord, gridSize, gridSize);
				}
			}
		}
	}

	public void drawGrid(Graphics2D g) {

		for (int x = 0; x < MAX_X; x = x + gridSize) {
			g.drawLine(x, 0, x, MAX_Y);
		}
		for (int y = 0; y < MAX_Y; y = y + gridSize) {
			g.drawLine(0, y, MAX_X, y);
		}
	}

	/**
	 * Turns the grid on or off.
	 * 
	 * @param status - boolean
	 */
	public void grid(boolean status) {
		this.gridStatus = status;
	}

	/**
	 * Remove an animal from the list with animals.
	 * 
	 * @param animal - An object to be removed.
	 */
	public void removeAnimal(Animal animal) {
		Class<?> wantedAnimal = animal.getClass();
		Class<?> foundAnimal;
		for (int i = 0; i < animalList.size(); i++) {
			foundAnimal = animalList.get(i).getClass();
			if (foundAnimal.equals(wantedAnimal)) {
				int buildingIndex = findIndexOfBuildingWithMostAnimals(animal.getHouseType());
				buildingList.get(buildingIndex).removeAnimal();
				animalList.remove(i);
				break;
			}
		}
	}
	public void removeGoods(Goods goods) {
		Class<?> wantedgood = goods.getClass();
		Class<?> foundgood;
		for (int i = 0; i < goodsList.size(); i++) {
			foundgood = cropsList.get(i).getClass();
			if (foundgood.equals(wantedgood)) {
				goodsList.remove(i);
				break;
			}
		}
	}


	public int countAnimalsByType(Animal animal) {
		int counter = 0;
		Class<?> wantedAnimal = animal.getClass();
		Class<?> foundAnimal;
		for (int i = 0; i < animalList.size(); i++) {
			foundAnimal = animalList.get(i).getClass();
			if (foundAnimal.equals(wantedAnimal)) {
				counter++;
			}
		}
		return counter;
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
		if (gridStatus) {
			drawGrid(g);
		}
		drawAnimals(g);
		drawCrops(g);
		drawBuildings(g);
		drawFence(g);

		// drawNumbersOnBuildings(g);

		if (marker) {
			drawBuildingMarker(g, gridX, gridY, markerSize);
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
		// KAN EVEVENTUELLT SKAPA PROBLEM (+1 på node[x][y] kan behövas för att inte
		// missa restriktion. *TESTA*
		for (int x = 0; x < MAX_X; x = x + gridSize) {
			for (int y = 0; y < MAX_Y; y = y + gridSize) {
				if (node[x][y] == false) {
					grid[x / gridSize][y / gridSize] = false;
				}
			}
		}
	}

	/**
	 * Method that isnt implemented yet.
	 * 
	 * @param Building to be removed.
	 */
	public void removeBuilding(Building building) {
		Class<?> wantedBuilding = building.getClass();
		Class<?> foundBuilding;
		for (int i = 0; i < buildingList.size(); i++) {
			foundBuilding = buildingList.get(i).getClass();
			if (foundBuilding.equals(wantedBuilding)) {
				Building b = buildingList.get(i);
				b.setWalkableArea(b.getX() + 1, b.getY() + 1, b.getX() + BUILDINGSIZE - 1, b.getY() + BUILDINGSIZE - 1,
						true);
				buildingList.remove(i);
				fenceList.get(i).setWalkableArea(b.getX() + 1, b.getY() + 1, b.getX() - 1 + BUILDINGSIZE * 2,
						b.getY() - 1 + BUILDINGSIZE * 2, true);
				fenceList.remove(i);
				break;
			}
		}
	}

	/**
	 * Method used for removing crops. Using java reflection.
	 * 
	 * @param crops Crops object
	 */
	public void removeCrops(Crops crops) {
		Class<?> wantedCrop = crops.getClass();
		Class<?> foundCrop;
		for (int i = 0; i < cropsList.size(); i++) {
			foundCrop = cropsList.get(i).getClass();
			if (foundCrop.equals(wantedCrop)) {
				cropsList.get(i).setWalkableArea(cropsList.get(i).getX() + 1, cropsList.get(i).getY() + 1,
						cropsList.get(i).getX() + BUILDINGSIZE - 1, cropsList.get(i).getY() + BUILDINGSIZE - 1, true);
				cropsList.remove(i);
				break;
			}
		}
	}

	/**
	 * Method that find the building of a specific type with least amount of
	 * population
	 * 
	 * @param buildingStr Building type in a String
	 * @return Integer, index of the building in BuildingList.
	 */
	public int findIndexOfBuildingWithLeastAnimals(String buildingStr) {
		Building building;
		try {
			building = (Building) Class.forName("property." + buildingStr).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			building = null;
		}
		int leastPopIndex = -1;
		int minValue = Integer.MAX_VALUE;
		if (building instanceof Barn) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Barn) {
					minValue = buildingList.get(i).getNumberOfAnimals();
					leastPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Barn) {
					if (minValue > buildingList.get(i).getNumberOfAnimals()) {
						minValue = buildingList.get(i).getNumberOfAnimals();
						leastPopIndex = i;
					}
				}
			}
		} else if (building instanceof Pigsty) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Pigsty) {
					minValue = buildingList.get(i).getNumberOfAnimals();
					leastPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Pigsty) {
					if (minValue > buildingList.get(i).getNumberOfAnimals()) {
						minValue = buildingList.get(i).getNumberOfAnimals();
						leastPopIndex = i;
					}
				}
			}
		} else if (building instanceof HenHouse) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof HenHouse) {
					minValue = buildingList.get(i).getNumberOfAnimals();
					leastPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof HenHouse) {
					if (minValue > buildingList.get(i).getNumberOfAnimals()) {
						minValue = buildingList.get(i).getNumberOfAnimals();
						leastPopIndex = i;
					}
				}
			}
		} else if (building instanceof Stable) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Stable) {
					minValue = buildingList.get(i).getNumberOfAnimals();
					leastPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Stable) {
					if (minValue > buildingList.get(i).getNumberOfAnimals()) {
						minValue = buildingList.get(i).getNumberOfAnimals();
						leastPopIndex = i;
					}
				}
			}
		}
		return leastPopIndex;
	}

	/**
	 * Method that find the building of a specific type with most amount of
	 * population
	 * 
	 * @param buildingStr Building type in a String
	 * @return Integer, index of the building in BuildingList.
	 */
	public int findIndexOfBuildingWithMostAnimals(String buildingStr) {
		Building building;
		try {
			building = (Building) Class.forName("property." + buildingStr).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			building = null;
		}
		int mostPopIndex = -1;
		int maxValue = Integer.MIN_VALUE;
		if (building instanceof Barn) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Barn) {
					maxValue = buildingList.get(i).getNumberOfAnimals();
					mostPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Barn) {
					if (maxValue < buildingList.get(i).getNumberOfAnimals()) {
						maxValue = buildingList.get(i).getNumberOfAnimals();
						mostPopIndex = i;
					}
				}
			}
		} else if (building instanceof Pigsty) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Pigsty) {
					maxValue = buildingList.get(i).getNumberOfAnimals();
					mostPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Pigsty) {
					if (maxValue < buildingList.get(i).getNumberOfAnimals()) {
						maxValue = buildingList.get(i).getNumberOfAnimals();
						mostPopIndex = i;
					}
				}
			}
		} else if (building instanceof HenHouse) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof HenHouse) {
					maxValue = buildingList.get(i).getNumberOfAnimals();
					mostPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof HenHouse) {
					if (maxValue < buildingList.get(i).getNumberOfAnimals()) {
						maxValue = buildingList.get(i).getNumberOfAnimals();
						mostPopIndex = i;
					}
				}
			}
		} else if (building instanceof Stable) {
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Stable) {
					maxValue = buildingList.get(i).getNumberOfAnimals();
					mostPopIndex = i;
				}
			}
			for (int i = 0; i < buildingList.size(); i++) {
				if (buildingList.get(i) instanceof Stable) {
					if (maxValue < buildingList.get(i).getNumberOfAnimals()) {
						maxValue = buildingList.get(i).getNumberOfAnimals();
						mostPopIndex = i;
					}
				}
			}
		}
		return mostPopIndex;
	}

	/**
	 * Return the animalList
	 * 
	 * @return LinkedList<Animal>
	 */
	public LinkedList<Animal> getAnimalList() {
		return this.animalList;
	}

	/**
	 * Return the buildingList
	 * 
	 * @return LinkedList<Building>
	 */

	public LinkedList<Building> getBuildingList() {
		return this.buildingList;
	}
	/**
	 * return the goodsList
	 */
	public LinkedList<Goods> getGoodsList() {
		return this.goodsList;
	}

	/**
	 * Return the cropList
	 * 
	 * @return LinkedList<Crops>
	 */

	public LinkedList<Crops> getCropsList() {
		return this.cropsList;
	}

	/**
	 * Method that sets the node for the board-area
	 * 
	 * @param node 1600x1600 boolean array.
	 */
	public void setNode(boolean[][] node) {
		this.node = node;
	}

	/**
	 * Method that returns the node for the board-area
	 * 
	 * @return node 1600x1600 boolean array
	 */
	public boolean[][] getNode() {
		return node;
	}

	public boolean checkGrid(int gridX, int gridY, int size) {

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				int xCoord = (gridX + x) * gridSize;
				int yCoord = (gridY + y) * gridSize;

				// Skapar problem då man inte kan placera två saker brevid varandra (över eller
				// vänster om tidigare crop/byggnad) FÖRMODLIGEN ÅTGÄRDAT. TESTA MER.
				if ((node[xCoord + 1][yCoord + 1] == false) || (node[xCoord + gridSize - 1][yCoord + 1] == false)
						|| (node[xCoord + 1][yCoord + gridSize - 1] == false)
						|| (node[xCoord + gridSize - 1][yCoord + gridSize - 1] == false) || (xCoord < 0)
						|| (xCoord >= 1600) || (yCoord < 0) || (yCoord >= 1600)) {
					return false;
				}
			}
		}
		return true;
	}

	public void moveMarker(int xMove, int yMove) {
		int newX = (gridX + xMove) * gridSize;
		int newY = (gridY + yMove) * gridSize;
		if (newX >= 0 && newX < 1600 - 160 && newY >= 0 && newY < 1600 - 160) {
			gridX = gridX + xMove;
			gridY = gridY + yMove;
		}

	}

	public void setMarker(boolean status) {
		marker = status;
	}
	public void setMarkerSize(int size) {
		this.markerSize  = size;
	}
	public int[] accept(int size) {
		int[] coord = new int[2];

		if (checkGrid(gridX, gridY, size)) {
			coord[0] = gridX * gridSize;
			coord[1] = gridY * gridSize;
		} else {
			coord[0] = -1;
			coord[1] = -1;
		}
		marker = false;
		return coord;
	}
}