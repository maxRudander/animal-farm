package farm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class EffectFileReader {
	private EventHandler handler = EventHandler.getInstance();
	private String [][][] effectMap = new String [2][3][3];
	private	int eventID;
	private int optionID;
	private int effectID;

	public EffectFileReader(String filename) {
		try(BufferedReader buf = new BufferedReader (new InputStreamReader (new FileInputStream (filename), "UTF-8"))) {
			String line = buf.readLine();
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
		} catch (IOException e) {}
	}
	public String[][][] getEffectMap () {
		return effectMap;
	}
	public static void main (String[]args) {
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
