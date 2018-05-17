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
import finance.BankLoan;
import finance.BankLoan2;
import finance.MaffiaLoan;
import finance.PaydayLoan;
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
	private BankLoan bLoan;
	private BankLoan2 bLoan2;
	private PaydayLoan pLoan;
	private MaffiaLoan mLoan;

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
	private int week = 1;
	private int year = 1;
	private int cash;
	private int debt;
	private int x;
	private int y;

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
		handler.instantiateEffects(this);
		season = new Season(board, main);
		bLoan = new BankLoan();
		bLoan2 = new BankLoan2();
		pLoan = new PaydayLoan();
		mLoan = new MaffiaLoan();
	}

	/**
	 * Starts up an new game.
	 */
	public void newGame(boolean isFirst, boolean gameEnvironment) {
		environment = gameEnvironment;
		if (!isFirst) {
			main.dispose();
			main = null;
			board = null;
		}
		setName();
		board = new Board();
		main = new UIMain(this, board);
		if (gameEnvironment) {
			gameEnvironment();
		} else {
			testEnvironment();
		}
		week = getWeek();
		cash = getCash();
		setCommodityStart();
		setPropertyStart();
		setCropsStart();
		setFinanceStart();
	}

	/**
	 * Sets up an environment for testing purposes
	 */
	public void testEnvironment() {
		cash = 1000000;
		handler.setGameMode(false);
		main.showConsole(true);
	}

	/**
	 * Sets up an environment for gaming purposes
	 */
	public void gameEnvironment() {
		cash = 5000;
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
		// season = new Season(board, main);
		// season.setSeason(week);
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
	 * Handles the end of turn phase. Randomizes the sell/buy price
	 */
	public void endTurn() {
		main.editCommodity("Cow", 500 + (rand.nextInt(100) - 50), -1);
		main.editCommodity("Pig", 300 + (rand.nextInt(50) - 25), -1);
		main.editCommodity("Sheep", 200 + (rand.nextInt(50) - 25), -1);
		main.editCommodity("Chicken", 50 + (rand.nextInt(20) - 10), -1);
	}

	/**
	 * Request a full update for all labels and buttons in the UI.
	 */
	public void requestCheck() {
		main.lblCheck();
		main.marketCheck();
		main.propertyCheck();
		main.propertyCheck();
	}

	/**
	 * Adds Commodities and sets their prices. Should be called when application
	 * starts
	 */
	public void setCommodityStart() {
		main.addCommodity("Cow", 500, 0, new ImageIcon("images/icons/cowIcon.png")); // Changed for demo
		main.addCommodity("Pig", 300, 0, new ImageIcon("images/icons/pigIcon.png")); // Changed for demo
		main.addCommodity("Sheep", 200, 0, new ImageIcon("images/icons/sheepIcon.png")); // Changed for demo
		main.addCommodity("Chicken", 50, 0, new ImageIcon("images/icons/chickenIcon.png"));

	}

	/**
	 * Adds Properties and sets their prices. Should be called when application
	 * starts
	 */

	public void setPropertyStart() {
		main.addProperty("Barn", 1000, 0, new ImageIcon("images/icons/barnIcon.png")); // changed for demo
		main.addProperty("Pigsty", 1200, 0, new ImageIcon("images/icons/pigstyIcon.png"));
		main.addProperty("Stable", 1000, 0, new ImageIcon("images/icons/stableIcon.png"));
		main.addProperty("HenHouse", 700, 0, new ImageIcon("images/icons/henhouseIcon.png"));
	}

	/**
	 * Adds Crops and sets their prices. Should be called when application starts
	 */
	public void setCropsStart() {
		main.addCrops("Carrot", 200, 0, new ImageIcon("images/icons/carrotIcon.png"));
		main.addCrops("Corn", 300, 0, new ImageIcon("images/icons/cornIcon.png"));
		main.addCrops("Oats", 100, 0, new ImageIcon("images/icons/oatIcon.png"));
		main.addCrops("Lettuce", 400, 0, new ImageIcon("images/icons/lettuceIcon.png"));
	}

	/**
	 * adds the different lender options to the ui. Should be called when
	 * application starts
	 */
	public void setFinanceStart() {

		main.addFinance("Bankloan", bLoan.getInterest());
		main.addFinance("Bankloan 2", bLoan2.getInterest());
		main.addFinance("Payday loan", pLoan.getInterest());
		main.addFinance("Maffia loan", mLoan.getInterest());
	}

	/**
	 * Adds the selected animal to the board
	 * 
	 * @param name
	 *            name of animal that will be added
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
		main.editCommodity(name, -1, main.getCommodityStock(name)+1);
		cash = cash - price;
		requestCheck();
	}

	/**
	 * Sells the selected animal and removes it from the board
	 * 
	 * @param name
	 *            name of animal that will be sold
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
		main.editCommodity(name, -1, main.getCommodityStock(name)-1);
		cash = cash + price;
		requestCheck();
	}

	/**
	 * Adds selected building to board through x and y coordinate provided by player
	 * @param y 
	 * 
	 * @param name,
	 *            x, y
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
		main.editProperty(name, -1, main.getPropertyStock(name)+1);
		cash = cash - price;
		requestCheck();
	}

	/**
	 * Sells the selected building and removes it from the board
	 * 
	 * @param name
	 *            name of building that will be sold
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
		main.editProperty(name, -1, main.getPropertyStock(name)-1);
		cash = cash + price;
		requestCheck();
	}

	/**
	 * Adds selected crop to board through x and y coordinate provided by player
	 * 
	 * @param name,
	 *            x, y
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
		main.editCrop(name, -1, main.getCropStock(name)+1);
		cash = cash - price;
		requestCheck();
	}

	/**
	 * Sells the selected crops and removes it from the board
	 * 
	 * @param name
	 *            name of crop that will be sold
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
		main.editCrop(name, -1, main.getCropStock(name)-1);
		cash = cash + price;
		requestCheck();
	}

	/**
	 * If the player doesn't have any loans at the selected lender, he will be able
	 * to sign up for an new
	 * 
	 * @param name
	 *            the name of the lender
	 */
	public void acceptLoan(String name) {
		int loanAmount = 0;
		double inter;
		if (name.equals("Bankloan")) {
			if (!bLoan.getHasLoan()) {
				while (loanAmount < bLoan.getMinLoan() || loanAmount > bLoan.getMaxLoan()) {
					loanAmount = Integer
							.parseInt(JOptionPane.showInputDialog("How much do you wanna loan? \nMinimum loan is: "
									+ bLoan.getMinLoan() + "\nMaximum loan is: " + bLoan.getMaxLoan()));
					if (loanAmount >= bLoan.getMinLoan() && loanAmount <= bLoan.getMaxLoan()) {
						setDebt(loanAmount);
						bLoan.setDebt(loanAmount);
						setCash(loanAmount);
						bLoan.setHasLoan(true);
						main.lblCheck();
						break;
					} else {
						JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
					}
				}
			}
		}
		if (name.equals("Bankloan 2")) {
			if (!bLoan2.getHasLoan()) {
				while (loanAmount < bLoan2.getMinLoan() || loanAmount > bLoan2.getMaxLoan()) {
					loanAmount = Integer
							.parseInt(JOptionPane.showInputDialog("How much do you wanna loan? \nMinimum loan is: "
									+ bLoan.getMinLoan() + "\nMaximum loan is: " + bLoan.getMaxLoan()));
					if (loanAmount >= bLoan2.getMinLoan() && loanAmount <= bLoan2.getMaxLoan()) {
						setDebt(loanAmount);
						bLoan2.setDebt(loanAmount);
						setCash(loanAmount);
						bLoan2.setHasLoan(true);
						main.lblCheck();
						break;
					} else {
						JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
					}
				}
			}
		}
		if (name.equals("Payday loan")) {
			if (!pLoan.getHasLoan()) {
				while (loanAmount < pLoan.getMinLoan() || loanAmount > pLoan.getMaxLoan()) {
					loanAmount = Integer
							.parseInt(JOptionPane.showInputDialog("How much do you wanna loan? \nMinimum loan is: "
									+ pLoan.getMinLoan() + "\nMaximum loan is: " + pLoan.getMaxLoan()));
					if (loanAmount >= pLoan.getMinLoan() && loanAmount <= pLoan.getMaxLoan()) {
						setDebt(loanAmount);
						pLoan.setDebt(loanAmount);
						setCash(loanAmount);
						pLoan.setHasLoan(true);
						main.lblCheck();
						break;
					} else {
						JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
					}
				}
			}
		}
		if (name.equals("Maffia loan")) {
			if (!mLoan.getHasLoan()) {
				while (loanAmount < mLoan.getMinLoan() || loanAmount > mLoan.getMaxLoan()) {
					loanAmount = Integer
							.parseInt(JOptionPane.showInputDialog("How much do you wanna loan? \nMinimum loan is: "
									+ mLoan.getMinLoan() + "\nMaximum loan is: " + mLoan.getMaxLoan()));
					if (loanAmount >= mLoan.getMinLoan() && loanAmount <= mLoan.getMaxLoan()) {
						setDebt(loanAmount);
						mLoan.setDebt(loanAmount);
						setCash(loanAmount);
						mLoan.setHasLoan(true);
						main.lblCheck();
						break;
					} else {
						JOptionPane.showMessageDialog(null, "We don't wanna loan you that amount. Try again!");
					}
				}
			}
		}
	}

	/**
	 * Lets the player pay off his loan if he has one
	 * 
	 * @param name
	 *            the name of the lender
	 */
	public void payOffLoan(String name) {
		int payment = 0;
		if (name.equals("Bankloan")) {
			if (bLoan.getHasLoan()) {
				while (payment == 0 || payment > bLoan.getDebt()) {
					try {
						payment = Integer.parseInt(JOptionPane.showInputDialog("Your debt to this lender is: "
								+ bLoan.getDebt() + "\nHow much do you wanna pay off?"));
					} catch (Exception e) {
					}
					if (payment > 0 && payment <= getDebt()) {
						setDebt(-payment);
						setCash(-payment);
						bLoan.setDebt(-payment);
						main.lblCheck();
						break;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have no debt to this lender");
			}
			if (bLoan.getDebt() == 0) {
				bLoan.setHasLoan(false);
			}
		}
		if (name.equals("Bankloan 2")) {
			if (bLoan2.getHasLoan()) {
				while (payment == 0 || payment > bLoan2.getDebt()) {
					try {
						payment = Integer.parseInt(JOptionPane.showInputDialog("Your debt to this lender is: "
								+ bLoan2.getDebt() + "\nHow much do you wanna pay off?"));
					} catch (Exception e) {
					}
					if (payment > 0 && payment <= getDebt()) {
						setDebt(-payment);
						setCash(-payment);
						bLoan2.setDebt(-payment);
						main.lblCheck();
						break;
					}
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
				while (payment == 0 || payment > pLoan.getDebt()) {
					try {
						payment = Integer.parseInt(JOptionPane.showInputDialog("Your debt to this lender is: "
								+ pLoan.getDebt() + "\nHow much do you wanna pay off?"));
					} catch (Exception e) {
					}
					if (payment > 0 && payment <= getDebt()) {
						setDebt(-payment);
						setCash(-payment);
						pLoan.setDebt(-payment);
						main.lblCheck();
						break;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "You have no debt to this lender");
			}
			if (pLoan.getDebt() == 0) {
				pLoan.setHasLoan(false);
			}
		}
		if (name.equals("Maffia loan")) {
			if (mLoan.getHasLoan()) {
				while (payment == 0 || payment > mLoan.getDebt()) {
					try {
						payment = Integer.parseInt(JOptionPane.showInputDialog("Your debt to this lender is: "
								+ mLoan.getDebt() + "\nHow much do you wanna pay off?"));
					} catch (Exception e) {
					}
					if (payment > 0 && payment <= getDebt()) {
						setDebt(-payment);
						setCash(-payment);
						mLoan.setDebt(-payment);
						main.lblCheck();
						break;
					}
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
	 * Not implemented yet due to problems with interest variable
	 */
	public void forcedPayment() {
		double payment = 0;
		double interest;
		double interestToPay;
		if (bLoan.getHasLoan()) {
			interest = bLoan.getInterest() / 100;
			interestToPay = (Double)bLoan.getDebt() * interest;
			try {
			payment = Double.parseDouble(JOptionPane.showInputDialog("Your payment is due! Your debt to the bank is"
					+ bLoan.getDebt() + "\nYou have to atleast pay the interest which is " + interestToPay));
		}catch(Exception e) {
			
		}
	}
	}

	/**
	 * Returns the current year
	 * 
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
	 * When called increases the week by one.
	 */
	public void setWeek() {
		this.week++;
		if (week >= 52) {
			setYear();
		}
		if (bLoan.getHasLoan()) {
			counter++;
			if (counter == 4) {
			forcedPayment();
			counter = 0;
			 }
		}
	}

	/**
	 * Returns how much cash the player has
	 * 
	 * @return cash
	 */
	public int getCash() {
		return cash;
	}

	/**
	 * Increases the players cash by the entered amount. Negative values decreases
	 * cash.
	 * 
	 * @param amount
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
		try  {
			newName = (String) JOptionPane.showInputDialog(null, "Enter your name", "Farmer you?", JOptionPane.QUESTION_MESSAGE, null, null, farmName);	
			if(newName.length()<1) {
				throw new NullPointerException();
			}
			farmName = newName;
		}
		catch (NullPointerException e) {
			if (farmName == null) {
				farmName = "Nobody";
			}
		}
		if(main!=null) {
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

	// ____________________________Methods for saving and loading the
	// game____________________________________________
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
		// saveList.add(board.getAnimalList());
		saveList.add(board.getNode());
		saveList.add(board.getBuildingList());
		saveList.add(board.getCropsList());
		new SaveGame(saveList);
	}

	/**
	 * Separates the loaded list into day, cash, animals, buildings and crops and
	 * adds them to board
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
		board.setNode((boolean[][]) loadedList.pop());
		loadedBuildings = (LinkedList) loadedList.pop();
		loadedCrops = (LinkedList) loadedList.pop();
		// Convert Animalcopy-list to ordinary AnimalList.
		loadedAnimals = restoreAnimalList(loadedAnimals);
		for (int i = 0; i < loadedBuildings.size(); i++) {
			board.addBuilding((Building) loadedBuildings.get(i));
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
		}
	}

	/**
	 * Called when loading an game. Sets up the commodity tab to the correct amount
	 * of animals
	 */
	public void setCommodityLoaded() {
		main.addCommodity("Cow", 500, nmbrOfCows, new ImageIcon("images/icons/cowIcon.png")); // Changed for demo
		main.addCommodity("Pig", 300, nmbrOfPigs, new ImageIcon("images/icons/pigIcon.png")); // Changed for demo
		main.addCommodity("Sheep", 200, nmbrOfSheeps, new ImageIcon("images/icons/sheepIcon.png")); // Changed for demo
		main.addCommodity("Chicken", 50, nmbrOfChickens, new ImageIcon("images/icons/chickenIcon.png"));

	}

	/**
	 * Called when loading an game. Sets up the Property tab to the correct amount
	 * of propertys
	 */
	public void setPropertyLoaded() {
		main.addProperty("Barn", 1000, nmbrOfBarns, new ImageIcon("images/icons/barnIcon.png")); // changed for demo
		main.addProperty("Pigsty", 1200, nmbrOfPigSty, new ImageIcon("images/icons/pigstyIcon.png"));
		main.addProperty("Stable", 1000, nmbrOfStable, new ImageIcon("images/icons/StableIcon.png"));
		main.addProperty("HenHouse", 700, nmbrOfHenHouse, new ImageIcon("images/icons/henhouseIcon.png"));
	}

	/**
	 * Called when loading an game. Sets up the crops tab to the correct amount of
	 * crops
	 */
	public void setCropsLoaded() {
		main.addCrops("Carrot", 200, nmbrOfCarrots, new ImageIcon("images/icons/carrotIcon.png"));
		main.addCrops("Corn", 300, nmbrOfCorn, new ImageIcon("images/icons/cornIcon.png"));
		main.addCrops("Oats", 100, nmbrOfOats, new ImageIcon("images/icons/oatIcon.png"));
		main.addCrops("Lettuce", 400, nmbrOfLettuce, new ImageIcon("images/icons/lettuceIcon.png"));
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
			animalCopy.setSpeed(animal.getSpeed());
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
			animal.setSpeed(copy.getSpeed());
			animal.setX_direction(copy.getX_direction());
			animal.setY_direction(copy.getY_direction());
			newAnimalList.add(animal);
		}
		return newAnimalList;
	}
}
