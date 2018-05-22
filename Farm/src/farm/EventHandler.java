package farm;

import java.lang.reflect.Method;
import java.util.ArrayList;
/**
 * Class that organizes Events and sends them to UIEvents.
 * Also connects Event Options with their effects. Singleton class.
 * @author Max
 *
 */
public class EventHandler {
	private static EventHandler instance;
	private ArrayList<Event> events = new ArrayList<Event>();
	private String [][][] effectMap;
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
		return new UIEvent(getEvent(id));
	}
	/**
	 * Instantiates the effectMap. An EffectFileReader must have been constructed before this method is called.
	 * @param effectMap - a 3D String array intended to hold all the effects for all the options for all the events.
	 */
	public void setEffectMap (String[][][] effectMap) {
		this.effectMap=effectMap;
	}
	/**
	 * Absolutely awful way of handling effects. Will hopefully not be used!
	 */
	public void triggerEffect(String choice) {// throws NoSuchMethodException, SecurityException {

		switch(choice) {
		case "0.0" :
			//trigger
			break;
		case "0.1" :
			//trigger
			break;
		case "0.2" :
			//trigger
			break;

		case "1.0" :
			//trigger
			break;
		case "1.1" :
			//trigger
			break;
		case "1.2" :
			//trigger
			break;
		}
	}
	/**
	 * This method is called when an option is picked in an UIEvent.
	 * The Event ID and option ID are provided as arguments.
	 * The effectMap is then searched for the effects pertaining to the Event and option.
	 * Lastly, another triggerEffect method is called with the found effects as arguments.
	 * @param eventID
	 * @param optionID
	 */
	public void triggerEffect(int eventID, int optionID) {
		ArrayList<String> effects = new ArrayList<String>();
		String effect;
		for (int i = 0; i < effectMap[eventID][optionID].length; i++) {
			effect = effectMap[eventID][optionID][i];
			if (!(effect == null)) {
				effects.add(effect);
			}
			else {
				break;
			}
		}
		triggerEffect(effects);
	}
	/**
	 * Not completed! This method is supposed to call methods in Effects using reflection.
	 * For each entry in the effects parameter, a method name and parameters types and parameters are to be found.
	 * Then the method with those parameters is to be called.
	 * @param effects - an array of the effects to be triggered.
	 */
	private void triggerEffect(ArrayList<String> effects) {
		String method;
		for (int i = 0; i < effects.size(); i++) {
			System.out.println(effects.get(i));
		}
		System.lineSeparator();
		
		method = "alterCash";
	
		Class<?> [] parameterTypes = {int.class};
		Object [] params = {-100};
		
		
		try {
			Class<?> c = Class.forName("farm.Effects");
	        Object eff = c.newInstance();
	        Method m = eff.getClass().getMethod(method, parameterTypes);
	        m.invoke(eff, params);
		} catch (Exception e) {
			
		}
	}

}
