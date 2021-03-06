package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import event.Season;
import farm.Board;
import farm.Console;
import main.Controller;

/**
 * Main UI class that temporarily doubles as a minor controller.
 * 
 * EDIT: Most controller aspects have now been shifted over to the Controller
 * class. Ideally, no such functions should remain.
 * 
 * @author Mikael Lindfors, Max Rudander, Elin Olsson, Malin Zederfeldt,
 *         Matthias Svensson Falk.
 *
 */
public class UIMain extends JFrame implements ActionListener {
	private JPanel pnlMain = new JPanel(); 
	private JPanel pnlNorth = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlEastTabs = new JPanel();
	private JPanel pnlEastCenter = new JPanel();
	private JPanel pnlSouth = new JPanel();

	private Dimension tabDimension = new Dimension(100, 500);
	private Dimension menuDimension = new Dimension(460, 500);
	private Dimension actionDimension = new Dimension(600, 50);

	private Controller controller;
	private JScrollPane scrollBoard = new JScrollPane(new JPanel());
	private Board mainBoard;

	private JMenuBar barNorth = new JMenuBar();
	private JMenu file = new JMenu("File");
	private JMenuItem newGame = new JMenuItem("New Game");
	private JMenuItem saveGame = new JMenuItem("Save Game");
	private JMenuItem loadGame = new JMenuItem("Load Game");
	private JMenuItem mainMenu = new JMenuItem("Main Menu");
	private JMenuItem exitGame = new JMenuItem("Exit");
	private JMenuItem changeName = new JMenuItem("Change name");

	private JMenu options = new JMenu("Options");
	private JMenuItem console = new JMenuItem("Console");

	private JMenu help = new JMenu("Help");
	private JMenuItem tutorial = new JMenuItem("Tutorial");

	private JButton btnNextWeek = new JButton("Next week");
	private JButton btnExit = new JButton("Quit");
	private JButton btnMarket = new JButton("Animals");
	private JButton btnFinance = new JButton("Finance");
	private JButton btnConsole = new JButton("Console");
	private JButton btnCrops = new JButton("Crops");
	private JButton btnBuildings = new JButton("Buildnings");
	private JButton btnGoods = new JButton("Goods");

	private JLabel lblDate = new JLabel();
	private JLabel lblCash = new JLabel();
	private JLabel lblAction = new JLabel();
	private JLabel lblName = new JLabel();

	private ArrayList<Commodity> items = new ArrayList<Commodity>();
	private ArrayList<Property> props = new ArrayList<Property>();
	private ArrayList<Crops> crops = new ArrayList<Crops>();
	private ArrayList<Finance> finance = new ArrayList<Finance>();
	private ArrayList<Goods> goodsList = new ArrayList<Goods>();
	private PanelScroller ps;

	private Season season;
	private String filename = "files/tutorial.txt";

	private boolean enterKeyActivated = false;
	private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	private static final String MOVE_UP = "move up";
	private static final String MOVE_DOWN = "move down";
	private static final String ENTER = "Accept";
	private static final String MOVE_LEFT = "move left";
	private static final String MOVE_RIGHT = "move right";

	/**
	 * Constructs the UI and adds all the components into the main component
	 */
	public UIMain(Controller controller, Board mainBoard) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.controller = controller;
		this.mainBoard = mainBoard;
		season = new Season(mainBoard, this);
		pnlMain.setLayout(new BorderLayout());
		pnlNorth.setLayout(new GridLayout(1, 5));
		pnlSouth.setLayout(new BorderLayout());
		pnlEast.setLayout(new BorderLayout());
		pnlEastTabs.setLayout(new GridLayout(6, 1));
		lblCash.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheck();
		barNorth.add(file);
		barNorth.add(options);
		barNorth.add(help);
		file.add(newGame);
		file.add(saveGame);
		file.add(loadGame);
		file.add(changeName);
		file.add(mainMenu);
		file.add(exitGame);
		options.add(console);
		help.add(tutorial);
		pnlNorth.add(barNorth);
		pnlNorth.add(lblName);
		pnlNorth.add(btnNextWeek);
		pnlNorth.add(lblDate);
		pnlNorth.add(lblCash);
		pnlNorth.add(btnExit);
		pnlSouth.add(lblAction);
		pnlSouth.setPreferredSize(actionDimension);
		pnlEastTabs.setPreferredSize(tabDimension);
		pnlEastTabs.add(btnMarket);
		pnlEastTabs.add(btnCrops);
		pnlEastTabs.add(btnBuildings);
		pnlEastTabs.add(btnGoods);
		pnlEastTabs.add(btnFinance);
		pnlEastTabs.add(btnConsole);
		switchTab(pnlMarket());
		((JPanel) scrollBoard.getViewport().getView()).add(mainBoard);
		pnlEast.add(pnlEastTabs, BorderLayout.WEST);
		pnlEast.add(pnlEastCenter, BorderLayout.CENTER);
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(scrollBoard, BorderLayout.CENTER);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		setButtonListeners();
		add(pnlMain);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(true);
		setVisible(true);
		setTitle(controller.getName() + "'s Farm");
		lblName.setText(controller.getName());
		ps = new PanelScroller(mainBoard);
		scrollBoard.addMouseListener(ps);
		scrollBoard.addMouseMotionListener(ps);
		setLocationRelativeTo(null);
	}

	/**
	 * Shows the console tab if true, else it hides it. Used during set up
	 * 
	 * @param b - boolean that decides if the console should be shown
	 */
	public void showConsole(boolean b) {
		btnConsole.setEnabled(b);
	}

	/**
	 * Response to attempts at toggling the console button.
	 */
	public void toggleConsole() {
		try {
			if (btnConsole.isEnabled()) {
				btnConsole.setEnabled(false);
			} else if (JOptionPane.showInputDialog("Say the magic word!").equals("farmeryou?")) {
				btnConsole.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "AH AH AH\nYou didn't say the magic word!");
			}
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "AH AH AH\nYou didn't say the magic word!");
		}
	}

	/**
	 * Updates the labels in the northern panel with their current values.
	 */

	public void lblCheck() {
		lblDate.setText("Year: " + controller.getYear() + ", " + "Week: " + controller.getWeek());
		lblCash.setText("Cash: " + controller.getCash() + "$" + " Debt: " + controller.getDebt() + "$");
	}

	/**
	 * Creates and returns a panel for the market in the side menu.
	 * 
	 * @return the new JPanel
	 */
	public JPanel pnlMarket() {
		JPanel pnlMarket = new JPanel();
		pnlMarket.setLayout(new GridLayout(Math.max(20, items.size()), 1));
		for (int i = 0; i < items.size(); i++) {
			pnlMarket.add(items.get(i).toJPanel());
		}
		pnlMarket.setPreferredSize(menuDimension);
		return pnlMarket;
	}

	/**
	 * Creates and returns a panel for the crops in the side menu.
	 * 
	 * @return the new JPanel
	 */

	public JPanel pnlCrops() {
		JPanel pnlCrops = new JPanel();
		pnlCrops.setLayout(new GridLayout(Math.max(20, crops.size()), 1));
		for (int i = 0; i < crops.size(); i++) {
			pnlCrops.add(crops.get(i).toJPanel());
		}
		pnlCrops.setPreferredSize(menuDimension);
		return pnlCrops;
	}
	/**
	 * Creates and returns a panel for the goods in the side menu.
	 * 
	 * @return the new JPanel
	 */
	public JPanel pnlGoods() {
		JPanel pnlGoods = new JPanel();
		pnlGoods.setLayout(new GridLayout(Math.max(20, goodsList.size()), 1));
		for (int i = 0; i < goodsList.size(); i++) {
			pnlGoods.add(goodsList.get(i).toJPanel());
		}
		pnlGoods.setPreferredSize(menuDimension);
		return pnlGoods;
	}


	/**
	 * Creates and returns a panel for the buildings in the side menu.
	 * 
	 * @return the new JPanel
	 */
	public JPanel pnlBuildings() {
		JPanel pnlBuildings = new JPanel();
		pnlBuildings.setLayout(new GridLayout(Math.max(20, props.size()), 1));
		for (int i = 0; i < props.size(); i++) {
			pnlBuildings.add(props.get(i).toJPanel());
		}
		pnlBuildings.setPreferredSize(menuDimension);
		return pnlBuildings;
	}
	/**
	 * Creates and returns a panel for finance in the side menu.
	 * 
	 * @return the new JPanel
	 */
	private JPanel pnlFinance() {
		JPanel pnlFinance = new JPanel();
		pnlFinance.setLayout(new GridLayout(Math.max(20, finance.size()), 1));
		for (int i = 0; i < finance.size(); i++) {
			pnlFinance.add(finance.get(i).pnlAmounts());
			pnlFinance.add(finance.get(i).toJPanel());
		}
		pnlFinance.setPreferredSize(menuDimension);
		return pnlFinance;
	}

	/**
	 * Creates and returns a panel for the console in the side menu.
	 * 
	 * @return the new JPanel
	 */
	private JPanel pnlConsole() {
		JPanel pnlConsole = new Console(this, mainBoard);
		pnlConsole.setPreferredSize(menuDimension);
		return pnlConsole;
	}

	/**
	 * An over complicated way to add listeners to the buttons.
	 */
	public void setButtonListeners() {
		JButton button;
		for (int i = 0; i < pnlNorth.getComponentCount(); i++) {
			if (pnlNorth.getComponent(i) instanceof JButton) {
				button = (JButton) pnlNorth.getComponent(i);
				button.addActionListener(this);
				button.setFocusPainted(false);
				button.setFont(new Font("Serif", Font.BOLD, 12));
			}
		}
		for (int i = 0; i < barNorth.getMenuCount(); i++) {
			for (int j = 0; j < barNorth.getMenu(i).getItemCount(); j++) {
				barNorth.getMenu(i).getItem(j).addActionListener(this);
			}
		}
		for (int i = 0; i < pnlEastTabs.getComponentCount(); i++) {
			if (pnlEastTabs.getComponent(i) instanceof JButton) {
				button = (JButton) pnlEastTabs.getComponent(i);
				button.addActionListener(this);
				button.setToolTipText("Opens the " + button.getText() + " panel!");
				button.setFocusPainted(false);
				button.setFont(new Font("Serif", Font.BOLD, 14));
			}
		}
	}

	/**
	 * sets the text to the received string in pnlAction
	 * 
	 * @param action the string that will been set in lblAction
	 */
	public void setLblAction(String action) {
		lblAction.setText(action);
	}

	/**
	 * Switches the panel to be shown in the side menu.
	 * 
	 * @param panel The panel to be shown.
	 */
	public void switchTab(JPanel panel) {
		pnlEastCenter.removeAll();
		pnlEastCenter.add(panel);
		pnlEastCenter.revalidate();
	}

	/**
	 * Method that compares the prices to the available funds. Enables or disables
	 * the buy & sell buttons after stock and required funds.
	 */
	public void marketCheck() {
		Class<?> animal;
		int stock;
		int cap;
		for (int i = 0; i < items.size(); i++) {
			stock = items.get(i).getStock();
			try {
				animal = Class.forName("commodity." + items.get(i).getType());
				cap = (int) animal.getMethod("getCapacity").invoke(null);
			} catch (Exception e) {
				cap = -1;
			}

			if (controller.getCash() < items.get(i).price || stock >= cap) {
				items.get(i).btnBuy.setEnabled(false);
				items.get(i).btnSlaughter.setEnabled(false);
			} else {
				items.get(i).btnBuy.setEnabled(true);
				items.get(i).btnSlaughter.setEnabled(true);
			}
			if (items.get(i).stock > 0) {
				items.get(i).btnSell.setEnabled(true);
				items.get(i).btnSlaughter.setEnabled(true);
			} else {
				items.get(i).btnSell.setEnabled(false);
				items.get(i).btnSlaughter.setEnabled(false);
			}
		}
		lblCheck();
	}
	/**
	 * Method that compares the prices to the available funds. Enables or disables
	 * the buy & sell buttons after stock and required funds.
	 */
	public void goodsCheck() {
		for (int i = 0; i < goodsList.size(); i++) {
			if (goodsList.get(i).stock > 0) {
				goodsList.get(i).btnSell.setEnabled(true);
				;
			} else {
				goodsList.get(i).btnSell.setEnabled(false);
			}
		}
		lblCheck();
	}
	/**
	 * When called adds an new good
	 * @param name the goods name
	 * @param price the goods price
	 * @param stock the stock of goods
	 * @param icon the goods picture
	 */
	public void addGoods(String name, int price, int stock, Icon icon) {
		new Goods(name, price, stock, icon);
	}
	/**
	 * a method that returns the stock of a specified good
	 * @param name the name of the good
	 * @return the stock of the specified good
	 */
	public int getGoodsStock(String name) {
		Goods goods;
		for (int i = 0; i < goodsList.size(); i++) {
			goods = goodsList.get(i);
			if (goods.lblGoodsName.getText().equals(name)) {
				return goods.getStock();
			}
		}
		return -1;
	}
	/**
	 * Method that changes the status of a Good.
	 * 
	 * @param name - The name of the Good to be changed.
	 * @param price - The new price. Set negative value for unchanged.
	 * @param stock - The new stock. Set negative value for unchanged.
	 */
	public void editGoods(String name, int price, int stock) {
		Goods goods;
		for (int i = 0; i < goodsList.size(); i++) {
			goods = goodsList.get(i);
			if (goods.lblGoodsName.getText().equals(name)) {
				if (price >= 0) {
					goods.setPrice(price);
				}
				if (stock >= 0) {
					if (goods.getStock()<stock) lblAction.setText("Congrats, you've slaughtered an animal. You now have " + stock + " " + name);
					goods.setStock(stock);
				}
			}
		}
	}
	//Kodgranskning: kontrollera variabel animal
	/**
	 * Method that compares the prices to the available funds. Enables or disables
	 * the buy & sell buttons after stock and required funds.
	 */
	public void propertyCheck() {
		Class<?> building;
//		Class<?> animal;
		int stock;
		int singleCap;
		int totalCap;
		for (int i = 0; i < props.size(); i++) {
			stock = props.get(i).getStock();
			try {
				building = Class.forName("property." + props.get(i).getType());
//				animal = Class.forName("commodity." + (String) building.getMethod("getOccupant").invoke(null)); 
				singleCap = (int) building.getMethod("getCapacity").invoke(null);
				totalCap = singleCap * stock;
			} catch (Exception e) {
				singleCap = -1;
				totalCap = -1;
			}
			if (controller.getCash() < props.get(i).price) {
				props.get(i).btnBuy.setEnabled(false);
			} else {
				props.get(i).btnBuy.setEnabled(true);
			}
			if (props.get(i).stock > 0 && items.get(i).stock <= totalCap - singleCap) {
				props.get(i).btnSell.setEnabled(true);
			} else {
				props.get(i).btnSell.setEnabled(false);
			}
		}
		lblCheck();
	}

	/**
	 * Method that compares the prices to the available funds. Enables or disables
	 * the buy & sell buttons after stock and required funds.
	 */
	public void cropsCheck() {
		for (int i = 0; i < crops.size(); i++) {
			if (controller.getCash() < crops.get(i).price) {
				crops.get(i).btnBuy.setEnabled(false);
			} else {
				crops.get(i).btnBuy.setEnabled(true);
			}
			if (crops.get(i).stock > 0) {
				crops.get(i).btnSell.setEnabled(true);
				;
			} else {
				crops.get(i).btnSell.setEnabled(false);
			}
		}
		lblCheck();
	}

	/**
	 * When called adds an new Commodity
	 * 
	 * @param name the Commodity's name.
	 * @param price the Commodity's price.
	 * @param stock sets the total stock of the Commodity owned by player.
	 * @param icon the Commodity's icn
	 */
	public void addCommodity(String name, int price, int stock, Icon icon) {
		new Commodity(name, price, stock, icon);
	}

	/**
	 * Method that changes the status of a Commodity.
	 * 
	 * @param name - The name of the Commodity to be changed.
	 * @param price - The new price. Set negative value for unchanged.
	 * @param stock - The new stock. Set negative value for unchanged.
	 */
	public void editCommodity(String name, int price, int stock) {
		Commodity com;
		for (int i = 0; i < items.size(); i++) {
			com = items.get(i);
			if (com.lblComName.getText().equals(name)) {
				if (price >= 0) {
					com.setPrice(price);
				}
				if (stock >= 0) {
					com.setStock(stock);
				}
			}
		}
	}
	/**
	 * a method that returns the stock of a specified animal
	 * @param name the name of the animal
	 * @return the stock of the specified animal
	 */
	public int getCommodityStock(String name) {
		Commodity com;
		for (int i = 0; i < items.size(); i++) {
			com = items.get(i);
			if (com.lblComName.getText().equals(name)) {
				return com.getStock();
			}
		}
		return -1;
	}

	/**
	 * When called adds a new Property
	 * 
	 * @param name - the Property's name.
	 * @param price - the Property's price.
	 * @param stock - sets the total stock of the Property owned by the player.
	 * @param icon - the Property's Icon.
	 */
	public void addProperty(String name, int price, int stock, Icon icon) {
		new Property(name, price, stock, icon);
	}
	/**
	 * Method that changes the status of a building
	 * 
	 * @param name - The name of the building to be changed.
	 * @param price - The new price. Set negative value for unchanged.
	 * @param stock - The new stock. Set negative value for unchanged.
	 */
	public void editProperty(String name, int price, int stock) {
		Property prop;
		for (int i = 0; i < props.size(); i++) {
			prop = props.get(i);
			if (prop.lblPropName.getText().equals(name)) {
				if (price >= 0) {
					prop.setPrice(price);
				}
				if (stock >= 0) {
					prop.setStock(stock);
				}
			}
		}
	}
	/**
	 * a method that returns the stock of a specified building
	 * @param name the name of the building
	 * @return the stock of the specified building
	 */
	public int getPropertyStock(String name) {
		Property prop;
		for (int i = 0; i < props.size(); i++) {
			prop = props.get(i);
			if (prop.lblPropName.getText().equals(name)) {
				return prop.getStock();
			}
		}
		return -1;
	}
	/** When called, adds a new Crop
	 * 
	 * @param name the name of the specified crop
	 * @param price the price of the specified crop
	 * @param stock the stock of specified crop
	 * @param icon the image of the specified crop
	 */
	public void addCrops(String name, int price, int stock, Icon icon) {
		new Crops(name, price, stock, icon);
	}
	/**
	 * Method that changes the status of a Crop
	 * 
	 * @param name - The name of the Crop to be changed.
	 * @param price - The new price. Set negative value for unchanged.
	 * @param stock - The new stock. Set negative value for unchanged.
	 */
	public void editCrop(String name, int price, int stock) {
		Crops crop;
		for (int i = 0; i < crops.size(); i++) {
			crop = crops.get(i);
			if (crop.lblCropsName.getText().equals(name)) {
				if (price >= 0) {
					crop.setPrice(price);
				}
				if (stock >= 0) {
					crop.setStock(stock);
				}
			}
		}
	}
	/**
	 * a method that returns the stock of a specified crop
	 * @param name the name of the crop
	 * @return the stock of the specified crop
	 */
	public int getCropStock(String name) {
		Crops crop;
		for (int i = 0; i < crops.size(); i++) {
			crop = crops.get(i);
			if (crop.lblCropsName.getText().equals(name)) {
				return crop.getStock();
			}
		}
		return -1;
	}

	/**
	 * When called, adds an new finance
	 * 
	 * @param name The lenders name
	 * @param interest The lenders interest
	 * @param minLoan the minimum loan for the lender
	 * @param maxLoan the maximum loan for the lender
	 */
	public void addFinance(String name, double interest, int minLoan, int maxLoan, ImageIcon icon) {
		new Finance(name, interest, minLoan, maxLoan, icon);
	}

	/**
	 * Triggers various methods when buttons are pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newGame) {
			controller.newGame(false, controller.getEnvironment());
		}
		if (e.getSource() == mainMenu) {
			controller.quit();
		}
		if (e.getSource() == exitGame) {
			controller.exit();
		}
		if (e.getSource() == saveGame) {
			controller.saveGame();
		}
		if (e.getSource() == loadGame) {
			controller.loadedGame(false);
		}
		if (e.getSource() == changeName) {
			controller.setName();
			lblName.setText(controller.getName());
		}
		if (e.getSource() == console) {
			toggleConsole();
		}
		if (e.getSource() == tutorial) {
			new UITutorial(filename);
		}
		if (e.getSource() == btnMarket) {
			switchTab(pnlMarket());
		}
		if (e.getSource() == btnCrops) {
			switchTab(pnlCrops());
		}
		if (e.getSource() == btnBuildings) {
			switchTab(pnlBuildings());
		}
		if (e.getSource() == btnGoods) {
			switchTab(pnlGoods());
		}
		if (e.getSource() == btnFinance) {
			switchTab(pnlFinance());
		}
		if (e.getSource() == btnConsole) {
			switchTab(pnlConsole());
		}
		if (e.getSource() == btnNextWeek) {
			if (controller.getCash() < 0) {
				if (gameOverWarning("You're out of cash!\n"
						+ "Proceeding will make you lose the game\n"
						+ "Do you want to proceed??")) {

				}
				else {
					JOptionPane.showMessageDialog(null, "Good choice!\n\n"
							+ "To postpone bankruptcy, take loans and sell whatever isn't nailed down.");
					return;
				}
			}
			controller.endTurn();
			controller.setWeek();
			marketCheck();
			season.setSeason(controller.getWeek());
		}
		if (e.getSource() == btnExit) {
			controller.exit();
		}
	}
	/**
	 * Warns to user that proceeding will result in game over.
	 * @return true if the user proceeds anyway, false if they restrain themselves
	 */
	public boolean gameOverWarning (String message) {
		if (JOptionPane.showConfirmDialog(null, message, "DangerZone!", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Method used for setting keybindings for the building menu.
	 * @param type Type of building that is being bought.
	 * @param price Price of building
	 * @param markerSize Size of the marker for the building.
	 */
	public void initilizeKeyBindings(String type, int price, int markerSize) {
		mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
		mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
		mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
		mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
		mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("ENTER"), ENTER);
		mainBoard.getActionMap().put(MOVE_UP, (Action) new MoveAction(0, -1));
		mainBoard.getActionMap().put(MOVE_DOWN, (Action) new MoveAction(0, 1));
		mainBoard.getActionMap().put(MOVE_LEFT, (Action) new MoveAction(-1, 0));
		mainBoard.getActionMap().put(MOVE_RIGHT, (Action) new MoveAction(1, 0));
		mainBoard.getActionMap().put(ENTER, (Action) new Accept(type, price, markerSize));
	}
	/**
	 * Private class used only for moving the marker on the board
	 * @author Mikael Lindfors
	 *
	 */
	private class MoveAction extends AbstractAction {
		private int x;
		private int y;
		/**
		 * Constructor that receives x and y direction.
		 * @param x x-direction
		 * @param y y-direction
		 */
		private MoveAction(int x, int y) {
			this.x = x;
			this.y = y;
		}
		/**
		 * moves the marker when keybindings are pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mainBoard.moveMarker(x, y);
		}
	}
	/**
	 * Private class used only for placing a building on the current
	 * marker-coordinates.
	 * @author Mikael Lindfors
	 *
	 */
	private class Accept extends AbstractAction {
		private String type;
		private int price;
		private int markerSize;
		/**
		 * Constructor used for setting the buildtype, price and markersize
		 * @param type type of Building
		 * @param price price of the Building
		 * @param markerSize size of the marker.
		 */
		private Accept(String type, int price, int markerSize) {
			this.type = type;
			this.price = price;
			this.markerSize = markerSize;
		}
		/**
		 * accepts the markers coordinate as a build-position unless returned 
		 * coordinates is -1. After purchase grid, marker and enter-key will be turned off.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (enterKeyActivated) {
				int[] coords = mainBoard.accept(markerSize);
				if (coords[0] != -1) {
					controller.buyProperty(type, price, coords[0], coords[1]);
					mainBoard.grid(false);
					mainBoard.setMarker(false);
					enterKeyActivated = false;
				}
			}
		}
	}

	/**
	 * Inner class that enables the player to scroll the board by "drag & drop".
	 * 
	 * @author Max Rudander
	 *
	 */
	private class PanelScroller extends MouseAdapter {
		private Point origin;
		private JPanel panel;
		private JViewport viewPort;
		private Rectangle view;

		/**
		 * Constructor that stores a panel which itself
		 * 
		 * @param panel - 
		 */
		public PanelScroller(JPanel panel) {
			this.panel = panel;
			viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, panel);
		}

		/**
		 * sets the origin variables point to the point where the mouse was pressed
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			origin = new Point(e.getPoint());
		}

		/**
		 * Method that calculates between the points that the mouse has been dragged
		 */
		@Override
		public void mouseDragged(MouseEvent e) {
			

			int deltaX = origin.x - e.getX();
			int deltaY = origin.y - e.getY();

			view = viewPort.getViewRect();
			view.x += deltaX;
			view.y += deltaY;

			panel.scrollRectToVisible(view);
				
		}
	}

	/**
	 * Inner class that handles commodities for the market.
	 * 
	 * @author Max Rudander
	 */
	private class Commodity implements ActionListener {
		private JLabel lblComName = new JLabel();
		private JLabel lblComImage = new JLabel();
		private JLabel lblComStock = new JLabel();
		private JLabel lblComPrice = new JLabel();
		private JButton btnBuy = new JButton("Buy!");
		private JButton btnSell = new JButton("Sell!");
		private JButton btnSlaughter = new JButton("Slaughter");
		private int price;
		private int stock;

		/**
		 * Constructor for the Commodity. Also adds it to a list of Commodities for
		 * future use.
		 * 
		 * @param name the commodity's name.
		 * @param price the commodity's value.
		 */

		public Commodity(String name, int price, int stock, Icon icon) {
			lblComName.setText(name);
			lblComImage.setIcon(icon);
			btnSlaughter.setForeground(Color.red);
			setPrice(price);
			setStock(stock);
			btnBuy.addActionListener(this);
			btnSell.addActionListener(this);
			btnSlaughter.addActionListener(this);
			items.add(this);
			marketCheck();
		}

		/**
		 * Returns the price of the commodity.
		 * 
		 * @return - the price.
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * Sets the price of the commodity, updates the label and performs a market
		 * check.
		 * 
		 * @param price - the new price.
		 */
		public void setPrice(int price) {
			this.price = price;
			lblComPrice.setText("$" + price);
			marketCheck();
		}

		/**
		 * Returns the stock of the commodity.
		 * 
		 * @return - the stock.
		 */
		public int getStock() {
			return stock;
		}

		/**
		 * Sets the stock of the commodity, updates the label and performs a market
		 * check.
		 * 
		 * @param stock
		 */
		public void setStock(int stock) {
			this.stock = stock;
			lblComStock.setText("#" + stock);
			marketCheck();

		}

		/**
		 * gets the type of commodity from the JLabel
		 * 
		 * @return the type of commodity
		 */
		public String getType() {
			return lblComName.getText();
		}

		/**
		 * Creates and returns a panel containing all information about a commodity.
		 * 
		 * @return the created panel
		 */
		public JPanel toJPanel() {
			JPanel panel = new JPanel(new GridLayout(1, 6));
			panel.add(lblComName);
			panel.add(lblComImage);
			panel.add(lblComPrice);
			panel.add(lblComStock);
			panel.add(btnBuy);
			panel.add(btnSell);
			panel.add(btnSlaughter);
			btnSlaughter.setFont(new Font("", Font.BOLD, 9));
			return panel;
		}

		/**
		 * Handles the buying,selling of the commodity.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnBuy)) {
				controller.buyCommodity(lblComName.getText(), getPrice());
			} else if (e.getSource().equals(btnSell)) {
				controller.sellCommodity(lblComName.getText(), getPrice());
			lblAction.setText("You have " + stock + " " + lblComName.getText() + "(s)! \n Your remaining funds: "
					+ controller.getCash() + "$!");
		} else if (e.getSource().equals(btnSlaughter)) {
			controller.slaughterCommodity(lblComName.getText());	
		}
	}
	}

	/**
	 * Inner class that handles properties for buildings
	 * 
	 * @author Malin Zederfeldt, Mikael Lindfors
	 *
	 */

	private class Property implements ActionListener {
		private JLabel lblPropName = new JLabel();
		private JLabel lblPropImage = new JLabel();
		private JLabel lblPropPrice = new JLabel();
		private JLabel lblPropStock = new JLabel();
		private JButton btnBuy = new JButton("Buy!");
		private JButton btnSell = new JButton("Sell!");
		private int price;
		private int stock;

		/**
		 * Constructor for property
		 * 
		 * @param name property's name
		 * @param price property's price
		 * @param stock current stock of property
		 * @param icon image for property
		 */
		public Property(String name, int price, int stock, Icon icon) {
			lblPropName.setText(name);
			lblPropImage.setIcon(icon);
			setPrice(price);
			setStock(stock);
			btnBuy.addActionListener(this);
			btnSell.addActionListener(this);
			props.add(this);
			propertyCheck();
		}
		/**
		 * @return the name of the building
		 */
		public String getType() {
			return lblPropName.getText();
		}

		/**
		 * Gets price of property
		 * 
		 * @return price
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * Sets price for property
		 * 
		 * @param price current price
		 */
		public void setPrice(int price) {
			this.price = price;
			lblPropPrice.setText("$" + price); // set Label
			propertyCheck(); // checks property market
		}

		/**
		 * Gets current stock
		 * 
		 * @return stock
		 */
		public int getStock() {
			return stock;
		}

		/**
		 * Sets stock for property
		 * 
		 * @param stock current stock
		 */
		public void setStock(int stock) {
			this.stock = stock;
			lblPropStock.setText("#" + stock); 
			propertyCheck(); 
		}

		/**
		 * Returns panel with information for building market
		 * 
		 * @return panel
		 */
		public JPanel toJPanel() {
			JPanel panel = new JPanel(new GridLayout(1, 6));
			panel.add(lblPropName);
			panel.add(lblPropImage);
			panel.add(lblPropPrice);
			panel.add(lblPropStock);
			panel.add(btnBuy);
			panel.add(btnSell);
			return panel;
		}

		/**
		 * handles buying and selling the properties
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnBuy)) {
				mainBoard.grid(true);
				mainBoard.setMarker(true);
				mainBoard.setMarkerSize(4);
				enterKeyActivated = true;
				initilizeKeyBindings(lblPropName.getText(), getPrice(), 4);
			} else if (e.getSource().equals(btnSell)) {
				controller.sellProperty(lblPropName.getText(), getPrice());
			}
			lblAction.setText("You have " + stock + " " + lblPropName.getText() + "(s)! \n Your remaining funds: "
					+ controller.getCash() + "$!");

		}
	}
	/**
	 * Inner class Goods
	 * 
	 * @author Elin Olsson
	 *
	 */
	private class Goods implements ActionListener {
		private JLabel lblGoodsName = new JLabel();
		private JLabel lblGoodsImage = new JLabel();
		private JLabel lblGoodsPrice = new JLabel();
		private JLabel lblGoodsStock = new JLabel();
		private JButton btnSell = new JButton("Sell!");
		private int price;
		private int stock;

		/**
		 * Constructor for goods
		 * 
		 * @param name
		 *        goods's name
		 * @param price
		 *            goods price
		 * @param stock
		 *            current stock of goods
		 * @param icon
		 *            image for the goods
		 */
		public Goods(String name, int price, int stock, Icon icon) {
			lblGoodsName.setText(name);
			lblGoodsImage.setIcon(icon);
			setPrice(price);
			setStock(stock);
			btnSell.addActionListener(this);
			goodsList.add(this);
			goodsCheck();
		}

		/**
		 * Gets price of goods
		 * 
		 * @return price
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * Sets price for goods
		 *
		 * @param price
		 *            current price
		 */
		public void setPrice(int price) {
			this.price = price;
			lblGoodsPrice.setText("$" + price); 
			goodsCheck(); 

		}

		/**
		 * Gets current stock
		 * 
		 * @return stock
		 */
		public int getStock() {
			return stock;
		}

		/**
		 * Sets stock for goods
		 * 
		 * @param stock
		 *            current stock
		 */
		public void setStock(int stock) {
			this.stock = stock;
			lblGoodsStock.setText("#" + stock); 
			goodsCheck(); 
		}

		/**
		 * sets up the JPanel which hold the different goods
		 * 
		 * @return the JPanel 
		 */
		public JPanel toJPanel() {
			JPanel panel = new JPanel(new GridLayout(1, 6));
			panel.add(lblGoodsName);
			panel.add(lblGoodsImage);
			panel.add(lblGoodsPrice);
			panel.add(lblGoodsStock);
			panel.add(btnSell);
			return panel;

		}
		/**
		 * ActionListener for the buttons
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnSell)) {
				controller.sellGoods(lblGoodsName.getText(), getPrice());
			}
			goodsCheck();
			lblAction.setText("You have " + stock + " " + lblGoodsName.getText() + "(s)! \n Your remaining funds: "
					+ controller.getCash() + "$!");
		}
	}


	/**
	 * inner class that handles crops
	 * 
	 * @author Elin Olsson, Mikael Lindfors
	 *
	 */
	private class Crops implements ActionListener {
		private JLabel lblCropsName = new JLabel();
		private JLabel lblCropsImage = new JLabel();
		private JLabel lblCropsPrice = new JLabel();
		private JLabel lblCropsStock = new JLabel();
		private JButton btnBuy = new JButton("Buy!");
		private JButton btnSell = new JButton("Sell!");
		private int price;
		private int stock;
		/**
		 * Constructor for crops
		 * 
		 * @param name crops's name
		 * @param price crops price
		 * @param stock current stock of crops
		 * @param icon image for the crops
		 */
		public Crops(String name, int price, int stock, Icon icon) {
			lblCropsName.setText(name);
			lblCropsImage.setIcon(icon);
			setPrice(price);
			setStock(stock);
			btnBuy.addActionListener(this);
			btnSell.addActionListener(this);
			crops.add(this);
			cropsCheck();
		}

		/**
		 * Gets price of crop
		 * 
		 * @return price
		 */
		public int getPrice() {
			return price;
		}

		/**
		 * Sets price for crops
		 * 
		 * @param price current price
		 */
		public void setPrice(int price) {
			this.price = price;
			lblCropsPrice.setText("$" + price); 
			cropsCheck(); 
		}

		/**
		 * Gets current stock
		 * 
		 * @return stock
		 */
		public int getStock() {
			return stock;
		}

		/**
		 * Sets stock for crops
		 * 
		 * @param stock current stock
		 */
		public void setStock(int stock) {
			this.stock = stock;
			lblCropsStock.setText("#" + stock); 
			cropsCheck(); 
		}

		/**
		 * sets up the JPanel which hold the different crops
		 * 
		 * @return the JPanel
		 */
		public JPanel toJPanel() {
			JPanel panel = new JPanel(new GridLayout(1, 6));
			panel.add(lblCropsName);
			panel.add(lblCropsImage);
			panel.add(lblCropsPrice);
			panel.add(lblCropsStock);
			panel.add(btnBuy);
			panel.add(btnSell);
			return panel;

		}
		/**
		 * The actionlistener for the buttons
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnBuy)) {
				mainBoard.grid(true);
				mainBoard.setMarker(true);
				mainBoard.setMarkerSize(1);
				enterKeyActivated = true;
				initilizeKeyBindings(lblCropsName.getText(), getPrice(), 1);
			} else if (e.getSource().equals(btnSell)) {
				controller.sellCrops(lblCropsName.getText(), getPrice());
			}
			propertyCheck();
			lblAction.setText("You have " + stock + " " + lblCropsName.getText() + "(s)! \n Your remaining funds: "
					+ controller.getCash() + "$!");

		}
		/**
		 * Method used for setting keybindings for the crops menu.
		 * @param type Type of building that is being bought.
		 * @param price Price of building
		 * @param markerSize Size of the marker for the building.
		 */
		public void initilizeKeyBindings(String type, int price, int markerSize) {
			mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
			mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
			mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
			mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);
			mainBoard.getInputMap(IFW).put(KeyStroke.getKeyStroke("ENTER"), ENTER);
			mainBoard.getActionMap().put(MOVE_UP, (Action) new MoveAction(0, -1));
			mainBoard.getActionMap().put(MOVE_DOWN, (Action) new MoveAction(0, 1));
			mainBoard.getActionMap().put(MOVE_LEFT, (Action) new MoveAction(-1, 0));
			mainBoard.getActionMap().put(MOVE_RIGHT, (Action) new MoveAction(1, 0));
			mainBoard.getActionMap().put(ENTER, (Action) new Accept(type, price, markerSize));
		}
		/**
		 * Private class used only for moving the marker on the board
		 * @author Mikael Lindfors
		 */
		private class MoveAction extends AbstractAction {
			private int x;
			private int y;
			/**
			 * Constructor that receives x and y direction.
			 * @param x x-direction
			 * @param y y-direction
			 */
			private MoveAction(int x, int y) {
				this.x = x;
				this.y = y;
			}
			/**
			 * moves the marker when keybindings are pressed.
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainBoard.moveMarker(x, y);
			}
		}
		/**
		 * Private class used only for placing a crop on the current
		 * marker-coordinates.
		 * @author Mikael Lindfors
		 *
		 */
		private class Accept extends AbstractAction {
			private String type;
			private int price;
			private int markerSize;
			/**
			 * Constructor used for setting the croptype, price and markersize
			 * @param type type of Crop
			 * @param price price of the Building
			 * @param markerSize size of the marker.
			 */
			private Accept(String type, int price, int markerSize) {
				this.type = type;
				this.price = price;
				this.markerSize = markerSize;
			}
			/**
			 * accepts the markers coordinate as a build-position unless returned 
			 * coordinates is -1. After purchase grid, marker and enter-key will be turned off.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (enterKeyActivated) {
					int[] coords = mainBoard.accept(markerSize);
					if (coords[0] != -1) {
						controller.buyCrops(type, price, coords[0], coords[1]);
						mainBoard.grid(false);
						mainBoard.setMarker(false);
						enterKeyActivated = false;
					}
				}
			}

		}
	}

	/**
	 * Inner class that handles the different lender options
	 * 
	 * @author Matthias Falk.
	 *
	 */
	private class Finance implements ActionListener {
		private JLabel lblLoanName = new JLabel();
		private JLabel lblLoanImage = new JLabel();
		private JLabel lblAmounts = new JLabel();
		private JButton btnAcceptLoan = new JButton("Take loan");
		private JButton btnPayOffLoan = new JButton("Pay off loan");
		private JTextField txtAmount = new JTextField("Enter amount..");

		/**
		 * Constructor which sets up the labels and add actionlisteners.
		 * 
		 * @param name the name of the lender
		 * @param interest the interest for the loan
		 * @param minLoan the minimum loan
		 * @param maxLoan the maximum loan
		 */
		public Finance(String name, double interest, int minLoan, int maxLoan, ImageIcon icon) {
			lblLoanName.setText(name);
			lblLoanImage.setIcon(icon);
			finance.add(this);
			btnAcceptLoan.addActionListener(this);
			btnPayOffLoan.addActionListener(this);
			lblAmounts.setText(name + " will lend you an amount between " + minLoan + " and " + maxLoan
					+ ". Interest rate: " + interest + "%");
			lblAmounts.setFont(new Font("", Font.BOLD, 11));
			setListenerTxtField();
		}

		/**
		 * sets up the JPanel for one lender option
		 * 
		 * @return the JPanel
		 */
		public JPanel toJPanel() {
			JPanel pnl = new JPanel(new GridLayout(1, 5));
			pnl.add(lblLoanImage);
			pnl.add(lblLoanName);
			pnl.add(txtAmount);
			pnl.add(btnAcceptLoan);
			pnl.add(btnPayOffLoan);
			return pnl;
		}
		/**
		 * Sets up the panel that holds the lblAmounts which contains text with information about the lender
		 * @return pnl The panel
		 */
		public JPanel pnlAmounts() {
			JPanel pnl = new JPanel();
			pnl.add(lblAmounts);
			return pnl;
		}
		/**
		 * Sets up an listener for the textfield where the player feeds in the amount they want to lend/pay off
		 */
		public void setListenerTxtField() {
			txtAmount.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					txtAmount.setText("");
				}

				@Override
				public void focusLost(FocusEvent arg0) {

				}
			});
		}

		/**
		 * the actionperformed method for the buttons. Gets the int that the player has made as input in the txtAmount and saves it in amount.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int amount;
			try {
				amount = Integer.parseInt(txtAmount.getText());
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Please enter something");
				return;
			}
			if (e.getSource() == btnAcceptLoan) {
				controller.acceptLoan(lblLoanName.getText(), amount);
				txtAmount.setText("Enter amount..");
			}
			if (e.getSource() == btnPayOffLoan) {
				controller.payOffLoan(lblLoanName.getText(), amount);
				txtAmount.setText("Enter amount..");
			}
		}
	}
}