package farm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
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
//	private JPanel pnlTextWindow = new JPanel();
	private Board pnlTextWindow = new Board();
	
	
	private int cash = 1500;
	private JTextArea txtWindow = new JTextArea();
	private JLabel lblCash = new JLabel(String.format("%40d $", cash));
	

	private ArrayList<Commodity> items = new ArrayList<Commodity>();
	private boolean marketOpen = true;

	private void setPanels () {
		pnlEast.setLayout(new GridLayout(2,1));
		pnlNorth.setLayout(new GridLayout(1,3));
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
		pnlEast.add(btnMarket, BorderLayout.CENTER);
		pnlEast.add(pnlMarket);
		pnlNorth.add(btnChangeDay);
		pnlNorth.add(lblCash);
		pnlNorth.add(btnExit);
		pnlTextWindow.add(txtWindow);
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlTextWindow, BorderLayout.CENTER);
		new JFrame("Bondgården prototyp");
		
		setPreferredSize(new Dimension(1260,665));
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
				pnlEast.add(pnlMarket);
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
			endTurn();

		}
		if(e.getSource() == btnExit) {
			System.exit(0);
		}
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
		lblCash.setText(String.format("%40d $", cash));
	}
	public void endTurn() {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).lblComName.getText().equals("Cow")) {
				items.get(i).changePrince(100);
			}
		}
		marketCheck();
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
					pnlTextWindow.addAnimal(new Cow());
				}
				else if (lblComName.getText().equals("Pig")) {
					pnlTextWindow.addAnimal(new Pig());
				}
				
			}
			else if(e.getSource().equals(btnSell)) {
				stock --;
				cash += price;
				//*******************************
				if (lblComName.getText().equals("Cow")) {
					pnlTextWindow.removeAnimal(new Cow());
				}
				else if (lblComName.getText().equals("Pig")) {
					pnlTextWindow.removeAnimal(new Pig());
				}
				
				

			}
			marketCheck();
			System.out.println("You have " + stock + " " + lblComName.getText() + "(s)!");
			System.out.println("Your remaining funds: " + cash +"$!");
		}
	}

}
