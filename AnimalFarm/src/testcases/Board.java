//package com.zetcode;
package farm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	public int x = 800;
	public int y = 600;
	private int animalsize = 80;
	private int buildingsize = 160;

	private int max_x = 665;
	private int max_y = 535;
	int choice = 0;
	private LinkedList<Animal> animalList = new LinkedList<Animal>();
	private LinkedList<Building> buildingList = new LinkedList<Building>();
	private Image cowleft1, cowleft2, cowleft3, pinkcow, barnpic, cowright1, cowright2, cowright3;
	private Timer timer;

	public Board() {
		initBoard();
	}

	private void loadImages() {
		cowleft1 = new ImageIcon("images/ko1.png").getImage();
		cowleft2 = new ImageIcon("images/ko2.png").getImage();
		cowleft3 = new ImageIcon("images/ko3.png").getImage();

		cowright1 = new ImageIcon("images/ko1right.png").getImage();
		cowright2 = new ImageIcon("images/ko2right.png").getImage();
		cowright3 = new ImageIcon("images/ko3right.png").getImage();

		pinkcow = new ImageIcon("images/cowpink.jpg").getImage();
		barnpic = new ImageIcon("images/Barn.png").getImage();
	}

	private void initBoard() {

		// setOpaque(false);
//		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(800, 600));
		setDoubleBuffered(true);
		loadImages();
		timer = new Timer(40, this);
		timer.start();
	}

	public void addAnimal(Animal animal) {
		animalList.add(animal);
	}

	public void removeAnimal(Animal animal) {
		if (animal instanceof Cow) {
			for (int i=0;i<animalList.size();i++ ) {
				if (animalList.get(i) instanceof Cow) {
					animalList.remove(i);
					break;
				}
			}
			
		}
		if (animal instanceof Pig) {
			for(int i=0;i<animalList.size();i++) {
				if (animalList.get(i) instanceof Pig) {
					animalList.remove(i);
					break;
				}
			}
			
		}

	}

	public void paintComponent(Graphics gg) {
		super.paintComponent(gg);
		Graphics2D g = (Graphics2D) gg;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHints(rh);

		drawEdges(g);
		drawAnimals(g);
		drawBuildings(g);

		// gg.setColor(Color.BLACK);
		//
		// RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		// rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		// g.setRenderingHints(rh);
		//
		// g.setColor(Color.GREEN);
		// g.fillRect(100, 100, 800, 800);
		// g.setColor(Color.WHITE);
		// drawBuilding(gg);

	}

	public void drawBuildings(Graphics g) {
		for (int i = 0; i < buildingList.size(); i++) {
			if (buildingList.get(i) instanceof Barn) {
				Barn barn = (Barn) buildingList.get(i);
				g.drawImage(barnpic, barn.getX(), barn.getY(), barn.getX() + buildingsize, barn.getY() + buildingsize,
						0, 0, 856, 638, this);
			}
		}
	}

	public void drawAnimals(Graphics g) {
		for (int i = 0; i < animalList.size(); i++) {
			if (animalList.get(i) instanceof Cow) {
				Cow cow = (Cow) animalList.get(i);
				if (cow.getX_direction() < 0) {
					if (cow.getAnimation() == 0) {
						g.drawImage(cowleft1, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 1) {
						g.drawImage(cowleft2, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 2) {
						g.drawImage(cowleft3, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					}
				} else if (cow.getX_direction() > 0) {
					if (cow.getAnimation() == 0) {
						g.drawImage(cowright1, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 1) {
						g.drawImage(cowright2, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					} else if (cow.getAnimation() == 2) {
						g.drawImage(cowright3, cow.getX(), cow.getY(), cow.getX() + animalsize, cow.getY() + animalsize,
								0, 0, 2120, 1800, this);
					}
				}
				cow.nextAnimation();
			} else if (animalList.get(i) instanceof Pig) {

			}
		}

	}

	public void drawEdges(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, max_x, max_y);
	}

	

	@Override
	public void actionPerformed(ActionEvent arg0) {

		for (int i = 0; i < animalList.size(); i++) {
			animalList.get(i).move();
		}
		repaint();

	}
}
