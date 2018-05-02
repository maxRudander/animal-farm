package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.lang.reflect.Method;

import javax.swing.UIManager;
/**
 * 
 * @author mlind
 *
 */
public class FirstClass {
	//private UIMain main = new UIMain();
	
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
		
		try {
			//Class<?> c = Class.forName("farm.UIStartMenu");
	        //Object main = c.newInstance();
	        //Method m = main.getClass().getMethod("endTurn");
	        //m.invoke(main);
		} catch (Exception e) {
			
		}
	}
	public static void main (String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstClass.settings();
					Class.forName("ui.UIStartMenu").newInstance();
				}
				catch (Exception e) {
				}
			}
		});
	}
}