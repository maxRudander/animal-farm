package farm;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

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
	private int day = 1;
	private int cash = 150000;
	private int x;
	private int y;

	private Random rand = new Random();

	public Controller() {
		new EventFileReader("files/testevent.txt");
		handler = EventHandler.getInstance();
		handler.setEffectMap(new EffectFileReader("files/testeffect.txt").getEffectMap());
		handler.instantiateEffects(this);

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
		board = new Board();
		main = new UIMain(this, board);
		day = getDay();
		cash = getCash();
		setCommodityStart();
		setPropertyStart();
		setCropsStart();
	}
	/**
	 * Sets up an loaded game
	 */
	public void loadedGame() {
		board = new Board();
		loadGame();
		main = new UIMain(this, board);
		setCommodityLoaded();
		setPropertyLoaded();
		setCropsLoaded();
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
		main.addCommodity("Cow", 500, 10, new ImageIcon("images/cowIcon.png"));			// Changed for demo
		main.addCommodity("Pig", 300, 10, new ImageIcon("images/pigIcon.png"));			// Changed for demo
		main.addCommodity("Sheep", 200, 10, new ImageIcon("images/sheepIcon.png"));		// Changed for demo
		main.addCommodity("Chicken", 50, 0, new ImageIcon("images/chickenIcon.png"));

	}

	/**
	 * Adds Properies and sets their prices. Should be called when application
	 * starts
	 */

	public void setPropertyStart() {
		main.addProperty("Barn", 1000, 1, new ImageIcon("images/barnIcon.png"));			// changed for demo
		main.addProperty("Pigsty", 1200, 0, new ImageIcon("images/pigstyIcon.png"));
		main.addProperty("Hen house", 700, 0, new ImageIcon("images/henhouseIcon.png"));
		main.addProperty("Stable", 1000, 0, new ImageIcon("images/StableIcon.png"));
	}

	public void setCropsStart() {
		main.addCrops("Carrot", 200, 0, new ImageIcon("images/carrotIcon.png"));
		main.addCrops("Corn", 300, 0, new ImageIcon("images/cornIcon.png"));
		main.addCrops("Oats", 100, 0, new ImageIcon("images/oatIcon.png"));
		main.addCrops("Lettuce", 400, 0, new ImageIcon("images/lettuceIcon.png"));
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

		if (name == "Barn") {
			board.addBuilding(new Barn(x, y));
		}

		if (name == "Pigsty") {
			board.addBuilding(new Pigsty(x, y));
		}

		if (name == "Hen house") {
			board.addBuilding(new HenHouse(x, y));
		}

		if (name == "Stable") {
			board.addBuilding(new Stable(x, y));
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
		if (name == "Barn") {
			board.removeBuilding(new Barn());
		}
		if (name == "Pigsty") {
			board.removeBuilding(new Pigsty());
		}
		if (name == "Hen house") {
			board.removeBuilding(new HenHouse());
		}
		if (name == "Stable") {
			board.removeBuilding(new Stable());
		}
	}

	public void BuyCrops(String name, int x, int y) {

		if (name == "Carrot") {
			board.addCrops(new Carrot(x, y));
		}

		if (name == "Corn") {
			board.addCrops(new Corn(x, y));
		}

		if (name == "Oats") {
			board.addCrops(new Oat(x, y));
		}

		if (name == "Lettuce") {
			board.addCrops(new Lettuce(x, y));
		}
	}

	public void sellCrops(String name) {
		if (name == "Carrot") {
			board.removeCrops(new Carrot());
		}
		if (name == "Corn") {
			board.removeCrops(new Corn());
		}
		if (name == "Oats") {
			board.removeCrops(new Oat());
		}
		if (name == "Lettuce") {
			board.removeCrops(new Lettuce());
		}
	}

	/**
	 * Returns the current day
	 * 
	 * @return day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * When called increases the day by one
	 */
	public void setDay() {
		this.day++;
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
	//____________________________Methods for saving and loading the game____________________________________________
	/**
	 * Collects all data into an LinkedList which it sends to SaveGame
	 */
	public void saveGame() {
		LinkedList saveList = new LinkedList();
		saveList.add(getDay());
		saveList.add(getCash());
		saveList.add(board.makeCopyOfAnimalList());
//		saveList.add(board.getAnimalList());
		saveList.add(board.getNode());
		saveList.add(board.getBuildingList());
		saveList.add(board.getCropsList());
		new SaveGame(saveList);
	}
	/**
	 * Separates the loaded list into day, cash, animals, buildings and crops and adds them to board
	 */
	public void loadGame() {
		load = new LoadGame();
		LinkedList loadedList = new LinkedList();
		LinkedList loadedAnimals = new LinkedList();
		LinkedList loadedBuildings = new LinkedList();
		LinkedList loadedCrops = new LinkedList();
		loadedList = load.load();
		this.day = (int) loadedList.pop();
		this.cash = (int) loadedList.pop();
		loadedAnimals = (LinkedList) loadedList.pop();
		board.setNode((Node[][])loadedList.pop());
		loadedBuildings = (LinkedList) loadedList.pop();
		loadedCrops = (LinkedList) loadedList.pop();
		//Convert Animalcopy-list to ordinary AnimalList.
		loadedAnimals = board.restoreAnimalList(loadedAnimals);
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
		main.addCommodity("Cow", 500, nmbrOfCows, new ImageIcon("images/cowIcon.png"));			// Changed for demo
		main.addCommodity("Pig", 300, nmbrOfPigs, new ImageIcon("images/pigIcon.png"));			// Changed for demo
		main.addCommodity("Sheep", 200, nmbrOfSheeps, new ImageIcon("images/sheepIcon.png"));		// Changed for demo
		main.addCommodity("Chicken", 50, nmbrOfChickens, new ImageIcon("images/chickenIcon.png"));

	}
	/**
	 * Called when loading an game. Sets up the Property tab to the correct amount of propertys
	 */
	public void setPropertyLoaded() {
		main.addProperty("Barn", 1000, nmbrOfBarns, new ImageIcon("images/barnIcon.png"));			// changed for demo
		main.addProperty("Pigsty", 1200, nmbrOfPigSty, new ImageIcon("images/pigstyIcon.png"));
		main.addProperty("Hen house", 700, nmbrOfHenHouse, new ImageIcon("images/henhouseIcon.png"));
		main.addProperty("Stable", 1000, nmbrOfStable, new ImageIcon("images/StableIcon.png"));
	}
	/**
	 * Called when loading an game. Sets up the crops tab to the correct amount of crops
	 */
	public void setCropsLoaded() {
		main.addCrops("Carrot", 200, nmbrOfCarrots, new ImageIcon("images/carrotIcon.png"));
		main.addCrops("Corn", 300, nmbrOfCorn, new ImageIcon("images/cornIcon.png"));
		main.addCrops("Oats", 100, nmbrOfOats, new ImageIcon("images/oatIcon.png"));
		main.addCrops("Lettuce", 400, nmbrOfLettuce, new ImageIcon("images/lettuceIcon.png"));
	}
}
