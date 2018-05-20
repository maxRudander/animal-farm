package farm;

import commodity.*;
import main.Controller;
import property.*;

public class WhiteBoxTest {

	private Board board = new Board();
	private Controller controller = new Controller();

	public void testCase1dot0() {
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("felaktigbyggnad");
		System.out.println("Förväntad retur = -1 ");
		System.out.println("Retur = " + returnValue);

	}

	public void testCase1dot1() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new Barn());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Cow());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("Barn");
		System.out.println("Förväntad retur = 1 ");
		System.out.println("Retur = " + returnValue);

	}

	public void testCase1dot2() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new HenHouse());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Chicken());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("HenHouse");
		System.out.println("Förväntad retur = 1 ");
		System.out.println("Retur = " + returnValue);

	}

	public void testCase1dot3() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new Pigsty());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Pig());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("Pigsty");
		System.out.println("Förväntad retur = 1 ");
		System.out.println("Retur = " + returnValue);

	}

	public void testCase1dot4() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new Stable());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Sheep());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("Stable");
		System.out.println("Förväntad retur = 1 ");
		System.out.println("Retur = " + returnValue);

	}

	public void testCase2dot0() {
		controller.newGame(true, true);
	}

	public void testCase2dot1() {
		controller.newGame(true, false);
	}

	public void testCase2dot2() {
		controller.newGame(false, true);
	}

	public void testCase2dot3() {
		controller.newGame(false, false);
	}

	public void testCase3dot0() {
		controller.buyProperty("Felaktig byggnadstyp",100, 100, 100);
	}
	public void testCase3dot1() {
		controller.buyProperty("Barn", 100,100, 100);
	}
	public void testCase3dot2() {
		controller.buyProperty("HenHouse",100, 100, 100);
	}
	public void testCase3dot3() {
		controller.buyProperty("Pigsty", 100,100, 100);
	}
	public void testCase3dot4() {
		controller.buyProperty("Stable",100, 100, 100);
	}
	

	/**
	 * Debug required to see result.
	 */
	public void testCase4dot0() {
		controller.buyCommodity("Felaktig Animal-typ", 100);
	}

	/**
	 * Debug required to see result.
	 */
	public void testCase4dot1() {
		controller.buyCommodity("Cow",100);
	}

	/**
	 * Debug required to see result.
	 */
	public void testCase4dot2() {
		controller.buyCommodity("Chicken",100);
	}

	/**
	 * Debug required to see result.
	 */
	public void testCase4dot3() {
		controller.buyCommodity("Pig",100);
	}

	/**
	 * Debug required to see result.
	 */
	public void testCase4dot4() {
		controller.buyCommodity("Stable",100);
	}

	public static void main(String[] args) {

		WhiteBoxTest wbt = new WhiteBoxTest();
		/**
		 * Need to be run separately to get the expected result.
		 */
		// wbt.testCase1dot0();
		// wbt.testCase1dot1();
		// wbt.testCase1dot2();
		// wbt.testCase1dot3();
		// wbt.testCase1dot4();

		/**
		 * Need to be run separately to get the expected result.
		 */

		 wbt.testCase2dot0();
		// wbt.testCase2dot1();
		// wbt.testCase2dot2(); //Will crash without testCase2dot0 or testCase2dot1
		// since the UI wants to dispose an old UI that doesnt exist. But result are
		// expected.
		// wbt.testCase2dot3(); //Will crash without testCase2dot0 or testCase2dot1
		// since the UI wants to dispose an old UI that doesnt exist. But result are
		// expected.

		
//		wbt.testCase3dot0();
		wbt.testCase3dot1();
//		wbt.testCase3dot2();
//		wbt.testCase3dot3();
//		wbt.testCase3dot4();
		
		/**
		 * Testcase 2.0 or 2.1 required to run before 4.0-4.4 testCases
		 */

		 wbt.testCase4dot0();
		// wbt.testCase4dot1();
		// wbt.testCase4dot2();
		// wbt.testCase4dot3();
		// wbt.testCase4dot4();

	}

}
