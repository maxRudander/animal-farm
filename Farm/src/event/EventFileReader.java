package event;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ui.UIEvent;
/**
 * Class the reads a text file and creates events from the information stored within.
 * Not integrated with the rest of the program.
 * @author Max Rudander, Elin Olsson, Malin Zederfeldt.
 *
 */
public class EventFileReader {
	private int id;
	private String title;
	private String text;
	private String[] titleOptions;
	private String[] textOptions;
	private String imagePath;
	private ArrayList<String> list = new ArrayList<String>();
	private EventHandler handler = EventHandler.getInstance();
	
	/**
	 * Reads a file and creates new events.
	 * @param filename - the file to be read.
	 */
	public EventFileReader (String filename) {
		String line;
		try(BufferedReader buf = new BufferedReader (new InputStreamReader (new FileInputStream (filename), "UTF-8"))) {
			line = buf.readLine();
			while (line!=null) {
				if (!line.startsWith("#")) {
					id = Integer.parseInt(line);
					title = buf.readLine();
					imagePath = buf.readLine();
					line = buf.readLine();
					text = "";
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							text += line+System.lineSeparator();
							line = buf.readLine();
						}
					}
					else {
						text = line;
					}

					line = buf.readLine();
					list.clear();
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							list.add(line);
							line = buf.readLine();
						}
					}
					titleOptions = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						titleOptions[i]=list.get(i);
					}
					line = buf.readLine();
					list.clear();
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							list.add(line);
							line = buf.readLine();
						}
					}
					textOptions = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						textOptions[i]=list.get(i);
					}
					list.clear();
					line = buf.readLine();
					createEvent();
				}
				else {
					line = buf.readLine();
				}
			}
			//Kodgranskning: tom exception
		} catch (IOException e) {}
	}
	/**
	 * Creates an Event from the information harvested from the file and stores it in the EventHandler.
	 */
	public void createEvent() {
		Event event = new Event(id, title, text, imagePath, titleOptions, textOptions);
		handler.addEvent(event);
	}
	
	/**
	 * Creates an UIEvent from the information harvested from the text file
	 * @return - a new JFrame containing the UIEvent.
	 */
	public void createUIEvent () {
		Event event = new Event(id, title, text, imagePath, titleOptions, textOptions);
		new UIEvent(event, false);
	}
	/**
	 * A simple test of the reader. Creates a UIEvent of the last event to be read.
	 * @param args
	 */
	//Kodgranskning: ta bort main
	public static void main (String[] args) {
		EventFileReader reader = new EventFileReader("files/testevent.txt");
		reader.createUIEvent();
	}

}
