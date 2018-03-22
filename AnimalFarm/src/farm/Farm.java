//package com.zetcode;
package farm;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Farm extends JFrame {

	public Farm() {

		initUI();
	}

	private void initUI() {

		add(new Board());
		setResizable(false);
		pack();
		setTitle("Farm");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame ex = new Farm();
				ex.setVisible(true);
			}
		});
	}
}