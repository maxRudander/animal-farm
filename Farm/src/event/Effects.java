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
	public void alterCash (Integer amount) {
		//add or remove cash
		controller.setCash(amount);
		controller.requestCheck();

		System.out.println("Cash change " + amount);

	}
	/**
	 * Adds or removes any number of any Animal.
	 * @param animalType - the type of Animal to be added or removed.
	 * @param amount - the number of Animals to be added (or removed if negative)
	 */
	public void alterAnimalStock (String animalType, Integer amount) {
		if (amount > 0) {
			for (int i = 0; i<amount; i++) {
				controller.buyCommodity(animalType);
			}
		}
		else if (amount < 0) {
			for (int i = amount; i < 0; i++) {
				controller.sellCommodity(animalType);
			}
		}
		controller.requestCheck();
		System.out.println(animalType + " change " + amount);
	}
	public void alterCropsStock (String CropsType, Integer amount) {
		if (amount > 0) {
			for (int i = 0; i<amount; i++) {
				controller.buyCrops(CropsType, i,i);		// Not an advisable effect!!!
			}
		}
		else if (amount < 0) {
			for (int i = amount; i < 0; i++) {
				controller.sellCrops(CropsType);
			}
		}
		controller.requestCheck();
		System.out.println(CropsType + " change " + amount);
	}
	/**
	 * @author elino 
	 * @param BuildingType
	 * @param amount
	 */
	public void alterBuildingStock (String BuildingType, Integer amount) {
		if (amount > 0) {
			for (int i = 0; i<amount; i++) {
				controller.buyProperty(BuildingType, i, i); // Not an advisable effect!!!
			}
		}
		else if (amount < 0) {
			for (int i = amount; i < 0; i++) {
				controller.sellProperty(BuildingType);
			}
		}
		controller.requestCheck();
		System.out.println(BuildingType + " change " + amount);
	}
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
