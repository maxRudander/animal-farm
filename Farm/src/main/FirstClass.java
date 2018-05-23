package main;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.UIManager;
/**
 * 
 * The main class for setting up and starting the game.
 *	@author Max Rudander, Malin Zederfeldt, Matthias Falk
 */
public class FirstClass {
	
	/**
	 * Look and feel setup depending on system.
	 */
	public static void settings() {
		String windowsLookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		String macLookAndFeel = "com.apple.laf.AquaLookAndFeel";
		if (UIManager.getSystemLookAndFeelClassName().equals(windowsLookAndFeel)) {
			UIManager.put("ToolTip.background", Color.GREEN);
			UIManager.put("Button.select", Color.BLACK);
			UIManager.put("Button.background", new Color (204, 102, 0));
			UIManager.put("Button.foreground", Color.WHITE);
			UIManager.put("Button.disabledText", new Color (204, 102, 0));
			UIManager.put("RadioButton.background", new Color (204, 102, 0));
			UIManager.put("RadioButton.foreground", Color.WHITE);
		}
		else if (UIManager.getSystemLookAndFeelClassName().equals(macLookAndFeel)) {
			UIManager.put("ToolTip.background", Color.GREEN);
			UIManager.put("Button.foreground", new Color(204, 102, 0));
			UIManager.put("RadioButton.foreground", Color.WHITE);
			UIManager.put("Button.disabledtext", Color.GRAY);
		}
	}
	/**
	 * Main method for the project
	 */
	public static void main (String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstClass.settings();
					Class.forName("ui.UIStartMenu").newInstance();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}