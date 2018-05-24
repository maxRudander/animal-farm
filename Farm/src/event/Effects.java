package event;

import main.Controller;

/**
 * Class containing all the effects to be used by Events.
 * This class will corroborate with both the EventHandler and the Controller.
 * Note that the lack of primitive data types as parameters is intentional.
 * Use wrappers (such as Integer) instead.
 * <br><br>
 * The following parameter types are supported (empty is also allowed):
 * <ol>
 * <li> Boolean	-	the strings "TRUE" and "FALSE are parsed (upper case necessary).
 * <li> Integer	-	all integers (both positive and negative) are parsed.
 * <li> String	-	any entry that isn't parsed defaults to String.
 * </ol>
 * Observe that no methods in this class (apart from the constructor) should have a return value.
 * Returning a boolean (or its wrapper) is highly discouraged and is likely to cause severe issues.
 * 
 * @author Max R
 *
 */
public class Effects {
	private Controller controller;

	/**
	 * Constructor for Effects. Ensures that it communicates directly with a Controller.
	 * @param controller - the Controller carrying out the effects.
	 */
	public Effects (Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Increases or decreases the size of the players wallet.
	 * @param amount - the amount to be added (or removed if negative)
	 */
	//Kodgranskning: ta bort println
	public void alterCash (Integer amount) {
		controller.setCash(amount);
		controller.requestCheck();

		System.out.println("Cash change " + amount);

	}
	/**
	 * Adds or removes any number of any Animal.
	 * @param animalType - the type of Animal to be added or removed.
	 * @param amount - the number of Animals to be added (or removed if negative)
	 */
	//Kodgranskning: ta bort println
	public void alterAnimalStock (String animalType, Integer amount) {
		if (amount > 0) {
			for (int i = 0; i<amount; i++) {
				controller.buyCommodity(animalType, 0);
			}
		}
		else if (amount < 0) {
			for (int i = amount; i < 0; i++) {
				controller.sellCommodity(animalType, 0);
			}
		}
		controller.requestCheck();
		System.out.println(animalType + " change " + amount);
	}
	//Kodgranskning: ta bort println, kommentarer
	public void alterCropsStock (String cropsType, Integer amount) {
		if (amount > 0) {
			for (int i = 0; i<amount; i++) {
				controller.buyCrops(cropsType, 0, i,i);		// Not an advisable effect!!!
			}
		}
		else if (amount < 0) {
			for (int i = amount; i < 0; i++) {
				controller.sellCrops(cropsType, 0);
			}
		}
		controller.requestCheck();
		System.out.println(cropsType + " change " + amount);
	}
	/**
	 * @param buildingType
	 * @param amount
	 */
	//Kodgranskning: ta bort println
	public void alterBuildingStock (String buildingType, Integer amount) {
		if (amount > 0) {
			for (int i = 0; i<amount; i++) {
				controller.buyProperty(buildingType, 0, i, i); // Not an advisable effect!!!
			}
		}
		else if (amount < 0) {
			for (int i = amount; i < 0; i++) {
				controller.sellProperty(buildingType, 0);
			}
		}
		controller.requestCheck();
		System.out.println(buildingType + " change " + amount);
	}
	//___________//Kodgranskning: ta bort nedanstående metoder/bygg dem____________________
	/**
	 * An idea of how to deal with long lasting effects.
	 * @param flag
	 * @param status
	 */
	public void setFlag (Integer flag, Boolean status) {
		//set a flag true or false
	}
	/**
	 * A simple test of a method with empty parameters. Will be removed later.
	 */
	public void testEmptyParam () {
		System.out.println("It Worked!");
	}
	/**
	 * A simple test of Boolean parameters. Will be removed later.
	 * @param bool - the Boolean to be checked.
	 */
	public void testBoolean (Boolean bool) {
		if (bool) {
			System.out.println("TRUE");
		}
		else if (!bool) {
			System.out.println("FALSE");
		}
		else {
			System.out.println("???");
		}
	}

}
