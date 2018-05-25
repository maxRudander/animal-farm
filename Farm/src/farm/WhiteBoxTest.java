package farm;

import commodity.Chicken;
import commodity.Cow;
import commodity.Pig;
import commodity.Sheep;
import finance.BankLoan;
import finance.BankLoan2;
import finance.MafiaLoan;
import finance.PaydayLoan;
import main.Controller;
import property.Barn;
import property.HenHouse;
import property.Pigsty;
import property.Stable;
import ui.UIMain;

public class WhiteBoxTest {

	private Board board = new Board();
	private Controller controller = new Controller();
	private UIMain ui = new UIMain(controller, board);

	private BankLoan bLoan = new BankLoan();
	private BankLoan2 bLoan2 = new BankLoan2();
	private PaydayLoan ploan = new PaydayLoan();
	private MafiaLoan mLoan = new MafiaLoan();

	/**
	 * Testcase 1.0 Linked with testcase 1.0-1.4
	 */
	public void testCase1dot0() {
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("felaktigbyggnad");
		System.out.println("Expected return = -1 ");
		System.out.println("Return = " + returnValue);

	}

	/**
	 * Testcase 1.1 Linked with testcase 1.0-1.4
	 */
	public void testCase1dot1() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new Barn());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Cow());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("Barn");
		System.out.println("Expected return = 1 ");
		System.out.println("Return = " + returnValue);

	}

	/**
	 * Testcase 1.2 Linked with testcase 1.0-1.4
	 */
	public void testCase1dot2() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new HenHouse());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Chicken());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("HenHouse");
		System.out.println("Expected return = 1 ");
		System.out.println("Return = " + returnValue);

	}

	/**
	 * Testcase 1.3 Linked with testcase 1.0-1.4
	 */
	public void testCase1dot3() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new Pigsty());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Pig());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("Pigsty");
		System.out.println("Expected return = 1 ");
		System.out.println("Return = " + returnValue);
	}

	/**
	 * Testcase 1.4 Linked with testcase 1.0-1.4
	 */

	public void testCase1dot4() {
		for (int i = 0; i < 3; i++) {
			board.addBuilding(new Stable());
		}
		for (int i = 0; i < 5; i++) {
			board.addAnimal(new Sheep());
		}
		int returnValue = board.findIndexOfBuildingWithLeastAnimals("Stable");
		System.out.println("Expected return = 1 ");
		System.out.println("Return = " + returnValue);

	}

	/**
	 * Testcase 2.0 Linked with testcase 2.0-2.1
	 */
	public void testCase2dot0() {
		controller.newGame(true, true);
	}

	/**
	 * Testcase 2.1 Linked with testcase 2.0-2.1
	 */
	public void testCase2dot1() {
		controller.newGame(false, false);
	}

	/**
	 * Testcase 3.0 Linked with testcase 3.0-3.4
	 */
	public void testCase3dot0() {
		controller.buyProperty("Wrong input", 1, 100, 100);
	}

	/**
	 * Testcase 3.1 Linked with testcase 3.0-3.4
	 */
	public void testCase3dot1() {
		controller.buyProperty("Barn", 1, 100, 100);
	}

	/**
	 * Testcase 3.2 Linked with testcase 3.0-3.4
	 */
	public void testCase3dot2() {
		controller.buyProperty("HenHouse", 1, 100, 100);
	}

	/**
	 * Testcase 3.3 Linked with testcase 3.0-3.4
	 */
	public void testCase3dot3() {
		controller.buyProperty("Pigsty", 1, 100, 100);
	}

	/**
	 * Testcase 3.4 Linked with testcase 3.0-3.4
	 */
	public void testCase3dot4() {
		controller.buyProperty("Stable", 1, 100, 100);
	}

	/**
	 * Testcase 4.0
	 */
	public void testCase4dot0() {
		controller.buyProperty("Barn", 1, 100, 100);
		controller.sellProperty("Wrong input", 1);
	}

	/**
	 * Testcase 4.1
	 */
	public void testCase4dot1() {
		controller.buyProperty("Barn", 1, 100, 100);
		controller.sellProperty("Barn", 1);
	}

	/**
	 * Testcase 4.2
	 */
	public void testCase4dot2() {
		controller.buyProperty("Pigsty", 1, 100, 100);
		controller.sellProperty("Pigsty", 1);
	}

	/**
	 * Testcase 4.3
	 */
	public void testCase4dot3() {
		controller.buyProperty("Henhouse", 1, 100, 100);
		controller.sellProperty("Henhouse", 1);
	}

	/**
	 * Testcase 4.4
	 */
	public void testCase4dot4() {
		controller.buyProperty("Stable", 1, 100, 100);
		controller.sellProperty("Stable", 1);
	}

	/**
	 * Testcase 5.0 Linked with testcase 5.0-5.4 Debug required to see result.
	 */
	public void testCase5dot0() {
		controller.buyCommodity("Wrong input", 1);
	}

	/**
	 * Testcase 5.1 Linked with testcase 5.0-5.4 Debug required to see result.
	 */
	public void testCase5dot1() {
		controller.buyCommodity("Cow", 1);
	}

	/**
	 * Testcase 5.2 Linked with testcase 5.0-5.4 Debug required to see result.
	 */
	public void testCase5dot2() {
		controller.buyCommodity("Chicken", 1);
	}

	/**
	 * Testcase 5.3 Linked with testcase 5.0-5.4 Debug required to see result.
	 */
	public void testCase5dot3() {
		controller.buyCommodity("Pig", 1);
	}

	/**
	 * Testcase 5.4 Linked with testcase 5.0-5.4 Debug required to see result.
	 */
	public void testCase5dot4() {
		controller.buyCommodity("Sheep", 1);
	}

	/**
	 * Testcase 6.0 Debug required to see result.
	 */
	public void testCase6dot0() {
		controller.buyCommodity("Cow", 1);
		controller.sellCommodity("Wrong input", 1);
	}

	/**
	 * Testcase 6.1 Debug required to see result.
	 */
	public void testCase6dot1() {
		controller.buyCommodity("Cow", 1);
		controller.sellCommodity("Cow", 1);
	}

	/**
	 * Testcase 6.2 Debug required to see result. 
	 */
	public void testCase6dot2() {
		controller.buyCommodity("Pig", 1);
		controller.sellCommodity("Pig", 1);
	}

	/**
	 * Testcase 6.3 Debug required to see result.
	 */
	public void testCase6dot3() {
		controller.buyCommodity("Chicken", 1);
		controller.sellCommodity("Chicken", 1);
	}

	/**
	 * Testcase 6.4 Debug required to see result.
	 */
	public void testCase6dot4() {
		controller.buyCommodity("Sheep", 1);
		controller.sellCommodity("Sheep", 1);
	}

	/**
	 * Testcase 7.0 Wrong type of loan.
	 */
	public void testCase7dot0() {
		testCase2dot0(); // Start game
		controller.acceptLoan("Wrong type of loan", 2000);

	}

	/**
	 * Testcase 7.1 Correct "Farm bank" loan, within min-max
	 */
	public void testCase7dot1() {
		controller.acceptLoan("Farm bank", 2000);
	}

	/**
	 * Testcase 7.2 Correct "Farm bank" loan, below min.
	 */
	public void testCase7dot2() {
		controller.acceptLoan("Farm bank", 50);
	}

	/**
	 * Testcase 7.3 Correct "Farm bank" loan, above max
	 */
	public void testCase7dot3() {
		controller.acceptLoan("Farm bank", 1000000);
	}

	/**
	 * Testcase 7.4 Correct "City bank" loan, within min-max
	 */
	public void testCase7dot4() {
		controller.acceptLoan("City bank", 2000);
	}

	/**
	 * Testcase 7.5 Correct "City bank" loan, below min
	 */
	public void testCase7dot5() {
		controller.acceptLoan("City bank", 50);
	}

	/**
	 * Testcase 7.6 Correct "City bank" loan, above max
	 */
	public void testCase7dot6() {
		controller.acceptLoan("City bank", 1000000);
	}

	/**
	 * Testcase 7.7 Correct "Payday Loan", loan within min-max
	 */
	public void testCase7dot7() {
		controller.acceptLoan("Payday loan", 2000);
	}

	/**
	 * Testcase 7.8 Correct "Payday Loan", loan below min
	 */
	public void testCase7dot8() {
		controller.acceptLoan("Payday loan", 50);
	}

	/**
	 * Testcase 7.9 Correct "Payday Loan", loan above max
	 */
	public void testCase7dot9() {
		controller.acceptLoan("Payday loan", 1000000);
	}

	/**
	 * Testcase 7.10 Correct "Mafia Loan", loan within min-max
	 */
	public void testCase7dot10() {
		controller.acceptLoan("Mafia loan", 2000);
	}

	/**
	 * Testcase 7.11 Correct "Mafia Loan", loan below min
	 */
	public void testCase7dot11() {
		controller.acceptLoan("Mafia loan", 50);
	}

	/**
	 * Testcase 7.12 Correct "Mafia Loan", loan above max
	 */
	public void testCase7dot12() {
		controller.acceptLoan("Mafia loan", 1000000);
	}

	/**
	 * Mainmethod for starting testcases.
	 * 
	 * @param args
	 */
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

		// wbt.testCase2dot0();

		// wbt.testCase2dot1(); //Will crash without testCase2dot0 or testCase2dot1
		// since the UI wants to dispose an old UI that doesnt exist. But result are
		// expected.

		// wbt.testCase3dot0();
		// wbt.testCase3dot1();
		// wbt.testCase3dot2();
		// wbt.testCase3dot3();
		// wbt.testCase3dot4();

		/**
		 * Testcase 2.0 or 2.1 required to run before 4.0-4.4 testCases
		 */

		// wbt.testCase4dot0();
		// wbt.testCase4dot1();
		// wbt.testCase4dot2();
		// wbt.testCase4dot3();
		// wbt.testCase4dot4();
		 wbt.testCase7dot0();
//		 wbt.testCase7dot1();
		 wbt.testCase7dot2();
//		 wbt.testCase7dot3();
//		 wbt.testCase7dot4();
//		 wbt.testCase7dot5();
//		 wbt.testCase7dot6();
//		 wbt.testCase7dot7();
//		 wbt.testCase7dot8();
//		 wbt.testCase7dot9();
//		 wbt.testCase7dot10();
//		 wbt.testCase7dot11();
//		 wbt.testCase7dot12();
//		 
		
	}

}
