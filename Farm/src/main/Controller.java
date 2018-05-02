package main;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
import event.EffectFileReader;
import event.EventFileReader;
import event.EventHandler;
import event.Season;
import farm.Board;
//import farm.Node;
import property.Barn;
import property.Building;
import property.HenHouse;
import property.Pigsty;
import property.Stable;
import ui.UIMain;
/**
 * Controller for the application. Connects the different classes with UIMain.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk
 */
public class Controller extends Observable {
	private EventHandler handler;
	private UIMain main;
	private Board board;
	private LoadGame load;
	private Season season;
	
	private int nmbrOfCows = 0;
	private int nmbrOfPigs = 0;
	private int nmbrOfSheeps = 0;
	private int nmbrOfChickens = 0;
	private int nmbrOfBarns = 0;
	private int nmbrOfPigSty = 0;
	private int nmbrOfHenHouse = 0;
	private int nmbrOfStable = 0;
	private int nmbrOfCarrots = 0;
	private int nmbrOfCorn = 0;
	private int nmbrOfOats = 0;
	private int nmbrOfLettuce = 0;
	private int week = 1;
	private int year = 1;
	private int cash = 150000;
	private int x;
	private int y;

	private Random rand = new Random();
	
	private String farmName;

	public Controller() {
		new EventFileReader("files/testevent.txt");
		handler = EventHandler.getInstance();
		handler.setEffectMap(new EffectFileReader("files/testeffect.txt").getEffectMap());
		handler.instantiateEffects(this);
		season = new Season(board, main);

	}

	/**
	 * Starts up an new game
	 */
	public void newGame(boolean isFirst) {
		if (!isFirst) {
			main.dispose();
			main = null;
			board = null;
		}
		setName();
		board = new Board();
		main = new UIMain(this, board);
		week = getWeek();
		cash = getCash();
		setCommodityStart();
		setPropertyStart();
		setCropsStart();
	}
	/**
	 * Sets up an loaded game
	 */
	public void loadedGame(boolean isFirst) {
		LoadGame load;
		try {
			load = new LoadGame();
		} catch (IOException e) {
			if (isFirst) {
				setChanged();
				notifyObservers(true);
			}
			JOptionPane.showMessageDialog(null, "You have no saved games");
			return;
		}
		if (!isFirst) {
			main.dispose();
			main = null;
			board = null;
		}
		board = new Board();
		loadGame(load);
		main = new UIMain(this, board);
		setCommodityLoaded();
		setPropertyLoaded();
		setCropsLoaded();
		board.alterSeason(season.getSeason(getWeek()));
//		season = new Season(board, main);
//		season.setSeason(week);
	}
	public void quit() {
		if (JOptionPane.showConfirmDialog(null, "Abandon farm??", "MainMenu",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			setChanged();
			notifyObservers(true);
			main.dispose();
		}
	}

	/**
	 * Handles the exit phase.
	 */
	public void exit() {
		if (JOptionPane.showConfirmDialog(null, "Abandon farm??", "ExitGame",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			if (JOptionPane.showConfirmDialog(null, "Save game?", "ExitGame",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				saveGame();
			}
			System.exit(0);
			
		}

	}

	/**
	 * Handles the end of turn phase. Randomizes the sell/buy price
	 */
	public void endTurn() {
		main.editCommodity("Cow", 500 + (rand.nextInt(100) - 50), -1);
		main.editCommodity("Pig", 300 + (rand.nextInt(50) - 25), -1);
		main.editCommodity("Sheep", 200 + (rand.nextInt(50) - 25), -1);
		main.editCommodity("Chicken", 50 + (rand.nextInt(20) - 10), -1);
	}
	public void requestCheck() {
		main.lblCheck();
		main.marketCheck();
		main.propertyCheck();
	}
	/**
	 * Adds Commodities and sets their prices. Should be called when application
	 * starts
	 */
	public void setCommodityStart() {
		main.addCommodity("Cow", 500, 0, new ImageIcon("images/icons/cowIcon.png"));			// Changed for demo
		main.addCommodity("Pig", 300, 0, new ImageIcon("images/icons/pigIcon.png"));			// Changed for demo
		main.addCommodity("Sheep", 200, 0, new ImageIcon("images/icons/sheepIcon.png"));		// Changed for demo
		main.addCommodity("Chicken", 50, 0, new ImageIcon("images/icons/chickenIcon.png"));

	}

	/**
	 * Adds Properies and sets their prices. Should be called when application
	 * starts
	 */

	public void setPropertyStart() {
		main.addProperty("Barn", 1000, 0, new ImageIcon("images/icons/barnIcon.png"));			// changed for demo
		main.addProperty("Pigsty", 1200, 0, new ImageIcon("images/icons/pigstyIcon.png"));
		main.addProperty("Stable", 1000, 0, new ImageIcon("images/icons/stableIcon.png"));
		main.addProperty("Hen house", 700, 0, new ImageIcon("images/icons/henhouseIcon.png"));
	}

	public void setCropsStart() {
		main.addCrops("Carrot", 200, 0, new ImageIcon("images/icons/carrotIcon.png"));
		main.addCrops("Corn", 300, 0, new ImageIcon("images/icons/cornIcon.png"));
		main.addCrops("Oats", 100, 0, new ImageIcon("images/icons/oatIcon.png"));
		main.addCrops("Lettuce", 400, 0, new ImageIcon("images/icons/lettuceIcon.png"));
	}

	/**
	 * Adds the selected animal to the board
	 * 
	 * @param name
	 *            name of animal that will be added
	 */
	public void buyCommodity(String name) {
		if (name.equals("Cow")) {
			board.addAnimal(new Cow());
		}
		if (name.equals("Pig")) {
			board.addAnimal(new Pig());
		}
		if (name.equals("Chicken")) {
			board.addAnimal(new Chicken());
		}
		if (name.equals("Sheep")) {
			board.addAnimal(new Sheep());
		}
	}

	/**
	 * Sells the selected animal and removes it from the board
	 * 
	 * @param name
	 *            name of animal that will be sold
	 */
	public void sellCommodity(String name) {
		if (name.equals("Cow")) {
			board.removeAnimal(new Cow());
		}
		if (name.equals("Pig")) {
			board.removeAnimal(new Pig());
		}
		if (name.equals("Chicken")) {
			board.removeAnimal(new Chicken());
		}
		if (name.equals("Sheep")) {
			board.removeAnimal(new Sheep());
		}
	}

	/**
	 * Adds selected building to board through x and y coordinate provided by player
	 * 
	 * @param name,
	 *            x, y
	 */

	public void buyProperty(String name, int x, int y) {

		if (name.equals("Barn")) {
			board.addBuilding(new Barn(x, y));
			Barn.gained();
		}

		if (name.equals("Pigsty")) {
			board.addBuilding(new Pigsty(x, y));
			Pigsty.gained();
		}

		if (name.equals("Hen house")) {
			board.addBuilding(new HenHouse(x, y));
			HenHouse.gained();
		}

		if (name.equals("Stable")) {
			board.addBuilding(new Stable(x, y));
			Stable.gained();
		}
	}

	/**
	 * Sells the selected building and removes it from the board
	 * 
	 * @param name
	 *            name of builing that will be sold
	 */

	/**
	 * Sells the selected building and removes it from the board
	 * 
	 * @param name
	 *            name of builing that will be sold
	 */

	public void sellProperty(String name) {
		if (name.equals("Barn")) {
			board.removeBuilding(new Barn());
			Barn.lost();
		}
		if (name.equals("Pigsty")) {
			board.removeBuilding(new Pigsty());
			Pigsty.lost();
		}
		if (name.equals("Hen house")) {
			board.removeBuilding(new HenHouse());
			HenHouse.lost();
		}
		if (name.equals("Stable")) {
			board.removeBuilding(new Stable());
			Stable.lost();
		}
	}

	public void buyCrops(String name, int x, int y) {

		if (name.equals("Carrot")) {
			board.addCrops(new Carrot(x, y));
		}

		if (name.equals("Corn")) {
			board.addCrops(new Corn(x, y));
		}

		if (name.equals("Oats")) {
			board.addCrops(new Oat(x, y));
		}

		if (name.equals("Lettuce")) {
			board.addCrops(new Lettuce(x, y));
		}
	}

	public void sellCrops(String name) {
		if (name.equals("Carrot")) {
			board.removeCrops(new Carrot());
		}
		if (name.equals("Corn")) {
			board.removeCrops(new Corn());
		}
		if (name.equals("Oats")) {
			board.removeCrops(new Oat());
		}
		if (name == "Lettuce") {
			board.removeCrops(new Lettuce());
		}
	}
	/**
	 * Returns the current year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * When called increases the year by one
	 */
	public void setYear() {
		year++;
		week = 1;
	}

	/**
	 * Returns the current day
	 * 
	 * @return day
	 */
	public int getWeek() {
		return week;
	}

	/**
	 * When called increases the day by one. If 52 weeks have gone by year is increased
	 */
	public void setWeek() {
		this.week++;
//		if (this.week == 53) {
//			setYear();
//			board.alterSeason(Board.SPRING);
//		}
//		if (this.week == 14) {
//			board.alterSeason(Board.SUMMER);
//		}
//		if (this.week == 27) {
//			board.alterSeason(Board.AUTUMN);
//		}
//		if (this.week == 40) {
//			board.alterSeason(Board.WINTER);
//		}
	}

	/**
	 * Returns how much cash the player has
	 * 
	 * @return cash
	 */
	public int getCash() {
		return cash;
	}

	public void setCash(int amount) {
		cash = cash + amount;
	}
	
	public void setName() {
		farmName = JOptionPane.showInputDialog("Name your farm");
	}
	
	public String getName() {
		return farmName;
	}
	//____________________________Methods for saving and loading the game____________________________________________
	/**
	 * Collects all data into an LinkedList which it sends to SaveGame
	 */
	public void saveGame() {
		LinkedList saveList = new LinkedList();
		saveList.add(getYear());
		saveList.add(getWeek());
		saveList.add(getCash());
		saveList.add(getName());
		saveList.add(makeCopyOfAnimalList());
//		saveList.add(board.getAnimalList());
		saveList.add(board.getNode());
		saveList.add(board.getBuildingList());
		saveList.add(board.getCropsList());
		new SaveGame(saveList);
	}
	/**
	 * Separates the loaded list into day, cash, animals, buildings and crops and adds them to board
	 */
	public void loadGame(LoadGame load) {
		LinkedList loadedList = new LinkedList();
		LinkedList loadedAnimals = new LinkedList();
		LinkedList loadedBuildings = new LinkedList();
		LinkedList loadedCrops = new LinkedList();
		loadedList = load.load();
		this.year = (int) loadedList.pop();
		this.week = (int) loadedList.pop();
		this.cash = (int) loadedList.pop();
		this.farmName = (String) loadedList.pop();
		loadedAnimals = (LinkedList) loadedList.pop();
		board.setNode((Boolean[][])loadedList.pop());
		loadedBuildings = (LinkedList) loadedList.pop();
		loadedCrops = (LinkedList) loadedList.pop();
		//Convert Animalcopy-list to ordinary AnimalList.
		loadedAnimals = restoreAnimalList(loadedAnimals);
		for (int i = 0; i < loadedBuildings.size(); i++) {
				board.addBuilding((Building)loadedBuildings.get(i));
				if (loadedBuildings.get(i) instanceof Barn) {
					nmbrOfBarns++;
				}
				if (loadedBuildings.get(i) instanceof Pigsty) {
					nmbrOfPigSty++;
				}
				if (loadedBuildings.get(i) instanceof HenHouse) {
					nmbrOfHenHouse++;
				}
				if (loadedBuildings.get(i) instanceof Stable) {
					nmbrOfStable++;
				}
		}
		for (int i = 0; i < loadedAnimals.size(); i++) {
			board.addAnimal((Animal) loadedAnimals.get(i));
			
			
			if(loadedAnimals.get(i) instanceof Cow) {
				nmbrOfCows ++;
			}
			if(loadedAnimals.get(i) instanceof Pig) {
				nmbrOfPigs ++;
			}
			if(loadedAnimals.get(i) instanceof Sheep) {
				nmbrOfSheeps ++;
			}
			if(loadedAnimals.get(i) instanceof Chicken) {
				nmbrOfChickens ++;
			}
		}

		for (int i = 0; i < loadedCrops.size(); i++) {
				board.addCrops((Crops)loadedCrops.get(i));
				if(loadedCrops.get(i) instanceof Carrot) {
					nmbrOfCarrots++;
				}
				if(loadedCrops.get(i) instanceof Corn) {
					nmbrOfCorn++;
				}
				if(loadedCrops.get(i) instanceof Oat) {
					nmbrOfOats++;
				}
				if(loadedCrops.get(i) instanceof Lettuce) {
					nmbrOfLettuce++;
				}
		}
	}
	/**
	 * Called when loading an game. Sets up the commodity tab to the correct amount of animals
	 */
	public void setCommodityLoaded() {
		main.addCommodity("Cow", 500, nmbrOfCows, new ImageIcon("images/icons/cowIcon.png"));			// Changed for demo
		main.addCommodity("Pig", 300, nmbrOfPigs, new ImageIcon("images/icons/pigIcon.png"));			// Changed for demo
		main.addCommodity("Sheep", 200, nmbrOfSheeps, new ImageIcon("images/icons/sheepIcon.png"));		// Changed for demo
		main.addCommodity("Chicken", 50, nmbrOfChickens, new ImageIcon("images/icons/chickenIcon.png"));

	}
	/**
	 * Called when loading an game. Sets up the Property tab to the correct amount of propertys
	 */
	public void setPropertyLoaded() {
		main.addProperty("Barn", 1000, nmbrOfBarns, new ImageIcon("images/icons/barnIcon.png"));			// changed for demo
		main.addProperty("Pigsty", 1200, nmbrOfPigSty, new ImageIcon("images/icons/pigstyIcon.png"));
		main.addProperty("Stable", 1000, nmbrOfStable, new ImageIcon("images/icons/StableIcon.png"));
		main.addProperty("Hen house", 700, nmbrOfHenHouse, new ImageIcon("images/icons/henhouseIcon.png"));
	}
	/**
	 * Called when loading an game. Sets up the crops tab to the correct amount of crops
	 */
	public void setCropsLoaded() {
		main.addCrops("Carrot", 200, nmbrOfCarrots, new ImageIcon("images/icons/carrotIcon.png"));
		main.addCrops("Corn", 300, nmbrOfCorn, new ImageIcon("images/icons/cornIcon.png"));
		main.addCrops("Oats", 100, nmbrOfOats, new ImageIcon("images/icons/oatIcon.png"));
		main.addCrops("Lettuce", 400, nmbrOfLettuce, new ImageIcon("images/icons/lettuceIcon.png"));
	}
	public LinkedList<AnimalCopy> makeCopyOfAnimalList() {
		LinkedList<AnimalCopy> copyAnimalList = new LinkedList<AnimalCopy>();
		for (int i = 0; i < board.getAnimalList().size(); i++) {
			Animal animal = board.getAnimalList().get(i);
			AnimalCopy animalCopy = new AnimalCopy();
			if (animal instanceof Cow) {
				animalCopy.setAnimalType("Cow");
			} else if (animal instanceof Chicken) {
				animalCopy.setAnimalType("Chicken");
			} else if (animal instanceof Pig) {
				animalCopy.setAnimalType("Pig");
			} else if (animal instanceof Sheep) {
				animalCopy.setAnimalType("Sheep");
			}
			animalCopy.setX(animal.getX());
			animalCopy.setY(animal.getY());
			animalCopy.setSpeed(animal.getSpeed());
			animalCopy.setX_direction(animal.getX_direction());
			animalCopy.setY_direction(animal.getY_direction());
			copyAnimalList.add(animalCopy);
		}
		return copyAnimalList;
	}

	public LinkedList<Animal> restoreAnimalList(LinkedList<AnimalCopy> copyAnimals) {
		LinkedList<Animal> newAnimalList = new LinkedList<Animal>();
		for (int i = 0; i < copyAnimals.size(); i++) {
			AnimalCopy copy = copyAnimals.get(i);
			Animal animal = null;
			if (copy.getAnimalType().equals("Cow")) {
				animal = new Cow(copy.getX(), copy.getY());
				
			} else if (copy.getAnimalType().equals("Chicken")) {
				animal = new Chicken(copy.getX(), copy.getY());
				
			} else if (copy.getAnimalType().equals("Pig")) {
				animal = new Pig(copy.getX(), copy.getY());
				
			} else if (copy.getAnimalType().equals("Sheep")) {
				animal = new Sheep(copy.getX(), copy.getY());
				
			}
			animal.setSpeed(copy.getSpeed());
			animal.setX_direction(copy.getX_direction());
			animal.setY_direction(copy.getY_direction());
			newAnimalList.add(animal);
		}
		return newAnimalList;
	}
}
