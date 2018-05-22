package event;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Controller;
import ui.UIEvent;
/**
 * Class that organizes Events and sends them to UIEvents.
 * Also connects Event Options with their effects. Singleton class.
 * @author Max R
 *
 */
public class EventHandler {
	private static EventHandler instance;
	private Effects effects;
	private Conditions conditions;
	private ArrayList<Event> events = new ArrayList<Event>();
	private String [][][] effectMap;
	private String [][] conditionMap;
	private boolean gameMode;
	/**
	 * Empty private constructor to overwrite default constructor.
	 */
	private EventHandler() {

	}
	/**
	 * If the class is instantiated, the instance is returned. If not, the class is instantiated and then returned. 
	 * @return
	 */
	public static EventHandler getInstance() {
		if (instance == null) {
			instance = new EventHandler();
		}
		return instance;
	}
	/**
	 * Adds a new Event to the list of events.
	 * @param event - the Event to be added.
	 */
	public void addEvent(Event event) {
		events.add(event);
	}
	/**
	 * Counts the number of events in the list.
	 * @return - the number of events.
	 */
	public int countEvents() {
		return events.size();
	}
	/**
	 * Searches for and returns the Event whose ID is provided as an argument. If the ID is invalid, the default event is returned.
	 * @param id - the ID of the Event.
	 * @return - the Event.
	 */
	public Event getEvent (int id) {
		if (events.size()>=id+1 && id>=0) {
			return events.get(id);
		}
		else {
			return Event.alienInvasion();
		}
	}
	/**
	 * Opens up the Event whose ID is provided as an argument in a UIEvent.
	 * @param id - the ID of the Event.
	 * @return - a new UIEvent created from the Event whose ID is provided.
	 */
	public UIEvent runEvent (int id) {
		//TODO checkConditions(id)
		return new UIEvent(getEvent(id), gameMode);
	}
	/**
	 * Instantiates the effectMap. An EffectFileReader must have been constructed before this method is called.
	 * @param effectMap - a 3D String array intended to hold all the effects for all the options for all the events.
	 */
	public void setEffectMap (String[][][] effectMap) {
		this.effectMap=effectMap;
	}
	/**
	 * Instantiate Effects passing on a Controller in the process.
	 * @param controller - the controller
	 */
	public void instantiateEffects (Controller controller) {
		effects = new Effects (controller);
	}
	public void instantiateConditions (Controller controller) {
		conditions = new Conditions (controller, null);
	}
	/**
	 * Sets a boolean to true if game mode is, otherwise false.
	 * @param gameMode - true for game mode, false for test mode.
	 */
	public void setGameMode(boolean gameMode) {
		this.gameMode=gameMode;
	}
	/**
	 * Absolutely awful way of handling effects. Will not be used!
	 * Currently serves a purpose by printing out the event option and id.
	 */
	public void triggerEffects(String choice) {// throws NoSuchMethodException, SecurityException {

		switch(choice) {
//		case "0.0" :
//			break;
//		case "0.1" :
//			break;
//		case "0.2" :
//			break;
//		case "1,0" :
//			break;
//		case "1.1" :
//			break;
//		case "1.2" :
//			break;
		default :
			System.out.println(choice);
			break;
		}
	}
	public void checkConditions (int eventID) {
		//TODO ask if eventID has conditions
		/*
		 * If yes ask every entry in String [...][0] if it begins with eventID
		 * when found read all String [x][...]
		 * store all entries into an ArrayList<String>
		 * 
		 * call runMethods(the list, Conditions object) 
		 */
	}
	/**
	 * This method is called when an option is picked in an UIEvent.
	 * The Event ID and option ID are provided as arguments.
	 * The effectMap is then searched for the effects pertaining to the Event and option.
	 * Lastly, runMethods is called with the found effects as arguments.
	 * @param eventID - the id of the Event that has been responded to
	 * @param optionID - the id of the Option that has been picked
	 */
	public void triggerEffects(int eventID, int optionID) {
		ArrayList<String> effectList = new ArrayList<String>();
		String effectStr;
		try {
			for (int i = 0; i < effectMap[eventID][optionID].length; i++) {
				effectStr = effectMap[eventID][optionID][i];
				if (!(effectStr == null)) {
					effectList.add(effectStr);
				}
				else {
					break;
				}
			}
			runMethods(effectList, effects);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "The effects for this event are not properly mapped out!");
		}

	}
	/**
	 * This method invokes methods in an object of either Effects or Conditions using reflection.
	 * <br>For each entry in the effectList parameter, a method name and arguments are located.
	 * Using this information, Object arrays are created to represent the arguments.
	 * From the Object arrays, Class arrays are also created to represent the arguments' respective parameter types.
	 * <br><br>Method objects corresponding to a the method name and Class array are then created.
	 * <br>Finally, those Method object are invoked using the Object arrays.
	 * 
	 * <br><br>Can currently invoke methods with String, Integer, Boolean as well as empty parameters.
	 * 
	 * 
	 * @param effectList - an array of the effects to be triggered.
	 * @param object - the object from which the methods are to be invoked
	 */
	private void runMethods(ArrayList<String> effectList, Object object) {
		StringBuilder builder;
		int nextBreak;

		String method;
		ArrayList<Object> paramsObj = new ArrayList<Object>();
		String nextParamS;
		Integer nextParamI;
		Boolean nextParamB;

		Method meth;
		Class<?> [] parameterTypes;
		Object [] params;

		for (int i = 0; i < effectList.size(); i++) {
			builder = new StringBuilder(effectList.get(i));
			nextBreak = builder.indexOf("\t");
			if (nextBreak < 0) {			// i.e. Empty parameter list!
				method = builder.toString();
				try {
					meth = object.getClass().getMethod(method);
					meth.invoke(object);
				}
				catch (Exception e) {
					System.out.println("Sigh, it was worth a shot!");
				}
			}
			else {
				method = builder.substring(0, nextBreak);
				builder.delete(0, nextBreak+1);
				nextBreak = builder.indexOf("\t");
				while (true) {
					try {
						nextParamS = builder.substring(0, nextBreak);
					}
					catch (StringIndexOutOfBoundsException e0) {
						nextParamS = builder.toString();
					}
					try {
						nextParamI = Integer.parseInt(nextParamS);
						paramsObj.add(nextParamI);
					}
					catch (NumberFormatException e1) {
						if (nextParamS.equals("TRUE") || nextParamS.equals("FALSE")) {
							nextParamB = Boolean.parseBoolean(nextParamS);
							paramsObj.add(nextParamB);
						}
						else {
							paramsObj.add(nextParamS);
						}
					}
					try {
						builder.delete(0, nextBreak);		// Both lines are necessary for
						builder.delete(0, 1);				// the exception to get caught!
						nextBreak = builder.indexOf("\t");
					}
					catch (StringIndexOutOfBoundsException e2) {
						break;
					}
				}
				//*******************************

				parameterTypes = new Class<?> [paramsObj.size()];
				params = new Object [paramsObj.size()];

				for (int j = 0; j<paramsObj.size(); j++) {
					parameterTypes[j] = paramsObj.get(j).getClass();
					params[j] = paramsObj.get(j);
				}	
				paramsObj.clear();

				try {
					meth = object.getClass().getMethod(method, parameterTypes);
					meth.invoke(object, params);
				}
				catch (Exception e) {
					System.out.println("Sigh, it was worth a shot!");
				}
			}

		}
	}

}
