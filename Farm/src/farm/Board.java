package farm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;
import commodity.Animal;
import commodity.Crops;
import commodity.Goods;
import event.Season;
import property.Barn;
import property.Building;
import property.Fence;
import property.HenHouse;
import property.Pigsty;
import property.Stable;

/**
 * Class that handles the animations in the mainpanel.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt.
 *
 */
public class Board extends JPanel implements ActionListener {
	private final int MAX_X = 1600;
	private final int MAX_Y = 1600;
	private final int ANIMALSIZE = 40;
	private final int BUILDINGSIZE = 80;
	private final int CROPSSIZE = 40;
	private final int GRIDSIZE = 40;
	private Graphics2D g;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private LinkedList<Crops> cropsList = new LinkedList<Crops>();
	private ArrayList<Fence> fenceList = new ArrayList<Fence>();
	private LinkedList<Goods> goodsList = new LinkedList<Goods>();
	private Timer timer;

	private boolean[][] node = new boolean[MAX_X][MAX_Y];
	private boolean[][] grid = new boolean[MAX_X / GRIDSIZE][MAX_Y / GRIDSIZE];
	private int gridX = 5;
	private int gridY = 5;
	private boolean gridStatus = false;
	private boolean marker = false;
	private int markerSize = 4;

	/**
	 * Constructor that sets the season, sets the board size, initialize the node
	 * array at a pixel level and start a timer updating the screen at 25fps.
	 */
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
		timer = new Timer(40, this);
		timer.start();
	}

	/**
	 * Method that sets the season-color for the board.
	 * 
	 * @param color Color with the seasons color
	 */
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
		int buildingIndex = findIndexOfBuildingWithLeastAnimals(animal.getHouseType());
		if (buildingIndex > -1) {
			animal.setX(buildingList.get(buildingIndex).getX() + (int) Math.random() * 40 + 81);
			animal.setY(buildingList.get(buildingIndex).getY() + (int) Math.random() * 40 + 81);
			buildingList.get(buildingIndex).addAnimal();
			animal.setNode(node);
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
	 * Method that adds a fence to the list with fences and sets the fences
	 * coordinates as not walkable.
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

	/**
	 * Method that adds goods to the list with goods and set the goods.
	 * 
	 * @param goods - An goods object to be added.
	 */
	public void addGoods(Goods goods) {
		goodsList.add(goods);
		for (int i = 0; i < goodsList.size(); i++) {
		}
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

	/**
	 * Remove a goods from the list with goods.
	 * 
	 * @param goods - An object to be removed.
	 */
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

	/**
	 * Method that counts animals by Animal-type.
	 * 
	 * @param animal Animal object
	 * @return int with number of animals found.
	 */

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
	 * Method used for removing buildings and the fence around them. Will also reset
	 * the node around the buildings
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
						cropsList.get(i).getX() + CROPSSIZE - 1, cropsList.get(i).getY() + CROPSSIZE - 1, true);
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

	/**
	 * Method that checks a grid (40x40 pixels) and see if its restricted for
	 * building buildings.
	 * 
	 * @param gridX Gridposition on x.
	 * @param gridY Gridposition on y
	 * @param size size on number of grids that will be check at one time.
	 * @return boolean true if grid isnt restricted
	 */
	public boolean checkGrid(int gridX, int gridY, int size) {

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				int xCoord = (gridX + x) * GRIDSIZE;
				int yCoord = (gridY + y) * GRIDSIZE;
				if ((node[xCoord + 1][yCoord + 1] == false) || (node[xCoord + GRIDSIZE - 1][yCoord + 1] == false)
						|| (node[xCoord + 1][yCoord + GRIDSIZE - 1] == false)
						|| (node[xCoord + GRIDSIZE - 1][yCoord + GRIDSIZE - 1] == false) || (xCoord < 0)
						|| (xCoord >= 1600) || (yCoord < 0) || (yCoord >= 1600)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Method used for moving the building/crops-marker on the board.
	 * 
	 * @param xMove x-direction of the movement.
	 * @param yMove y-direction of the movement.
	 */
	public void moveMarker(int xMove, int yMove) {
		int newX = (gridX + xMove) * GRIDSIZE;
		int newY = (gridY + yMove) * GRIDSIZE;
		if (newX >= 0 && newX < 1600 - 160 && newY >= 0 && newY < 1600 - 160) {
			gridX = gridX + xMove;
			gridY = gridY + yMove;
		}
	}

	/**
	 * Method used for turning building/crops-marker on or off.
	 * 
	 * @param status boolean true - on false - off.
	 */
	public void setMarker(boolean status) {
		marker = status;
	}

	/**
	 * Method used for setting the building/crop-marker size.
	 * 
	 * @param size size of the marker
	 */
	public void setMarkerSize(int size) {
		this.markerSize = size;
	}

	/**
	 * Method used for accepting a position for building a building or a crop. If
	 * coordinates is okay it will return coordinates for the building/crop to be
	 * build at. Otherwise it will return (-1,-1) as coordinates.
	 * 
	 * @param size size of the area that we want to build at.
	 * @return int array with x and y coordinates.
	 */
	public int[] accept(int size) {
		int[] coord = new int[2];
		if (checkGrid(gridX, gridY, size)) {
			coord[0] = gridX * GRIDSIZE;
			coord[1] = gridY * GRIDSIZE;
		} else {
			coord[0] = -1;
			coord[1] = -1;
		}
		marker = false;
		return coord;
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
		if (marker) {
			drawBuildingMarker(g, gridX, gridY, markerSize);
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

	/**
	 * Method used for drawing a Building/crops-marker on the board-area used for
	 * placing buildings/crops.
	 * 
	 * @param g Graphics2D component
	 * @param gridX Gridposition on x axis.
	 * @param gridY Gridposition on y axis.
	 * @param size Size of the building marker.
	 */
	public void drawBuildingMarker(Graphics2D g, int gridX, int gridY, int size) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				int xcoord = (gridX + x) * 40;
				int ycoord = (gridY + y) * 40;
				if (node[xcoord + 1][ycoord + 1] && node[xcoord - 1 + GRIDSIZE][ycoord + 1]
						&& node[xcoord + 1][ycoord - 1 + GRIDSIZE]
						&& node[xcoord - 1 + GRIDSIZE][ycoord - 1 + GRIDSIZE]) {
					g.setColor(Color.GREEN);
					g.fillRect(xcoord, ycoord, GRIDSIZE, GRIDSIZE);
				} else {
					g.setColor(Color.RED);
					g.fillRect(xcoord, ycoord, GRIDSIZE, GRIDSIZE);
				}
			}
		}
	}
	/**
	 * Method used for drawing a grid on the board-area. Used when placing buildings.
	 * @param g Graphics2D component
	 */
	public void drawGrid(Graphics2D g) {

		for (int x = 0; x < MAX_X; x = x + GRIDSIZE) {
			g.drawLine(x, 0, x, MAX_Y);
		}
		for (int y = 0; y < MAX_Y; y = y + GRIDSIZE) {
			g.drawLine(0, y, MAX_X, y);
		}
	}

	/**
	 * Testmethod. Used for highlighting restrictions on the board-area.
	 * All restricted pixels will turn red.
	 */
	public void drawRedOnRestrictions(Graphics2D g) {
		for (int x = 0; x < MAX_X; x++) {
			for (int y = 0; y < MAX_Y; y++) {
				if (node[x][y] == false) {
					g.setColor(Color.RED);
					g.drawOval(x, y, 1, 1);
				}
			}
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
	 * ActionListener that moves the animals every 40ms (25fps) and repaints the
	 * game-board. Also transfers restrictions from the pixel-node (1600x1600) to the grid-node (40x40)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).move();
		}
		repaint();
		for (int x = 0; x < MAX_X; x = x + GRIDSIZE) {
			for (int y = 0; y < MAX_Y; y = y + GRIDSIZE) {
				if (node[x][y] == false) {
					grid[x / GRIDSIZE][y / GRIDSIZE] = false;
				}
			}
		}
	}

}