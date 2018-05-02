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
	private final int MAX_X = 1600; // Original value 665
	private final int MAX_Y = 1600; // OriginalValue 535
	private final int ANIMALSIZE = 40;
	private final int BUILDINGSIZE = 80;
	private final int CROPSSIZE = 40;
	private boolean grid = false;
	private Graphics2D g;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private LinkedList<Crops> cropsList = new LinkedList<Crops>();
	private ArrayList<Fence> fenceList = new ArrayList<Fence>();
	private Timer timer;
	private Season season;

	// Test for gridbased restrictionsystem
	// private Node [][] node = new Node [MAX_X][MAX_Y];
	private Boolean[][] node = new Boolean[MAX_X][MAX_Y];

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

		// addFence(100, 100, 300, 300); // #1
		// addFence(400, 100, 600, 300); // #2
		// addFence(100, 400, 300, 600); // #3
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
		int buildingIndex = findIndexOfBuildingWithLeastAnimals(animal.getHouseType());

		if (buildingIndex > -1) {	//Kan troligen tas bort
			animal.setX(buildingList.get(buildingIndex).getX() + (int) Math.random() * 40 + 81);
			animal.setY(buildingList.get(buildingIndex).getY() + (int) Math.random() * 40 + 81);
			buildingList.get(buildingIndex).addAnimal();

			animal.setNode(node);
			for (int i = 0; i < buildingList.size(); i++) {
				x1 = buildingList.get(i).getX();
				y1 = buildingList.get(i).getY();
			}
			for (int i = 0; i < fenceList.size(); i++) {
				x1 = fenceList.get(i).getX1();
				y1 = fenceList.get(i).getY1();
				x2 = fenceList.get(i).getX2();
				y2 = fenceList.get(i).getY2();
			}
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
		building.setWalkableArea(building.getX(), building.getY(), building.getX() + BUILDINGSIZE,
				building.getY() + BUILDINGSIZE, false);
		buildingList.add(building);
		for (int i = 0; i < animalList.size(); i++) {
		}
	}

	public void addCrops(Crops crops) {
		crops.setNode(node);
		crops.setWalkableArea(crops.getX(), crops.getY(), crops.getX() + CROPSSIZE, crops.getY() + CROPSSIZE, false);
		cropsList.add(crops);
		for (int i = 0; i < animalList.size(); i++) {
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
		fence.setNode(node);
		fence.setWalkableArea(x1, y1, x2, y2, false);
		fenceList.add(fence);
		for (int i = 0; i < animalList.size(); i++) {
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
	 * Method that draws a grid on the board, but avoids buildings.
	 * 
	 * @param g Graphics2D object from the JPanel where everything will be drawn.
	 */
	// public void drawGrid(Graphics2D g) {
	// int gridSize = MAX_X / 20;
	// boolean onBuilding = false;
	// for (int x = 0; x < MAX_X; x = x + gridSize) {
	// ArrayList list = new ArrayList();
	// for (int buildingIndex = 0; buildingIndex < buildingList.size();
	// buildingIndex++) {
	// RestrictedArea area = buildingList.get(buildingIndex).getRestrictedArea();
	// for (int y = 0; y < MAX_Y; y++) {
	// if (!area.check(x, y) && !onBuilding) {
	// list.add(y);
	// onBuilding = true;
	// } else if (area.check(x, y) && onBuilding) {
	// list.add(y);
	// onBuilding = false;
	// }
	// }
	// }
	// if (!list.isEmpty()) {
	// g.drawLine(x, 0, x, (int) list.get(0));
	// for (int i = 1; i < list.size() - 2; i++) {
	// g.drawLine(x, (int) list.get(i), x, (int) list.get(i + 1));
	// }
	// g.drawLine(x, (int) list.get(list.size() - 1), x, MAX_Y);
	// } else {
	// g.drawLine(x, 0, x, MAX_Y);
	// }
	// }
	// for (int y = 0; y < MAX_Y; y = y + gridSize) {
	// ArrayList list = new ArrayList();
	// for (int buildingIndex = 0; buildingIndex < buildingList.size();
	// buildingIndex++) {
	// RestrictedArea area = buildingList.get(buildingIndex).getRestrictedArea();
	// for (int x = 0; x < MAX_X; x++) {
	// if (!area.check(x, y) && !onBuilding) {
	// list.add(x);
	// onBuilding = true;
	// } else if (area.check(x, y) && onBuilding) {
	// list.add(x);
	// onBuilding = false;
	// }
	// }
	// }
	// if (!list.isEmpty()) {
	// g.drawLine(0, y, (int) list.get(0), y);
	// for (int i = 1; i < list.size() - 2; i++) {
	// g.drawLine((int) list.get(i), y, (int) list.get(i + 1), y);
	// }
	// g.drawLine((int) list.get(list.size() - 1), y, MAX_X, y);
	// } else {
	// g.drawLine(0, y, MAX_X, y);
	// }
	// }
	// }
	//
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
		// if (grid) {
		// drawGrid(g);
		// }
		drawNumbersOnBuildings(g);
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
		Class<?> wantedBuilding = building.getClass();
		Class<?> foundBuilding;
		for (int i = 0; i < buildingList.size(); i++) {
			foundBuilding = buildingList.get(i).getClass();
			if (foundBuilding.equals(wantedBuilding)) {
				Building b = buildingList.get(i);
				b.setWalkableArea(b.getX(), b.getY(),b.getX()+BUILDINGSIZE,b.getY()+BUILDINGSIZE, true);
				buildingList.remove(i);
				fenceList.get(i).setWalkableArea(b.getX(), b.getY(), b.getX() + BUILDINGSIZE*2,
						b.getY() + BUILDINGSIZE*2, true);
				fenceList.remove(i);
				break;
			}
		}
	}

	public void removeCrops(Crops crops) {
		Class<?> wantedCrop = crops.getClass();
		Class<?> foundCrop;
		for (int i = 0; i < cropsList.size(); i++) {
			foundCrop = cropsList.get(i).getClass();
			if (foundCrop.equals(wantedCrop)) {
				cropsList.remove(i);
				break;
			}
		}
	}

	public int findIndexOfBuildingWithLeastAnimals(String buildingStr) {
		Building building;
		try {
			building = (Building) Class.forName("property."+buildingStr).newInstance();
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
	
	public int findIndexOfBuildingWithMostAnimals(String buildingStr) {
		Building building;
		try {
			building = (Building) Class.forName("property."+buildingStr).newInstance();
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
	
	
	
	

	public LinkedList<Animal> getAnimalList() {
		return this.animalList;
	}

	public LinkedList<Building> getBuildingList() {
		return this.buildingList;
	}

	public LinkedList<Crops> getCropsList() {
		return this.cropsList;
	}

	public void setNode(Boolean[][] node) {
		this.node = node;
	}

	public Boolean[][] getNode() {
		return node;
	}
}