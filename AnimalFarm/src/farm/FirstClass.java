package farm;

import java.awt.Color;
import java.awt.EventQueue;
import java.lang.reflect.Method;

import javax.swing.UIManager;

public class FirstClass {
	//private UIMain main = new UIMain();
	
	public static void settings() {
		UIManager.put("ToolTip.background", Color.GREEN);
		UIManager.put("Button.select", Color.BLACK);
		UIManager.put("Button.background", new Color (204, 102, 0));
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.disabledText", new Color (204, 102, 0));
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
					Class.forName("farm.UIStartMenu").newInstance();
				}
				catch (Exception e) {
				}
			}
		});
	}
}