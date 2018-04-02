package farm;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class EventHandler {
	private static EventHandler instance;
	private ArrayList<Event> events = new ArrayList<Event>();
	private String [][][] effectMap;

	private EventHandler() {
		
	}
	public static EventHandler getInstance() {
		if (instance == null) {
			instance = new EventHandler();
		}
		return instance;
	}
	public void addEvent(Event event) {
		events.add(event);
	}
	public int countEvents() {
		return events.size();
	}
	public Event getEvent (int id) {
		if (events.size()>=id+1 && id>=0) {
			return events.get(id);
		}
		else {
			return Event.alienInvasion();
		}
	}

	public UIEvent runEvent (int id) {
		return new UIEvent(getEvent(id));
	}
	/**
	 * Instantiates the all-powerful effectMap. An EffectFileReader object must have been created before this method is called.
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
	private void triggerEffect(ArrayList<String> effects) {
		for (int i = 0; i < effects.size(); i++) {
			System.out.println(effects.get(i));
		}
		System.lineSeparator();
		
		String method = "alterCash";
	
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
