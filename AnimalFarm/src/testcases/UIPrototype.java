package testcases;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class UIPrototype extends JFrame implements ActionListener {
	private JButton btnMarket = new JButton("Marknadsplats");
	private JButton btnChangeDay = new JButton("Byt dag");
	private JButton btnExit = new JButton("Avsluta");
	private JPanel pnlMain = new JPanel();
	private JPanel pnlEast = new JPanel();
	private JPanel pnlNorth = new JPanel();
	private JPanel pnlMarket = new JPanel();
	private JPanel pnlButtonEast = new JPanel();
	private JPanel pnlTextWindow = new JPanel();
	private JTextArea txtWindow = new JTextArea();
	private int cash = 1000;

	private ArrayList<Commodity> items = new ArrayList<Commodity>();
	private boolean marketOpen = false;

	private void setPanels () {
		pnlEast.setLayout(new BorderLayout());
		pnlButtonEast.setLayout(new GridLayout(1,1));
		pnlNorth.setLayout(new GridLayout(1,2));
		pnlMain.setLayout(new BorderLayout ());

		new Commodity("Cow", 500);
		new Commodity("Pig", 300);
		new Commodity("Horse", 2000);
		new Commodity("Apple", 5);
		new Commodity("Tomato", 5);

		pnlMarket.setLayout(new GridLayout (Math.max(10, items.size()),1));



		for  (int i = 0; i<items.size(); i++) {
			pnlMarket.add(items.get(i).toJPanel());
		}

		pnlButtonEast.add(btnMarket);

		pnlEast.add(pnlButtonEast, BorderLayout.WEST);

		pnlNorth.add(btnChangeDay);
		pnlNorth.add(btnExit);

		pnlTextWindow.add(txtWindow);

		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlTextWindow, BorderLayout.CENTER);

		new JFrame("Bondgården prototyp");
		setPreferredSize(new Dimension(800,600));
		add(pnlMain);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
	private void setButtonListeners() {
		btnMarket.addActionListener(this);
		btnExit.addActionListener(this);
		btnChangeDay.addActionListener(this);
	}
	public void actionPerformed (ActionEvent e) {

		if (e.getSource() == btnMarket) {
			if (!marketOpen) {
				pnlEast.add(pnlMarket, BorderLayout.EAST);
				pnlEast.revalidate();
				marketOpen = !marketOpen;
			}
			else {
				pnlEast.removeAll();
				pnlEast.add(btnMarket);
				pnlEast.revalidate();
				marketOpen = !marketOpen;
			}
		}
		if(e.getSource() == btnChangeDay) {

		}
		if(e.getSource() == btnExit) {
			System.exit(0);
		}
	}
	public void controlAvailability () {
		
	}

	public static void main(String[] args) {
		UIPrototype ui = new UIPrototype();
		ui.setButtonListeners();
		ui.setPanels();


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
				if (stock > 0) {
					btnSell.setEnabled(true);;
				}
				if (cash < price) {
					btnBuy.setEnabled(false);
				}
			}
			else if(e.getSource().equals(btnSell)) {
				stock --;
				cash += price;
				if (stock < 1) {
					btnSell.setEnabled(false);
				}
				if (cash >= price) {
					btnBuy.setEnabled(true);
				}

			}
			System.out.println("You have " + stock + " " + lblComName.getText() + "(s)!");
			System.out.println("Your remaining funds: " + cash);
		}
	}

}
