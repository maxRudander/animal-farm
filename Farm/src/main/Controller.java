package main;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import commodity.Animal;
import commodity.Apple;
import commodity.Appletree;
import commodity.Bacon;
import commodity.Carrot;
import commodity.Chicken;
import commodity.Corn;
import commodity.Cow;
import commodity.Crops;
import commodity.Eggs;
import commodity.Goods;
import commodity.Lettuce;
import commodity.Meat;
import commodity.Milk;
import commodity.Oat;
import commodity.OatMeal;
import commodity.Pig;
import commodity.Sheep;
import commodity.Sheepskin;
import event.ConditionFileReader;
import event.EffectFileReader;
import event.EventFileReader;
import event.EventHandler;
import event.Season;
import farm.Board;
import finance.BankLoan;
import finance.BankLoan2;
import finance.MafiaLoan;
import finance.PaydayLoan;
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
	private Season season;
	private BankLoan bLoan;
	private BankLoan2 bLoan2;
	private PaydayLoan pLoan;
	private MafiaLoan mLoan;

	private int counter = 0;
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
	private int nmbrOfAppletree = 0;
	private int nmbrOfMilk = 0;
	private int nmbrOfMeat = 0;
	private int nmbrOfEggs = 0;
	private int nmbrOfBacon = 0;
	private int nmbrOfOatMeal = 0;
	private int nmbrOfSheepskin = 0;
	private int nmbrOfApple = 0;
	private int week = 1;
	private int year = 1;
	private int cash;
	private int debt;
	private Random rand = new Random();
	private boolean environment;
	private String farmName;

	/**
	 * Constructor, sets up the EventHandler.
	 */
	public Controller() {
		new EventFileReader("files/testevent.txt");
		handler = EventHandler.getInstance();
		handler.setEffectMap(new EffectFileReader("files/testeffect.txt").getEffectMap());
		handler.setConditionMap(new ConditionFileReader("files/testcondition.txt").getConditionMap());
		handler.instantiateEffects(this);
		handler.instantiateConditions(this);
		season = new Season(board, main);
		bLoan = new BankLoan();
		bLoan2 = new BankLoan2();
		pLoan = new PaydayLoan();
		mLoan = new MafiaLoan();
	}
	
	/**
	 * Starts up an new game in either test or game environment.
	 * If a game is already running, the window is first disposed.
	 * @param isFirst true if a game hasn't been started, otherwise false
	 * @param gameEnvironment true for regular use, false for testing
	 */
	public void newGame(boolean isFirst, boolean gameEnvironment) {
		environment = gameEnvironment;
		if (!isFirst) {
			main.dispose();
			main = null;
			board = null;
		}
		resetFields();
		setName();
		board = new Board();
		main = new UIMain(this, board);
		if (gameEnvironment) {
			gameEnvironment();
			cash = 5000;
		} else {
			testEnvironment();
			cash = 1000000;
		}
		week = getWeek();
		cash = getCash();
		setCommodityStart();
		setPropertyStart();
		setCropsStart();
		setFinanceStart();
		setGoodsStart();
	}

	/**
	 * Sets up an environment for testing purposes
	 */
	public void testEnvironment() {
		handler.setGameMode(false);
		main.showConsole(true);
	}

	/**
	 * Sets up an environment for gaming purposes
	 */
	public void gameEnvironment() {
		handler.setGameMode(true);
		main.showConsole(false);
	}

	/**
	 * Returns the game environment.
	 * 
	 * @return true if game environment, false if test environment
	 */
	public boolean getEnvironment() {
		return environment;
	}

	/**
	 * Tries to load a saved game. If successful, the game is set up.
	 * If a game is already running, the window is first disposed.
	 * If no saved games exists, the attempt will fail.
	 * If a game is already running, a failure will return to the ongoing game.
	 * If a game is not running, a failure will return to the main menu.
	 * @param isFirst true if a game hasn't been started, otherwise false
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
		if (environment) {
			gameEnvironment();
		} else {
			testEnvironment();
		}
		setCommodityLoaded();
		setPropertyLoaded();
		setCropsLoaded();
		setFinanceLoaded();
		setGoodsLoaded();
		board.alterSeason(season.getSeason(getWeek()));
	}

	/**
	 * Disposes the window and returns to main menu.
	 */
	public void quit() {
		if (JOptionPane.showConfirmDialog(null, "Abandon farm??", "MainMenu",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			setChanged();
			notifyObservers(true);
			main.dispose();
		}
	}

	/**
	 * Exists the system after asking the player whether or not they would like to
	 * save their progress.
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
	 * This method is to be called if the player is a loser.
	 * A message is to be displayed taunting the loser for their incompetence.
	 * @param message - a taunting message
	 */
	public void gameOver(String message) {
		if(JOptionPane.showConfirmDialog(null, message + "\nReturn to main menu?", "GameOver", 
				JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION) {
					setChanged();
					notifyObservers(true);
					main.dispose();
				}
				else {
					System.exit(0);
				}

		
	}
	
	/**
	 * Handles the end of turn phase. Randomizes the sell/buy price and sell price for goods 
	 * Also generates goods for the user. Calls game over if out of cash.
	 */
	public void endTurn() {
		if (cash<0) gameOver("You're unable to pay your debts and are forced to declare bankruptcy");
		animalCost();
			for (int i = 0; i < main.getCommodityStock("Cow"); i++) {
				main.editGoods("Milk", -1, main.getGoodsStock("Milk") +1);
				board.addGoods(new Milk(), 1);
		}
			for (int i = 0; i < main.getCommodityStock("Chicken"); i++) {
				main.editGoods("Eggs", -1, main.getGoodsStock("Eggs") +1);
				board.addGoods(new Eggs(), 1);
		}
			for (int i = 0; i < main.getCropStock("Oats"); i++) {
				main.editGoods("OatMeal", -1, main.getGoodsStock("OatMeal") +1);
				board.addGoods(new OatMeal(), 1);
			}
			for (int i = 0; i < main.getCropStock("Appletree"); i++) {
				main.editGoods("Apple", -1, main.getGoodsStock("Apple") +2);
				board.addGoods(new Apple(), 2);
			}
			main.editCommodity("Cow", 500 + (rand.nextInt(100) - 50), -1);
			main.editCommodity("Pig", 300 + (rand.nextInt(50) - 25), -1);
			main.editCommodity("Sheep", 200 + (rand.nextInt(50) - 25), -1);
			main.editCommodity("Chicken", 50 + (rand.nextInt(20) - 10), -1);
			main.editGoods("Milk", 40 + (rand.nextInt(20) - 10), -1);
			main.editGoods("Bacon", 100 + (rand.nextInt(100) - 20), -1);
			main.editGoods("OatMeal", 30 + (rand.nextInt(20) - 10), -1);
			main.editGoods("Meat", 200 + (rand.nextInt(100) - 50), -1);
			main.editGoods("Sheepskin", 300 + (rand.nextInt(100) - 50), -1);
			main.editGoods("Eggs", 20 + (rand.nextInt(50) - 25), -1);
			main.editGoods("Apple", 10 + (rand.nextInt(20) - 10), -1);
	}
	/**
	 * Request a full update for all labels and buttons in the UI.
	 */
	public void requestCheck() {
		main.lblCheck();
		main.marketCheck();
		main.propertyCheck();
		main.propertyCheck();
		main.goodsCheck();
	}

	/**
	 * Adds Properties and sets their prices. Should be called when application
	 * starts
	 */
	public void setPropertyStart() {
		main.addProperty("Barn", 1000, 0, new ImageIcon("images/icons/barnIcon.png")); 
		main.addProperty("Pigsty", 1200, 0, new ImageIcon("images/icons/pigstyIcon.png"));
		main.addProperty("Stable", 1000, 0, new ImageIcon("images/icons/stableIcon.png"));
		main.addProperty("HenHouse", 700, 0, new ImageIcon("images/icons/henhouseIcon.png"));
	}

	/**
	 * Adds Commodities and sets their prices. Should be called when application
	 * starts
	 */
	public void setCommodityStart() {
		main.addCommodity("Cow", 500, 0, new ImageIcon("images/icons/cowIcon.png")); 
		main.addCommodity("Pig", 300, 0, new ImageIcon("images/icons/pigIcon.png")); 
		main.addCommodity("Sheep", 200, 0, new ImageIcon("images/icons/sheepIcon.png")); 
		main.addCommodity("Chicken", 50, 0, new ImageIcon("images/icons/chickenIcon.png"));

	}

	/**
	 * Adds Crops and sets their prices. Should be called when application starts
	 */
	public void setCropsStart() {
		main.addCrops("Carrot", 200, 0, new ImageIcon("images/icons/carrotIcon.png"));
		main.addCrops("Corn", 300, 0, new ImageIcon("images/icons/cornIcon.png"));
		main.addCrops("Oats", 100, 0, new ImageIcon("images/icons/oatIcon.png"));
		main.addCrops("Lettuce", 400, 0, new ImageIcon("images/icons/lettuceIcon.png"));
		main.addCrops("Appletree", 500, 0, new ImageIcon("images/icons/treeicon.png"));
	}

	/**
	 * Adds the different money lending options.
	 * Should be called when application starts
	 */
	public void setFinanceStart() {
		main.addFinance("Farm bank", bLoan.getInterest(), bLoan.getMinLoan(), bLoan.getMaxLoan(), new ImageIcon("images/icons/bankicon.png"));
		main.addFinance("City bank", bLoan2.getInterest(), bLoan2.getMinLoan(), bLoan2.getMaxLoan(), new ImageIcon("images/icons/bankicon.png"));
		main.addFinance("Payday loan", pLoan.getInterest(), pLoan.getMinLoan(), pLoan.getMaxLoan(), new ImageIcon("images/icons/smslanicon.png"));
		main.addFinance("Mafia loan", mLoan.getInterest(), mLoan.getMinLoan(), mLoan.getMaxLoan(), new ImageIcon("images/icons/ryskmaffiaicon.png"));
	}
	
	/**
	 * Adds goods and sets their prices. Should be called when application starts
	 */
	public void setGoodsStart() {
		main.addGoods("Milk", 40, 0, new ImageIcon("images/icons/milkicon.png"));
		main.addGoods("Bacon", 100, 0, new ImageIcon("images/icons/baconicon.png"));
		main.addGoods("Eggs", 20, 0, new ImageIcon("images/icons/eggsicon.png"));
		main.addGoods("Meat", 200, 0, new ImageIcon("images/icons/meaticon.png"));
		main.addGoods("OatMeal", 30, 0, new ImageIcon("images/icons/oatsicon.png"));
		main.addGoods("Sheepskin", 300, 0, new ImageIcon("images/icons/sheepskinicon.png"));
		main.addGoods("Apple", 10, 0, new ImageIcon("images/icons/appleicon.png"));
	}
	
	/**
	 * Returns the current stock of any animal, building, crop or good.
	 * @param name - the name of the product
	 * @return the current stock of the product
	 */
	public int getStock (String name) {
		int amount;
		if ((amount = main.getCommodityStock(name)) != -1) return amount;
		if ((amount = main.getPropertyStock(name)) != -1) return amount;
		if ((amount = main.getCropStock(name)) != -1) return amount;
		if ((amount = main.getGoodsStock(name)) != -1) return amount;
		else return 0;
	}

	/**
	 * Adds the selected animal to the board
	 * 
	 * @param name - the name of animal that will be added
	 * @param price - the amount of cash that will be removed.
	 */
	public void buyCommodity(String name, int price) {
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
		main.editCommodity(name, -1, main.getCommodityStock(name) + 1);
		cash = cash - price;
		requestCheck();
	}

	/**
	 * Sells the selected animal and removes it from the board
	 * 
	 * @param name - the name of animal that will be removed
	 * @param price - the amount of cash that will be added.
	 */
	public void sellCommodity(String name, int price) {
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
		main.editCommodity(name, -1, main.getCommodityStock(name) - 1);
		cash = cash + price;
		requestCheck();
	}

	/**
	 * Adds selected building to board through x and y coordinate provided by player.
	 * @param name - the name of the building that will be added
	 * @param price - the amount of cash that will be removed
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 */
	public void buyProperty(String name, int price, int x, int y) {
		if (name.equals("Barn")) {
			board.addBuilding(new Barn(x, y));
			Barn.gained();
		}
		if (name.equals("Pigsty")) {
			board.addBuilding(new Pigsty(x, y));
			Pigsty.gained();
		}
		if (name.equals("HenHouse")) {
			board.addBuilding(new HenHouse(x, y));
			HenHouse.gained();
		}
		if (name.equals("Stable")) {
			board.addBuilding(new Stable(x, y));
			Stable.gained();
		}
		main.editProperty(name, -1, main.getPropertyStock(name) + 1);
		cash = cash - price;
		requestCheck();
	}

	/**
	 * Sells the selected building and removes it from the board.
	 * @param name - the name of the building that will be removed
	 * @param price - the amount of cash that will be added
	 */
	public void sellProperty(String name, int price) {
		int animalCount;
		if (name.equals("Barn")) {
			animalCount = board.countAnimalsByType(new Cow());
			for (int i = 0; i < animalCount; i++) {
				board.removeAnimal(new Cow());
			}
			board.removeBuilding(new Barn());
			Barn.lost();
			for (int i = 0; i < animalCount; i++) {
				board.addAnimal(new Cow());
			}
		}
		if (name.equals("Pigsty")) {
			animalCount = board.countAnimalsByType(new Pig());
			for (int i = 0; i < animalCount; i++) {
				board.removeAnimal(new Pig());
			}
			board.removeBuilding(new Pigsty());
			Pigsty.lost();
			for (int i = 0; i < animalCount; i++) {
				board.addAnimal(new Pig());
			}
		}
		if (name.equals("HenHouse")) {
			animalCount = board.countAnimalsByType(new Chicken());
			for (int i = 0; i < animalCount; i++) {
				board.removeAnimal(new Chicken());
			}
			board.removeBuilding(new HenHouse());
			HenHouse.lost();
			for (int i = 0; i < animalCount; i++) {
				board.addAnimal(new Chicken());
			}
		}
		if (name.equals("Stable")) {
			animalCount = board.countAnimalsByType(new Sheep());
			for (int i = 0; i < animalCount; i++) {
				board.removeAnimal(new Sheep());
			}
			board.removeBuilding(new Stable());
			Stable.lost();
			for (int i = 0; i < animalCount; i++) {
				board.addAnimal(new Sheep());
			}
		}
		main.editProperty(name, -1, main.getPropertyStock(name) - 1);
		cash = cash + price;
		requestCheck();
	}
	
	/**
	 * Adds selected crop to board through x and y coordinate provided by player.
	 * @param name - the name of the crop that will be added
	 * @param price - the amount of cash that will be removed
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 */
	public void buyCrops(String name, int price, int x, int y) {
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
		if (name.equals("Appletree")) {
			board.addCrops(new Appletree(x,y));
		}
		main.editCrop(name, -1, main.getCropStock(name) + 1);
		cash = cash - price;
		requestCheck();
	}

	/**
	 * Sells the selected crop and removes it from the board.
	 * @param name - the name of the building that will be removed
	 * @param price - the amount of cash that will be added
	 */
	public void sellCrops(String name, int price) {
		if (name.equals("Carrot")) {
			board.removeCrops(new Carrot());
		}
		if (name.equals("Corn")) {
			board.removeCrops(new Corn());
		}
		if (name.equals("Oats")) {
			board.removeCrops(new Oat());
		}
		if (name.equals("Lettuce")) {
			board.removeCrops(new Lettuce());
		}
		if (name.equals("Appletree")) {
			board.removeCrops(new Appletree());
		}
		main.editCrop(name, -1, main.getCropStock(name) - 1);
		cash = cash + price;
		requestCheck();
	}
	/**
	 * Sells the selected good and removes it from the board
	 * 
	 * @param name
	 *            name of goods that will be sold
	 */
	public void sellGoods(String name, int price) {
		if (name.equals("Milk")) {
			board.removeGoods(new Milk(), 1);
		}
		if (name.equals("Eggs")) {
			board.removeGoods(new Eggs(), 1);
		}
		if (name.equals("Bacon")) {
			board.removeGoods(new Bacon(), 1);
		}
		if (name.equals("Meat")) {
			board.removeGoods(new Meat(), 1);
		}
		if (name.equals("Sheepskin")) {
			board.removeGoods(new Sheepskin(), 1);
		}
		if (name.equals("OatsMeal")) {
			board.removeGoods(new OatMeal(), 1);
		}
		if (name.equals("Apple")) {
			board.removeGoods(new Apple(), 1);
		}
		main.editGoods(name, -1, main.getGoodsStock(name) - 1);
		cash = cash + price;
		requestCheck();
	}
	/** 
	 * Slaughters an animal by removing it and adding an appropriate product.
	 * @param name - the name of the animal to be slaughtered
	 */
	public void slaughterCommodity(String name) {
		String product = null;
		int amount = 1;
		if (name.equals("Cow")) {
			board.removeAnimal(new Cow());
			product = "Meat";
			amount = 4;
		}
		if (name.equals("Pig")) {
			board.removeAnimal(new Pig());
			product = "Bacon";
			amount = 2;
		}
		if (name.equals("Chicken")) {
			board.removeAnimal(new Chicken());
			product = "Meat";
		}
		if (name.equals("Sheep")) {
			board.removeAnimal(new Sheep());
			product = "Sheepskin";
		}
		try {
			board.addGoods((Goods) Class.forName("commodity."+product).newInstance(), amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		main.editGoods(product, -1, main.getGoodsStock(product) + amount);
		main.editCommodity(name, -1, main.getCommodityStock(name) - 1);
		requestCheck();
	}

	/**
	 * If the player doesn't have any loans at the selected lender, he will be able
	 * to sign up for an new
	 * 
	 * @param name - the name of the lender
	 * @param amount - the amount the user writes in the field
	 */
	public void acceptLoan(String name, int amount) {
		int loanAmount = amount;
		if (name.equals("Farm bank")) {
			if (!bLoan.getHasLoan()) {
				if (loanAmount >= bLoan.getMinLoan() && loanAmount <= bLoan.getMaxLoan()) {
					setDebt(loanAmount);
					bLoan.setDebt(loanAmount, false);
					setCash(loanAmount);
					bLoan.setHasLoan(true);
				} else {
					JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
				}
			}
		}
		if (name.equals("City bank")) {
			if (!bLoan2.getHasLoan()) {
				if (loanAmount >= bLoan2.getMinLoan() && loanAmount <= bLoan2.getMaxLoan()) {
					setDebt(loanAmount);
					bLoan2.setDebt(loanAmount, false);
					setCash(loanAmount);
					bLoan2.setHasLoan(true);
				} else {
					JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
				}
			}
		}
		if (name.equals("Payday loan")) {
			if (!pLoan.getHasLoan()) {
				if (loanAmount >= pLoan.getMinLoan() && loanAmount <= pLoan.getMaxLoan()) {
					setDebt(loanAmount);
					pLoan.setDebt(loanAmount, false);
					setCash(loanAmount);
					pLoan.setHasLoan(true);
				} else {
					JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
				}
			}
		}
		if (name.equals("Mafia loan")) {
			if (!mLoan.getHasLoan()) {
				if (loanAmount >= mLoan.getMinLoan() && loanAmount <= mLoan.getMaxLoan()) {
					setDebt(loanAmount);
					mLoan.setDebt(loanAmount, false);
					setCash(loanAmount);
					mLoan.setHasLoan(true);
				} else {
					JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
				}
			}
		}
		requestCheck();
	}

	/**
	 * Lets the player pay off his loan if he has one
	 * 
	 * @param name - the name of the lender
	 * @param amount - the amount the user writes in the field
	 */
	public void payOffLoan(String name, int amount) {
		int payment = amount;
		if (name.equals("Farm bank")) {
			if (bLoan.getHasLoan()) {
				if (payment > 0 && payment <= bLoan.getDebt()) {
					setDebt(-payment);
					setCash(-payment);
					bLoan.setDebt(-payment, false);
					main.lblCheck();
				}
				if (payment == 0) {
					JOptionPane.showMessageDialog(null, "You need to enter an amount greater than 0");
				}
				if (payment > bLoan.getDebt()) {
					setDebt((int) bLoan.getDebt() * -1);
					setCash((int) bLoan.getDebt() * -1);
					bLoan.setDebt((int) bLoan.getDebt() * -1, false);
					main.lblCheck();
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have no debt to this lender");
			}
			if (bLoan.getDebt() == 0) {
				bLoan.setHasLoan(false);
			}
		}
		if (name.equals("City bank")) {
			if (bLoan2.getHasLoan()) {
				if (payment > 0 && payment <= getDebt()) {
					setDebt(-payment);
					setCash(-payment);
					bLoan2.setDebt(-payment, false);
					main.lblCheck();
				}
				if (payment == 0) {
					JOptionPane.showMessageDialog(null, "You need to enter an amount greater than 0");
				}
				if (payment > bLoan2.getDebt()) {
					setDebt((int) bLoan2.getDebt() * -1);
					setCash((int) bLoan2.getDebt() * -1);
					bLoan2.setDebt((int) bLoan2.getDebt() * -1, false);
					main.lblCheck();
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have no debt to this lender");
			}
			if (bLoan2.getDebt() == 0) {
				bLoan2.setHasLoan(false);
			}
		}
		if (name.equals("Payday loan")) {
			if (pLoan.getHasLoan()) {

				if (payment > 0 && payment <= getDebt()) {
					setDebt(-payment);
					setCash(-payment);
					pLoan.setDebt(-payment, false);
					main.lblCheck();
				}
				if (payment == 0) {
					JOptionPane.showMessageDialog(null, "You need to enter an amount greater than 0");
				}
				if (payment > pLoan.getDebt()) {
					setDebt((int) pLoan.getDebt() * -1);
					setCash((int) pLoan.getDebt() * -1);
					pLoan.setDebt((int) pLoan.getDebt() * -1, false);
					main.lblCheck();
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have no debt to this lender");
			}
			if (pLoan.getDebt() == 0) {
				pLoan.setHasLoan(false);
			}
		}
		if (name.equals("Mafia loan")) {
			if (mLoan.getHasLoan()) {
				if (payment > 0 && payment <= getDebt()) {
					setDebt(-payment);
					setCash(-payment);
					mLoan.setDebt(-payment, false);
					main.lblCheck();
				}
				if (payment == 0) {
					JOptionPane.showMessageDialog(null, "You need to enter an amount greater than 0");
				}
				if (payment > mLoan.getDebt()) {
					setDebt((int) mLoan.getDebt() * -1);
					setCash((int) mLoan.getDebt() * -1);
					mLoan.setDebt((int) mLoan.getDebt() * -1, false);
					main.lblCheck();
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have no debt to this lender");
			}
			if (mLoan.getDebt() == 0) {
				mLoan.setHasLoan(false);
			}
		}
	}
	/**
	 * Forces a player to pay at least the interest every 4 weeks if they are in debt
	 */
	public void forcedPayment() {
		String message ="You're unable to pay your debts and are forced to declare bankruptcy";
		double payment = 0;
		double interest;
		double interestToPay;
		boolean payed = false;
		double fullPay;
		if (bLoan.getHasLoan()) {
			payed = false;
			interest = (Double) bLoan.getInterest() / 100;
			interestToPay = (Double) bLoan.getDebt() * interest;
			fullPay = bLoan.getDebt() + interestToPay;
			if (interestToPay > cash) {
				gameOver(message);
				return;
			}
			while (!payed) {
				if (payment < interestToPay) {
					try {
						payment = Double
								.parseDouble(JOptionPane.showInputDialog("Your payment is due! Your debt to the bank is "
										+ bLoan.getDebt() + "\nYou have to atleast pay the interest which is "
										+ interestToPay + "\n to clear the debt you have to pay " + fullPay));
						if (payment > fullPay || payment > cash) throw new NumberFormatException();
					} catch (NumberFormatException | NullPointerException e) {
						payment = 0;
					}

				}
				if (payment >= interestToPay) {
					int pay = (int) ((payment - interestToPay) * -1);
					setCash((int) -payment);
					bLoan.setDebt(pay, false);
					setDebt(pay);
					main.lblCheck();
					payed = true;
					payment = 0;
				}
			}
			if (bLoan.getDebt() == 0) {
				bLoan.setHasLoan(false);
			}
		}
		if (bLoan2.getHasLoan()) {
			interest = (Double) bLoan2.getInterest() / 100;
			interestToPay = (Double) bLoan2.getDebt() * interest;
			payed = false;
			fullPay = bLoan2.getDebt() + interestToPay;
			if (interestToPay > cash) {
				gameOver(message);
				return;
			}
			while (!payed) {
				if (payment < interestToPay) {
					try {
						payment = Double
								.parseDouble(JOptionPane.showInputDialog("Your payment is due! Your debt to the bank is "
										+ bLoan2.getDebt() + "\nYou have to atleast pay the interest which is "
										+ interestToPay + "\n to clear the debt you have to pay " + fullPay));
						if (payment > fullPay || payment > cash) throw new NumberFormatException();
					} catch (NumberFormatException | NullPointerException e) {
						payment = 0;
					}					}
				if (payment >= interestToPay) {
					int pay = (int) ((payment - interestToPay) * -1);
					setCash((int) -payment);
					bLoan2.setDebt(pay, false);
					setDebt(pay);
					main.lblCheck();
					payed = true;
					payment = 0;
				}
			}
			if (bLoan2.getDebt() == 0) {
				bLoan2.setHasLoan(false);
			}
		}
		if (pLoan.getHasLoan()) {
			interest = (Double) pLoan.getInterest() / 100;
			interestToPay = (Double) pLoan.getDebt() * interest;
			fullPay = pLoan.getDebt() + interestToPay;
			payed = false;
			if (interestToPay > cash) {
				gameOver(message);
				return;
			}
			while (!payed) {
				if (payment < interestToPay) {
					try {
						payment = Double
								.parseDouble(JOptionPane.showInputDialog("Your payment is due! Your debt to the payday lender is "
										+ pLoan.getDebt() + "\nYou have to atleast pay the interest which is "
										+ interestToPay + "\n to clear the debt you have to pay " + fullPay));
						if (payment > fullPay || payment > cash) throw new NumberFormatException();
					} catch (NumberFormatException | NullPointerException e) {
						payment = 0;
					}
				}
				if (payment >= interestToPay) {
					int pay = (int) ((payment - interestToPay) * -1);
					setCash((int) -payment);
					pLoan.setDebt(pay, false);
					setDebt(pay);
					main.lblCheck();
					payed = true;
					payment = 0;
				}
			}
			if (pLoan.getDebt() == 0) {
				pLoan.setHasLoan(false);
			}
		}
		if (mLoan.getHasLoan()) {
			interest = (Double) mLoan.getInterest() / 100;
			interestToPay = (Double) mLoan.getDebt() * interest;
			fullPay = mLoan.getDebt() + interestToPay;
			payed = false;
			if (interestToPay > cash) {
				gameOver(message);
				return;
			}
			while (!payed) {
				if (payment < interestToPay) {
					try {
						payment = Double
								.parseDouble(JOptionPane.showInputDialog("Your payment is due! Your debt to the mafia is "
										+ mLoan.getDebt() + "\nYou have to atleast pay the interest which is "
										+ interestToPay + "\n to clear the debt you have to pay " + fullPay));
						if (payment > fullPay || payment > cash) throw new NumberFormatException();
					} catch (NumberFormatException | NullPointerException e) {
						payment = 0;
					}
				}
				if (payment >= interestToPay) {
					int pay = (int) ((payment - interestToPay) * -1);
					setCash((int) -payment);
					mLoan.setDebt(pay, false);
					setDebt(pay);
					main.lblCheck();
					payed = true;
					payment = 0;
				}
			}
			if (mLoan.getDebt() == 0) {
				mLoan.setHasLoan(false);
			}
		}
	}

	/**
	 * Should be called once a week. The cost for keeping an animal i.e the cost for
	 * food and maintenance
	 */
	public void animalCost() {
		LinkedList<Animal> list = new LinkedList<Animal>();
		list = board.getAnimalList();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Cow) {
				setCash(-40);
			}
			if (list.get(i) instanceof Chicken) {
				setCash(-10);
			}
			if (list.get(i) instanceof Pig) {
				setCash(-20);
			}
			if (list.get(i) instanceof Sheep) {
				setCash(-20);
			}
		}
	}

	/**
	 * @return the current week
	 */
	public int getWeek() {
		return this.week;
	}
	/**
	 * When called increases the week by one.
	 */
	public void setWeek() {
		this.week++;
		if (week >= 52) {
			setYear();
		}
		if (bLoan.getHasLoan() || bLoan2.getHasLoan() || pLoan.getHasLoan() || mLoan.getHasLoan()) {
			counter++;
			if (counter == 4) {
				forcedPayment();
				counter = 0;
			}
		}
	}

	/**
	 * Returns the current year
	 * 
	 * @return year The current year
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
	 * Returns how much cash the player has
	 * 
	 * @return cash The current cash
	 */
	public int getCash() {
		return cash;
	}

	/**
	 * Increases the players cash by the entered amount. Negative values decreases
	 * cash.
	 * 
	 * @param amount amount that will be added/subtracted
	 */
	public void setCash(int amount) {
		cash = cash + amount;
	}

	/**
	 * returns the total debt of all loans combined
	 * 
	 * @return the total debt
	 */
	public int getDebt() {
		return debt;
	}

	/**
	 * sets the total debt of all loans combined
	 * 
	 * @param d
	 *            the new amount that will be added or subtracted
	 */
	public void setDebt(int d) {
		debt = debt + d;
	}

	/**
	 * Sets a new name for the farm.
	 */
	public void setName() {
		String newName;
		try {
			newName = (String) JOptionPane.showInputDialog(null, "Enter your name", "Farmer you?",
					JOptionPane.QUESTION_MESSAGE, null, null, farmName);
			if (newName.length() < 1) {
				throw new NullPointerException();
			}
			farmName = newName;
		} catch (NullPointerException e) {
			if (farmName == null) {
				farmName = "Nobody";
			}
		}
		if (main != null) {
			main.setTitle(farmName + "'s Farm");
		}
	}

	/**
	 * Returns the name of the farm.
	 * 
	 * @return - the name of the farm.
	 */
	public String getName() {
		return farmName;
	}

	/**
	 * Collects all necessary data into an LinkedList which it sends to SaveGame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveGame() {
		LinkedList saveList = new LinkedList();
		saveList.add(getYear());
		saveList.add(getWeek());
		saveList.add(getCash());
		saveList.add(getDebt());
		saveList.add(getName());
		saveList.add(getEnvironment());
		saveList.add(saveLoans());
		saveList.add(saveDebts());
		saveList.add(makeCopyOfAnimalList());
		saveList.add(board.getNode());
		saveList.add(board.getBuildingList());
		saveList.add(board.getCropsList());
		saveList.add(board.getGoodsList());
		new SaveGame(saveList);
	}

	/**
	 * Separates the loaded list and stores the value.
	 * Adds items to Board when appropriate.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void loadGame(LoadGame load) {
		LinkedList loadedList = new LinkedList();
		LinkedList loadedAnimals = new LinkedList();
		LinkedList loadedBuildings = new LinkedList();
		LinkedList loadedCrops = new LinkedList();
		LinkedList loadedGoods = new LinkedList();
		Double [] debts;
		Boolean [] hasLoans;
		loadedList = load.load();
		this.year = (int) loadedList.pop();
		this.week = (int) loadedList.pop();
		this.cash = (int) loadedList.pop();
		this.debt = (int) loadedList.pop();
		this.farmName = (String) loadedList.pop();
		this.environment = (Boolean) loadedList.pop();
		hasLoans = (Boolean[]) loadedList.pop();
		debts = (Double[]) loadedList.pop();
		loadedAnimals = (LinkedList) loadedList.pop();
		board.setNode((boolean[][]) loadedList.pop());
		loadedBuildings = (LinkedList) loadedList.pop();
		loadedCrops = (LinkedList) loadedList.pop();
		loadedGoods = (LinkedList) loadedList.pop();
		resetFields();
		loadedAnimals = restoreAnimalList(loadedAnimals);
		for (int i = 0; i < loadedBuildings.size(); i++) {
			board.addBuilding((Building) loadedBuildings.get(i));
			if (loadedBuildings.get(i) instanceof Barn) {
				nmbrOfBarns++;
				Barn.gained();
			}
			if (loadedBuildings.get(i) instanceof Pigsty) {
				nmbrOfPigSty++;
				Pigsty.gained();
			}
			if (loadedBuildings.get(i) instanceof HenHouse) {
				nmbrOfHenHouse++;
				HenHouse.gained();
			}
			if (loadedBuildings.get(i) instanceof Stable) {
				nmbrOfStable++;
				Stable.gained();
			}
		}
		for (int i = 0; i < loadedAnimals.size(); i++) {
			board.addAnimal((Animal) loadedAnimals.get(i));

			if (loadedAnimals.get(i) instanceof Cow) {
				nmbrOfCows++;
			}
			if (loadedAnimals.get(i) instanceof Pig) {
				nmbrOfPigs++;
			}
			if (loadedAnimals.get(i) instanceof Sheep) {
				nmbrOfSheeps++;
			}
			if (loadedAnimals.get(i) instanceof Chicken) {
				nmbrOfChickens++;
			}
		}

		for (int i = 0; i < loadedCrops.size(); i++) {
			board.addCrops((Crops) loadedCrops.get(i));
			if (loadedCrops.get(i) instanceof Carrot) {
				nmbrOfCarrots++;
			}
			if (loadedCrops.get(i) instanceof Corn) {
				nmbrOfCorn++;
			}
			if (loadedCrops.get(i) instanceof Oat) {
				nmbrOfOats++;
			}
			if (loadedCrops.get(i) instanceof Lettuce) {
				nmbrOfLettuce++;
			}
			if (loadedCrops.get(i) instanceof Appletree) {
				nmbrOfAppletree++;
			}
		}

		for (int i = 0; i < loadedGoods.size(); i++) {
			board.addGoods((Goods) loadedGoods.get(i), 1);
			if (loadedGoods.get(i) instanceof Milk) {
				nmbrOfMilk++;
			}
			if (loadedGoods.get(i) instanceof Bacon) {
				nmbrOfBacon++;
			}
			if (loadedGoods.get(i) instanceof Meat) {
				nmbrOfMeat++;
			}
			if (loadedGoods.get(i) instanceof Eggs) {
				nmbrOfEggs++;
			}
			if (loadedGoods.get(i) instanceof OatMeal) {
				nmbrOfOatMeal++;
			}
			if (loadedGoods.get(i) instanceof Sheepskin) {
				nmbrOfSheepskin++;
			}
			if (loadedGoods.get(i) instanceof Apple) {
				nmbrOfApple++;
			}
		}
		setDebts(hasLoans, debts);

	}

	/**
	 * Called when loading a game. Sets up the commodity tab to the correct amount
	 * of animals.
	 */
	public void setCommodityLoaded() {
		main.addCommodity("Cow", 500, nmbrOfCows, new ImageIcon("images/icons/cowIcon.png")); 
		main.addCommodity("Pig", 300, nmbrOfPigs, new ImageIcon("images/icons/pigIcon.png"));
		main.addCommodity("Sheep", 200, nmbrOfSheeps, new ImageIcon("images/icons/sheepIcon.png")); 
		main.addCommodity("Chicken", 50, nmbrOfChickens, new ImageIcon("images/icons/chickenIcon.png"));

	}

	/**
	 * Called when loading an game. Sets up the Property tab to the correct amount
	 * of properties.
	 */
	public void setPropertyLoaded() {
		main.addProperty("Barn", 1000, nmbrOfBarns, new ImageIcon("images/icons/barnIcon.png")); 
		main.addProperty("Pigsty", 1200, nmbrOfPigSty, new ImageIcon("images/icons/pigstyIcon.png"));
		main.addProperty("Stable", 1000, nmbrOfStable, new ImageIcon("images/icons/StableIcon.png"));
		main.addProperty("HenHouse", 700, nmbrOfHenHouse, new ImageIcon("images/icons/henhouseIcon.png"));
	}

	/**
	 * Called when loading a game. Sets up the crops tab to the correct amount of
	 * crops.
	 */
	public void setCropsLoaded() {
		main.addCrops("Carrot", 200, nmbrOfCarrots, new ImageIcon("images/icons/carrotIcon.png"));
		main.addCrops("Corn", 300, nmbrOfCorn, new ImageIcon("images/icons/cornIcon.png"));
		main.addCrops("Oats", 100, nmbrOfOats, new ImageIcon("images/icons/oatIcon.png"));
		main.addCrops("Lettuce", 400, nmbrOfLettuce, new ImageIcon("images/icons/lettuceIcon.png"));
		main.addCrops("Appletree", 500, nmbrOfAppletree, new ImageIcon("images/icons/treeicon.png"));
	}
	/**
	 * Called when loading a game. Sets up the goods tab to the correct amount of
	 * goods.
	 */
	public void setGoodsLoaded() {
		main.addGoods("Milk", 40, nmbrOfMilk, new ImageIcon("images/icons/milkicon.png"));
		main.addGoods("Bacon", 100, nmbrOfBacon, new ImageIcon("images/icons/baconicon.png"));
		main.addGoods("OatMeal", 30, nmbrOfOatMeal, new ImageIcon("images/icons/oatsicon.png"));
		main.addGoods("Meat", 200, nmbrOfMeat, new ImageIcon("images/icons/meaticon.png"));
		main.addGoods("Sheepskin", 300, nmbrOfSheepskin, new ImageIcon("images/icons/sheepskinicon.png"));
		main.addGoods("Eggs", 20, nmbrOfEggs, new ImageIcon("images/icons/eggsicon.png"));
		main.addGoods("Apple", 10, nmbrOfApple, new ImageIcon("images/icons/appleicon.png"));
	}
	/**
	 * Called when loading a game. Sets up the finance tab. Currently no different from default method.
	 */
	public void setFinanceLoaded() {
		main.addFinance("Farm bank", bLoan.getInterest(), bLoan.getMinLoan(), bLoan.getMaxLoan(), new ImageIcon("images/icons/bankicon.png"));
		main.addFinance("City bank", bLoan2.getInterest(), bLoan2.getMinLoan(), bLoan2.getMaxLoan(), new ImageIcon("images/icons/bankicon.png"));
		main.addFinance("Payday loan", pLoan.getInterest(), pLoan.getMinLoan(), pLoan.getMaxLoan(), new ImageIcon("images/icons/smslanicon.png"));
		main.addFinance("Mafia loan", mLoan.getInterest(), mLoan.getMinLoan(), mLoan.getMaxLoan(), new ImageIcon("images/icons/ryskmaffiaicon.png"));
	}
	/**
	 * Stores the status of every type of loan.
	 * @return a Boolean array the statuses
	 */
	public Boolean [] saveLoans() {
		return new Boolean [] {bLoan.getHasLoan(), bLoan2.getHasLoan(), pLoan.getHasLoan(), mLoan.getHasLoan()};
		
	}
	/**
	 * Stores the total debt owed to each lender.
	 * @return a Double array containing the debts
	 */
	public Double [] saveDebts () {
		return new Double [] {bLoan.getDebt(), bLoan2.getDebt(), pLoan.getDebt(), mLoan.getDebt()};
	}
	public void setDebts (Boolean [] hasDebts, Double [] debts) {
		bLoan.setHasLoan(hasDebts[0]);
		bLoan2.setHasLoan(hasDebts[1]);
		pLoan.setHasLoan(hasDebts[2]);
		mLoan.setHasLoan(hasDebts[3]);
		if (bLoan.getHasLoan()) bLoan.setDebt(debts[0].intValue(), true);
		if (bLoan2.getHasLoan()) bLoan2.setDebt(debts[1].intValue(), true);
		if (pLoan.getHasLoan()) pLoan.setDebt(debts[2].intValue(), true);
		if (mLoan.getHasLoan()) mLoan.setDebt(debts[3].intValue(), true);
	}

	/**
	 * Makes a copy of and returns the animal list.
	 * 
	 * @return - the copy
	 */
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
			animalCopy.setX_direction(animal.getX_direction());
			animalCopy.setY_direction(animal.getY_direction());
			copyAnimalList.add(animalCopy);
		}
		return copyAnimalList;
	}

	/**
	 * Restores the animal list from the copy.
	 * 
	 * @param copyAnimals
	 *            - the copied list
	 * @return - the restored list
	 */
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
			animal.setX_direction(copy.getX_direction());
			animal.setY_direction(copy.getY_direction());
			newAnimalList.add(animal);
		}
		return newAnimalList;
	}
	/**
	 * Resets all setup related fields in Controller.
	 * Also resets the various static fields in Animal subclasses.
	 */
	public void resetFields () {
		counter = 0;
		nmbrOfCows = 0;
		nmbrOfPigs = 0;
		nmbrOfSheeps = 0;
		nmbrOfChickens = 0;
		nmbrOfBarns = 0;
		nmbrOfPigSty = 0;
		nmbrOfHenHouse = 0;
		nmbrOfStable = 0;
		nmbrOfCarrots = 0;
		nmbrOfCorn = 0;
		nmbrOfOats = 0;
		nmbrOfLettuce = 0;
		nmbrOfAppletree = 0;
		nmbrOfMilk = 0;
		nmbrOfMeat = 0;
		nmbrOfEggs = 0;
		nmbrOfBacon = 0;
		nmbrOfOatMeal = 0;
		nmbrOfSheepskin = 0;
		nmbrOfApple = 0;
		Cow.resetStorage();
		Pig.resetStorage();
		Sheep.resetStorage();
		Chicken.resetStorage();
		
	}
}
