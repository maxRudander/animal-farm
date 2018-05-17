package event;

import java.util.ArrayList;

import main.Controller;
import ui.UIMain;

public class Conditions {
	private Controller controller;
	private UIMain main;
	private int [] conditionID;
	
	public Conditions (Controller controller, UIMain main) {
		this.controller = controller;
		this.main = main;										// Shouldn't be here.
	}
	public void setConditions(ArrayList<Integer> list) {
		conditionID = new int [list.size()];
		for (int i = 0; i<list.size(); i++) {
			conditionID[i]= list.get(i).intValue();
		}
	}
	public boolean hasConditions (int id) {
		for (int i = 0; i<conditionID.length; i++) {
			if (conditionID[i] == id) {
				return true;
			}
		}
		return false;
	}
	public boolean hasAnimals (String animalType, Integer amount) {
		if (main.getCommodityStock(animalType) >= (amount.intValue())) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean hasCash (Integer amount) {
		if (controller.getCash() >= amount.intValue()) {
			return true;
		}
		else {
			return false;
		}
	}

}
