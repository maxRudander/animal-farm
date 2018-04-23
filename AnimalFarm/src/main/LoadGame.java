package main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
/**
 * Class for loading an game.
 * @author Matthias Falk
 *
 */
public class LoadGame {
	private String filename = "saves/gamesave.dat";
	private LinkedList loadedList = new LinkedList();
	
	/**
	 * Method that reads the text file for loading an game and returns it
	 * @return loadedList the list that has been loaded
	 */
	public LinkedList load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream (filename)));
			loadedList = (LinkedList) ois.readObject();
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadedList;
	}
}
