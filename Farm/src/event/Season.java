package event;

import java.awt.Color;
import java.util.Random;

import farm.Board;
import main.Controller;
import ui.UIMain;

/**
 * Class that sets and alters seasons and triggers events
 * 
 * @author Malin Zederfeldt
 *
 */
public class Season {
	private Board board;
	private UIMain main;
	private EventHandler handler = EventHandler.getInstance();

	private int id;
	private Random rand = new Random();
	public static final Color SPRING = new Color(183, 215, 132);
	public static final Color SUMMER = new Color(130, 202, 112);
	public static final Color FALL = new Color(243, 188, 46);
	public static final Color WINTER = new Color(179, 218, 241);

	/**
	 * Constructor sets board and UI for class
	 **/
	public Season(Board board, UIMain main) {
		this.board = board;
		this.main = main;
	}

	/**
	 * sets season after week in game
	 * 
	 * @param week
	 *            int week in gameplay
	 */

	public void setSeason(int week) {
		try {
			if (week <= 13) {
				spring();
			} else if (week >= 14 && week <= 26) {
				summer();
			} else if (week >= 27 && week <= 39) {
				fall();
			} else if (week >= 40) {
				winter();
			}
			//Kodgranskning: tom exception e.printStackTrace
		} catch (NullPointerException e) {
			System.out.println("null pointer");

		}
	}

	/**
	 * settings for spring
	 */
	public void spring() {
		board.alterSeason(SPRING);
		main.setLblAction("It's Spring!");
		id = rand.nextInt(handler.countEvents() - 1);
		handler.runEvent(id);
	}

	/**
	 * settings for summer
	 */
	public void summer() {
		board.alterSeason(SUMMER);
		main.setLblAction("It's Summer!");
		id = rand.nextInt(handler.countEvents() - 1);
		handler.runEvent(id);
	}

	/**
	 * settings for fall
	 **/
	public void fall() {
		board.alterSeason(FALL);
		main.setLblAction("It's Fall!");
		id = rand.nextInt(handler.countEvents() - 1);
		handler.runEvent(id);
	}

	/**
	 * settings for winter
	 */
	public void winter() {

		board.alterSeason(WINTER);
		main.setLblAction("It's Winter!");
		id = rand.nextInt(handler.countEvents() - 1);
		handler.runEvent(id);
	}

	/**
	 * lets other classes access static colors for seasons
	 * 
	 * @param week
	 *            current week in gameplay
	 * @return color for background
	 */
	public Color getSeason(int week) {
		if (week <= 13) {
			return SPRING;

		} else if (week >= 14 && week <= 26) {
			return SUMMER;

		} else if (week >= 27 && week <= 39) {
			return FALL;

		} else if (week >= 40) {
			return WINTER;
		}
		return SPRING;
	}

}