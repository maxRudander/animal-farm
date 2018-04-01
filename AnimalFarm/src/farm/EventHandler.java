package farm;

import java.util.ArrayList;

public class EventHandler {
	private static EventHandler instance;
	private ArrayList<Event> events = new ArrayList<Event>();
	private Effects ef;

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

	public UIEvent runEvent (int id) {
		if (events.size()>=id) {
			return new UIEvent(events.get(id));
		}
		else{
			return null;
		}
	}
	public void triggerEffect(String choice) throws NoSuchMethodException, SecurityException {

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

}
