package farm;
/**
 * Class containing all the effects to be used by Events.
 * This class will corroborate with both the EventHandler and the Controller.
 * @author Max
 *
 */
public class Effects {
	// contact with controller
	
	public void alterCash (int amount) {
		//add or remove cash
		System.out.println("Cash change " + amount);
	}
	
	public void alterStock (Animal animal, int amount) {
		//add or remove animals
		System.out.println(animal.toString() + " change " + amount);
	}
	public void setFlag (int flag, Boolean status) {
		//set a flag true or false
	}

}
