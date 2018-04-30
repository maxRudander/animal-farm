package event;

import main.Controller;

/**
 * Class containing all the effects to be used by Events.
 * This class will corroborate with both the EventHandler and the Controller.
 * Note that the lack of primitive data types as parameters is intentional.
 * Only use non primitive types limited to String, Integer and Boolean.
 * 
 * @author Max R
 *
 */
public class Effects {
	private Controller controller;
	
	public Effects (Controller controller) {
		this.controller = controller;
	}
	
	public void alterCash (Integer amount) {
		//add or remove cash
		controller.setCash(amount);
		controller.requestCheck();
		
		System.out.println("Cash change " + amount);
		
	}
	
	public void alterStock (String animalType, Integer amount) {
		int amountPrim = amount.intValue();
		//add or remove animals
		if (amountPrim > 0) {
			for (int i = 0; i<amountPrim; i++) {
				controller.buyCommodity(animalType);
			}
		}
		else if (amountPrim < 0) {
			for (int i = amountPrim; i < 0; i++) {
				controller.sellCommodity(animalType);
			}
		}
		controller.requestCheck();
		
		System.out.println(animalType + " change " + amountPrim);
	}
	public void setFlag (Integer flag, Boolean status) {
		//set a flag true or false
	}
	public void testEmptyParam () {
		System.out.println("It Worked!");
	}
	public void testBoolean (Boolean bool) {
		if (bool) {
			System.out.println("TRUE");
		}
		else {
			System.out.println("FALSE");
		}
	}

}
