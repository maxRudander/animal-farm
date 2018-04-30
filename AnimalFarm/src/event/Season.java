package event;

import java.awt.Color;

import farm.Board;
import main.Controller;
import ui.UIMain;

public class Season {
	private Controller controller;
	private Board board;
	private UIMain main;
	
	public static final Color SPRING = new Color(183, 215, 132);
	public static final Color SUMMER = new Color(130, 202, 112);
	public static final Color FALL = new Color(243, 188, 46);
	public static final Color WINTER = new Color(179, 218, 241);


	public Season(Board board, UIMain main) {
		this.board = board;
		this.main = main;
		
	}

	public void setSeason(int week) {	
		try {
					
		if (week <= 13) {
			board.alterSeason(SPRING);
			main.setLblAction("It's Spring!");
		}

		else if (week >= 14 && week <= 26) {
			board.alterSeason(SUMMER);
			main.setLblAction("It's Summer!");
		}

		else if (week >= 27 && week <= 39) {
			board.alterSeason(FALL);
			main.setLblAction("It's Fall!");
		}

		else if (week >= 40) {
			board.alterSeason(WINTER);
			main.setLblAction("It's Winter!");
		}

		}catch(NullPointerException e) {
			System.out.println("null pointer");
			
		}
		
		
		
	}

}