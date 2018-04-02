package farm;

import java.util.Random;

import javax.swing.JOptionPane;

public class Controller {
	private EventHandler handler;
	private UIMain main;
	private Board board;
	
	
	private int day;
	private int cash;
	
	private Random rand = new Random();
	
	public Controller () {
		new EventFileReader("files/testevent.txt");
		handler = EventHandler.getInstance();
		handler.setEffectMap(new EffectFileReader("files/testeffect.txt").getEffectMap());
		board = new Board();
		main = new UIMain(this, board);
		
		
	}
	/**
	 * Handles the exit phase. Complicated stuff!
	 */
	public void exit () {
		if(JOptionPane.showConfirmDialog(null, "Abandon farm??",  null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		
	}
	/**
	 * Handles the end of turn phase. Currently just tests.
	 */
	public void endTurn() {
		main.editCommodity("Cow", 500+(rand.nextInt(100)-50), -1);
		handler.runEvent(0);
	}
	
	
	public static void main (String[] args) {
		new Controller();
	}

}
