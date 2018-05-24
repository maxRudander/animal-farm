package event;

import java.util.ArrayList;

import main.Controller;

/**
 * Condition methods.
 * Every method must return a non primitive Boolean i.e. the wrapper class.
 * @author Max
 *
 */
public class Conditions {
	private Controller controller;
	private int [] conditionID;
	
	public Conditions (Controller controller) {
		this.controller = controller;
	}
	/**
	 * Not used. Probably not needed.
	 * @param list
	 */
	public void setConditions(ArrayList<Integer> list) {
		conditionID = new int [list.size()];
		for (int i = 0; i<list.size(); i++) {
			conditionID[i]= list.get(i).intValue();
		}
	}
	/**
	 * Not used. Probably not needed.
	 * @param id
	 * @return
	 */
	public boolean hasConditions (int id) {
		for (int i = 0; i<conditionID.length; i++) {
			if (conditionID[i] == id) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks whether the current stock of the given type is equal to or greater than the given integer.
	 * @param type - the type of animal, building, crop or good
	 * @param amount - the minimum amount
	 * @return true if the check is passed, else false
	 */
	public boolean atLeast (String type, Integer amount) {
		if (controller.getStock(type) >= amount) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean atLeastRoom (String building, Integer amount) {
		String animal;
		try {
			Class <?> c = Class.forName("property."+building);
			animal = (String) c.getMethod("getOccupant").invoke(null);
		} catch (Exception e) {
			return false;
		}
		if (controller.getStock(building)* 8 - controller.getStock(animal) >= amount) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * Checks whether the current cash is greater than or equal to the given integer.
	 * @param amount - the minimum amount
	 * @return true if the check is passed, else false
	 */
	public boolean atLeastCash (Integer amount) {
		if (controller.getCash() >= amount) {
			return true;
		}
		else {
			return false;
		}
	}

}
