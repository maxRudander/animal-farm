package farm;

import java.util.Random;

public class Controller {
	private EventHandler handler = EventHandler.getInstance();
	private UIMain main;
	private Board board;
	
	
	private int day;
	private int cash;
	
	private Random rand = new Random();
	
	public Controller () {
		new EventFileReader("files/testevent.txt");
		board = new Board();
		main = new UIMain(this, board);
		
		
	}
	/**
	 * Handles the exit phase. Complicated stuff!
	 */
	public void exit () {
		System.exit(0);
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
