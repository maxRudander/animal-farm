package farm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


public class UIMain extends JFrame implements ActionListener {
	private JPanel pnlMain = new JPanel();

	private JPanel pnlNorth = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlEastTabs = new JPanel();
	private JPanel pnlEastCenter = new JPanel();

	private Dimension tabDimension = new Dimension (140,500);
	private Dimension menuDimension = new Dimension (460,500);

	private Board mainBoard = new Board();

	private JButton btnNextDay = new JButton("Nästa Dag");
	private JButton btnExit = new JButton("Avsluta");
	private JButton btnMarket = new JButton("Marknad");
	private JButton btnFinance = new JButton("Finans");

	private JLabel lblDate = new JLabel();
	private JLabel lblCash = new JLabel();

	private ArrayList<Commodity> items = new ArrayList<Commodity>();
	private int day = 1;
	private int cash = 1500;
	private Random ran = new Random();

	public UIMain() {
		new Commodity("Cow", 500);
		new Commodity("Pig", 300);
		new Commodity("Horse", 2000);
		new Commodity("Sheep", 200);
		new Commodity("Chicken", 50);

		pnlMain.setLayout(new BorderLayout());
		pnlNorth.setLayout(new GridLayout (1,4));
		pnlEast.setLayout(new BorderLayout());
		pnlEastTabs.setLayout(new GridLayout (2,1));
		lblCash.setHorizontalAlignment(SwingConstants.CENTER);
		lblCash.setText("$ "+cash);
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setText("Dag: "+day);

		pnlNorth.add(btnNextDay);
		pnlNorth.add(lblDate);
		pnlNorth.add(lblCash);
		pnlNorth.add(btnExit);

		pnlEastTabs.setPreferredSize(tabDimension);
		pnlEastTabs.add(btnMarket);
		pnlEastTabs.add(btnFinance);
		switchTab(pnlMarket());

		pnlEast.add(pnlEastTabs, BorderLayout.WEST);
		pnlEast.add(pnlEastCenter, BorderLayout.CENTER);

		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(mainBoard, BorderLayout.CENTER);
		pnlMain.add(pnlEast, BorderLayout.EAST);

		setButtonListeners();
		add(pnlMain);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);

	}
	public JPanel pnlMarket () {
		JPanel pnlMarket = new JPanel();

		pnlMarket.setLayout(new GridLayout (Math.max(20, items.size()),1));
		for  (int i = 0; i<items.size(); i++) {
			pnlMarket.add(items.get(i).toJPanel());
		}

		pnlMarket.setPreferredSize(menuDimension);
		return pnlMarket;
	}
	private JPanel pnlFinance() {
		JPanel pnlFinance = new JPanel();

		pnlFinance.add(new JLabel("Hello!"));

		pnlFinance.setPreferredSize(menuDimension);
		return pnlFinance;
	}
	public void setButtonListeners() {
		JButton button;
		for (int i = 0; i < pnlNorth.getComponentCount(); i++) {
			if (pnlNorth.getComponent(i) instanceof JButton) {
				button= (JButton) pnlNorth.getComponent(i);
				button.addActionListener(this);
			}
		}
		for (int i = 0; i < pnlEastTabs.getComponentCount(); i++) {
			if (pnlEastTabs.getComponent(i) instanceof JButton) {
				button= (JButton) pnlEastTabs.getComponent(i);
				button.addActionListener(this);
			}
		}
	}

	public void switchTab (JPanel panel) {
		pnlEastCenter.removeAll();
		pnlEastCenter.add(panel);
		pnlEastCenter.revalidate();
	}
	public void marketCheck () {
		for (int i = 0; i < items.size(); i++) {
			if (cash < items.get(i).price) {
				items.get(i).btnBuy.setEnabled(false);
			}
			else {
				items.get(i).btnBuy.setEnabled(true);
			}
			if (items.get(i).stock > 0) {
				items.get(i).btnSell.setEnabled(true);;
			}
			else {
				items.get(i).btnSell.setEnabled(false);
			}
		}
		lblCash.setText("$ "+cash);
	}
	public void endTurn() {
		day++;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).lblComName.getText().equals("Cow")) {
				int oldPrice = items.get(i).price;
				items.get(i).changePrince(ran.nextInt(100)-50);
				int newPrice = items.get(i).price;
				JOptionPane.showMessageDialog(null, "Price change:\nCow\nOld price: $ " + oldPrice + "!\nNew price $ " + newPrice + "!");
			}
		}
		marketCheck();
		lblDate.setText("Dag: "+day);

	}
	public void actionPerformed (ActionEvent e) {

		if (e.getSource() == btnMarket) {
			switchTab(pnlMarket());
		}
		if(e.getSource() == btnFinance) {
			switchTab(pnlFinance());
		}
		if(e.getSource() == btnNextDay) {
			endTurn();
		}
		if(e.getSource() == btnExit) {
			System.exit(0);
		}
	}
	public static void main (String [] args) {
		UIMain ui = new UIMain();
		System.out.println(ui.getHeight());
		System.out.println(ui.getWidth());
		System.out.println();
		System.out.println(ui.mainBoard.getHeight());
		System.out.println(ui.mainBoard.getWidth());
		System.out.println();
		System.out.println(ui.pnlEast.getHeight());
		System.out.println(ui.pnlEast.getWidth());
		System.out.println();

	}
	
	private class Commodity implements ActionListener {
		private JLabel lblComName= new JLabel();
		private JLabel lblComImage = new JLabel();
		private JButton btnBuy = new JButton("Köp!");
		private JButton btnSell = new JButton("Sälj!");
		private int price;
		private int stock = 0;

		public Commodity(String name, int price) {
			lblComName.setText(name);
			lblComImage.setText("Här ska en bild vara");
			btnBuy.addActionListener(this);
			btnSell.addActionListener(this);
			btnSell.setEnabled(false);
			items.add(this);
			this.price=price;
			if (cash < price) {
				btnBuy.setEnabled(false);
			}
		}
		public void changePrince(int newPrice) {
			price+=newPrice;

		}
		public JPanel toJPanel() {
			JPanel panel = new JPanel (new GridLayout(1,4));
			panel.add(lblComName);
			panel.add(lblComImage);
			panel.add(btnBuy);
			panel.add(btnSell);
			return panel;
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnBuy)) {
				stock ++;				
				cash -= price;
				//*******************************
				if (lblComName.getText().equals("Cow")) {
					mainBoard.addAnimal(new Cow());
				}
				else if (lblComName.getText().equals("Pig")) {
					mainBoard.addAnimal(new Pig());
				}

			}
			else if(e.getSource().equals(btnSell)) {
				stock --;
				cash += price;
				//*******************************
				if (lblComName.getText().equals("Cow")) {
					mainBoard.removeAnimal(new Cow());
				}
				else if (lblComName.getText().equals("Pig")) {
					mainBoard.removeAnimal(new Pig());
				}



			}
			marketCheck();
			System.out.println("You have " + stock + " " + lblComName.getText() + "(s)!");
			System.out.println("Your remaining funds: " + cash +"$!");
		}
	}
}
