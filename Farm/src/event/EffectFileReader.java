package event;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class the reads a text file and stores the information within to be used with the EventHandler.
 * @author Max Rudander, Elin Olsson, Malin Zederfeldt.
 *
 */
public class EffectFileReader {
	private EventHandler handler = EventHandler.getInstance();
	private String [][][] effectMap; 
	private	int eventID;
	private int optionID;
	private int effectID;

	/**
	 * Reads a file and stores the information in a 3D String array.
	 * @param filename - the file to be read.
	 */
	public EffectFileReader(String filename) {
		String line;
		effectMap = new String [handler.countEvents()][3][4];
		try(BufferedReader buf = new BufferedReader (new InputStreamReader (new FileInputStream (filename), "UTF-8"))) {
			line = buf.readLine();
			while (line!=null) {
				if (!line.startsWith("#")) {
					eventID = Integer.parseInt(line.substring(0, line.indexOf(".")));
					optionID = Integer.parseInt(line.substring(line.indexOf(".")+1));
					effectID = 0;
					line = buf.readLine();
					
					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							effectMap [eventID][optionID][effectID] = line;
							line = buf.readLine();
							effectID++;
						}
						line = buf.readLine();
					}
				}
				else {
					line=buf.readLine();
				}
			}
			//Kodgranskning: tom exception
		} catch (IOException e) {}
	}
	/**
	 * Returns the effectMap, a 3D String array in which the data from the read file is stored.
	 * @return - the effectMap
	 */
	public String[][][] getEffectMap () {
		return effectMap;
	}
	/**
	 * A simple test of the effect map. Will be removed later on.
	 * @param args
	 */
	//Kodgranskning: ta bort main
	public static void main (String[]args) {
		new EventFileReader("files/testevent.txt"); // must be instantiated or Event count will be zero!
		EffectFileReader reader = new EffectFileReader("files/testeffect.txt");
		for (int i = 0; i<reader.effectMap.length; i++) {
			for (int j = 0; j < reader.effectMap[i].length; j++) {
				  for (int k = 0; k < reader.effectMap[i][j].length; k++) {
				    System.out.println(reader.effectMap[i][j][k]);
				  }
				  System.out.println("");
			}
		}
	}
}
