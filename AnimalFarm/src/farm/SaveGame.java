package farm;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
/**
 * Class for saving the game.
 * @author Matthias Falk
 *
 */
public class SaveGame {
	private String filename = "saveFiles/gamesave.dat";
	private LinkedList incomingList = new LinkedList();
	/**
	 * Constructor that recieves an list of data that will be saved
	 * @param incomingList the list that will be saved
	 */
	public SaveGame (LinkedList incomingList) {
		this.incomingList = incomingList;
		this.save();
	}
	/**
	 * Method that writes to the text file for saving an game
	 */
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			oos.writeObject(incomingList);
			oos.flush();
			oos.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
