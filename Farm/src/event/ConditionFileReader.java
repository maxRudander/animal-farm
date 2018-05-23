package event;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
//Kodgranskning: Kommentera hela klassen
public class ConditionFileReader {
	//private EventHandler handler = EventHandler.getInstance();
	private String [][] conditionMap;
	private int nbrOfConditionalEvents = 0;
	private int mostConditions = 0;

	public ConditionFileReader (String filename) {
		String line;
		int nbrOfConditions;
		LinkedList <String> list = new LinkedList<String>();
		try(BufferedReader buf = new BufferedReader (new InputStreamReader (new FileInputStream (filename), "UTF-8"))) {
			line = buf.readLine();
			while (line!=null) {
				if (!line.startsWith("#")) {
					nbrOfConditions = 0;
					list.add(line);
					nbrOfConditionalEvents ++;
					line = buf.readLine();

					if (line.startsWith("{")) {
						line = buf.readLine();
						while(!line.startsWith("}")) {
							list.add(line);
							nbrOfConditions++;
							line = buf.readLine();
						}
						line = buf.readLine();
					}
					if (mostConditions<nbrOfConditions) {
						mostConditions=nbrOfConditions;
					}
				}
				else {
					line = buf.readLine();
				}
			}
			//Kodgranskning: ändra printStackTrace(e)
		} catch (IOException e) {
			System.out.println("ERROR");
		}
		conditionMap = new String [nbrOfConditionalEvents][mostConditions+1];
		line = (String)list.pop();
		for (int i = 0; i < conditionMap.length; i++) {
			conditionMap[i][0] = line;
			for (int j = 1; j <= conditionMap[i].length; j++) {
				try {
					line = (String)list.pop();
					if (!Character.isDigit(line.charAt(0))) {
						conditionMap[i][j] = line;
					}
					else {
						break;
					}
				}
				//Kodgranskning: ändra printStackTrace(e)
				catch (NoSuchElementException e) {
					break;
				}
			}

		}
	}
	//Kodgranskning: ta bort main
	public static void main (String[] args) {
		ConditionFileReader reader = new ConditionFileReader ("files/testcondition.txt");
		for (int i = 0; i < reader.conditionMap.length; i++) {
			for (int j = 0; j < reader.conditionMap[i].length; j++) {
				System.out.println(reader.conditionMap[i][j]);
			}
			System.out.println();
		}
	}
}
